package kaitou.ppp.dao;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.domain.BaseDomain4InDoubt;
import kaitou.ppp.domain.annotation.PKField;
import kaitou.ppp.domain.system.SysCode;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.*;

import static com.womai.bsp.tool.utils.JsonUtil.json2Object;
import static com.womai.bsp.tool.utils.ReflectionUtil.getAnnotationClassField;
import static com.womai.bsp.tool.utils.ReflectionUtil.getFieldValue;

/**
 * 存疑记录的DAO父类.
 * User: 赵立伟
 * Date: 2015/4/16
 * Time: 9:13
 */
public abstract class BaseDao4InDoubt<T extends BaseDomain4InDoubt> extends BaseDao {

    @SuppressWarnings("unchecked")
    @Override
    public Object[] preSave(Object... domains) {
        if (CollectionUtil.isEmpty(domains)) {
            return super.preSave(domains);
        }
        debugTime("生成序列号开始");
        for (Object obj : domains) {
            T domain = (T) obj;
            if (domain.getSerialNo() < 0) {
                domain.generateSerialNo();
            }
        }
        debugTime("生成序列号结束");
        // TODO 待优化
        return check1(domains);
//        return check2(domains);
    }

    private Object[] check2(Object[] domains) {
        String pkFieldName = getPkFieldName();
        if (StringUtils.isEmpty(pkFieldName)) {
            return super.preSave(domains);
        }
        List<T> toSave = CollectionUtil.newList(domains);
        if (CollectionUtil.isNotEmpty(toSave)) {
            Map<Object, List<T>> checkThisBatchHasDuplicate = new HashMap<Object, List<T>>();
            for (T domain : toSave) {
                domain.noDoubt();
                Object newPKValue = getFieldValue(getDomainClass(), pkFieldName, domain);
                List<T> hasList = checkThisBatchHasDuplicate.get(newPKValue);
                if (CollectionUtil.isEmpty(hasList)) {
                    checkThisBatchHasDuplicate.put(newPKValue, CollectionUtil.newList(domain));
                } else {
                    hasList.add(domain);
                    for (T hasDomain : hasList) {
                        hasDomain.doInDoubt();
                    }
                }
            }
        }
        debugTime("校验同批次主键冲突");
        List<String> dbList = readDBFile(toSave.get(0).dbFileSuffix());
        List<T> newToSave = new ArrayList<T>(toSave);
        if (CollectionUtil.isNotEmpty(dbList)) {
            for (String oneDb : dbList) {
                for (T domain : toSave) {
                    Object newPKValue = getFieldValue(getDomainClass(), pkFieldName, domain);
                    String serialNoFieldName = "serialNo";
                    Object serialValue = getFieldValue(getDomainClass(), serialNoFieldName, domain);
                    if (!hasFieldValue(pkFieldName, newPKValue, oneDb) || hasFieldValue(serialNoFieldName, serialValue, oneDb)) {
                        continue;
                    }
                    domain.doInDoubt();
                    T duplicateDomain = (T) json2Object(oneDb, getDomainClass());
                    duplicateDomain.doInDoubt();
                    newToSave.add(duplicateDomain);
                }
            }
        }
        debugTime("校验已存在主键冲突");
        return CollectionUtil.toArray(newToSave, getDomainClass());
    }

    private Object[] check1(Object[] domains) {
        String pkFieldName = getPkFieldName();
        if (StringUtils.isEmpty(pkFieldName)) {
            return super.preSave(domains);
        }
        Set<T> toSave = new HashSet<T>(CollectionUtil.newList(domains));
        List<T> existsList = queryAll();
        debugTime("查询已存在的记录");
        for (T existsDomain : existsList) {
            if (existsDomain.isInDoubt()) {
                toSave.add(existsDomain);
            }
        }
        existsList.removeAll(toSave);
        Map<Object, List<T>> checkThisBatchHasDuplicate = new HashMap<Object, List<T>>();
        for (T domain : toSave) {
            domain.noDoubt();
            for (T existsDomain : existsList) {
                if (domain.getSerialNo() < 0 && domain.equals(existsDomain)) {
                    domain.setSerialNo(existsDomain.getSerialNo());
                }
            }
            if (domain.getSerialNo() < 0) {
                domain.generateSerialNo();
            }
            Object newPKValue = getFieldValue(getDomainClass(), pkFieldName, domain);
            if (newPKValue == null) {
                domain.doInDoubt();
                continue;
            }
            for (T existsDomain : existsList) {
                Object pkValue = getFieldValue(getDomainClass(), pkFieldName, existsDomain);
                if (newPKValue.equals(pkValue) && domain.getSerialNo() != existsDomain.getSerialNo()) {
                    domain.doInDoubt();
                    break;
                }
            }
            List<T> hasList = checkThisBatchHasDuplicate.get(newPKValue);
            if (CollectionUtil.isEmpty(hasList)) {
                checkThisBatchHasDuplicate.put(newPKValue, CollectionUtil.newList(domain));
            } else {
                hasList.add(domain);
                for (T hasDomain : hasList) {
                    hasDomain.doInDoubt();
                }
            }
        }
        debugTime("校验主键是否冲突完成");
        return CollectionUtil.toArray(toSave, getDomainClass());
    }

    /**
     * 获取主键属性名称
     *
     * @return 主键属性名称。如果没有指定主键，主键个数不唯一，或主键类型是覆盖，则返回空值
     */
    private String getPkFieldName() {
        List<Field> pkFields = getAnnotationClassField(getDomainClass(), PKField.class);
        if (CollectionUtil.isEmpty(pkFields) || pkFields.size() > 1) {
            return "";
        }
        Field pkField = pkFields.get(0);
        PKField fieldLogo = pkField.getAnnotation(PKField.class);
        if (fieldLogo.PKViolationType() == SysCode.PKViolationType.COVERED) {
            return "";
        }
        return pkField.getName();
    }

}
