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

    private static final String SERIAL_NO_FIELD_NAME = "serialNo";

    @SuppressWarnings("unchecked")
    @Override
    public Object[] preSave(boolean isNewTransaction, Object... domains) {
        if (CollectionUtil.isEmpty(domains)) {
            return super.preSave(isNewTransaction, domains);
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
        return check1(isNewTransaction, domains);
//        return check2(isNewTransaction, domains);
    }

    /**
     * 校验主键（优化后）
     *
     * @param isNewTransaction 是否新开事务
     * @param domains          实体集合
     * @return 待保存的实体集合
     */
    private Object[] check2(boolean isNewTransaction, Object[] domains) {
        String pkFieldName = getPkFieldName();
        if (StringUtils.isEmpty(pkFieldName)) {
            return super.preSave(isNewTransaction, domains);
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
                    Object serialValue = getFieldValue(getDomainClass(), SERIAL_NO_FIELD_NAME, domain);
                    if (!hasFieldValue(pkFieldName, newPKValue, oneDb) || hasFieldValue(SERIAL_NO_FIELD_NAME, serialValue, oneDb)) {
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

    /**
     * 校验主键
     *
     * @param isNewTransaction 是否新开事务
     * @param domains          实体集合
     * @return 待保存的实体集合
     */
    private Object[] check1(boolean isNewTransaction, Object[] domains) {
        String pkFieldName = getPkFieldName();
        if (StringUtils.isEmpty(pkFieldName)) {
            return super.preSave(isNewTransaction, domains);
        }
        Set<T> toSave = new HashSet<T>(CollectionUtil.newList(domains));
        List<T> existsList = queryAll(isNewTransaction);
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

    /**
     * 特殊处理与被删除实体主键冲突的实体
     *
     * @param domains 待删除实体集合
     * @return 特殊处理实体
     */
    @Override
    protected List<T> afterDelete(Object... domains) {
        if (CollectionUtil.isEmpty(domains)) {
            return super.afterDelete(domains);
        }
        List<String> dbList = readDBFile(((T) domains[0]).dbFileSuffix());
        Map<Object, List<String>> checkThisBatchHasDuplicate = new HashMap<Object, List<String>>();
        String pkFieldName = getPkFieldName();
        Class domainClass = getDomainClass();
        for (Object obj : domains) {
            T domain = (T) obj;
            Object pkValue = getFieldValue(domainClass, pkFieldName, domain);
            Object serialValue = domain.getSerialNo();
            for (String oneDb : dbList) {
                if (!hasFieldValue(pkFieldName, pkValue, oneDb) || hasFieldValue(SERIAL_NO_FIELD_NAME, serialValue, oneDb)) {
                    continue;
                }
                List<String> hasList = checkThisBatchHasDuplicate.get(pkValue);
                if (CollectionUtil.isEmpty(hasList)) {
                    checkThisBatchHasDuplicate.put(pkValue, CollectionUtil.newList(oneDb));
                } else {
                    hasList.add(oneDb);
                }
            }
        }
        List<T> specialHandlingDomains = CollectionUtil.newList();
        for (Map.Entry<Object, List<String>> item : checkThisBatchHasDuplicate.entrySet()) {
            List<String> dbs = item.getValue();
            if (CollectionUtil.isNotEmpty(dbs) && dbs.size() == 1) {
                for (String db : dbs) {
                    T duplicateDomain = (T) json2Object(db, getDomainClass());
                    duplicateDomain.noDoubt();
                    specialHandlingDomains.add(duplicateDomain);
                }
            }
        }
        return specialHandlingDomains;
    }
}
