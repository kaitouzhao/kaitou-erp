package kaitou.ppp.dao;

import com.womai.bsp.tool.utils.CollectionUtil;
import com.womai.bsp.tool.utils.JsonUtil;
import kaitou.ppp.common.log.BaseLogManager;
import kaitou.ppp.dao.support.Condition;
import kaitou.ppp.dao.support.Pager;
import kaitou.ppp.domain.BaseDomain;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.womai.bsp.tool.utils.JsonUtil.json2Object;
import static com.womai.bsp.tool.utils.JsonUtil.object2Json;
import static kaitou.ppp.common.utils.FileUtil.readLines;
import static kaitou.ppp.common.utils.FileUtil.writeLines;
import static kaitou.ppp.domain.BaseDomain.BACK_SUFFIX;
import static kaitou.ppp.domain.BaseDomain.DB_SUFFIX;
import static kaitou.ppp.domain.system.SysCode.DB_FILE_NAME_SPLIT;

/**
 * DAO父类.
 * User: 赵立伟
 * Date: 2015/1/22
 * Time: 11:53
 */
public abstract class BaseDao<T extends BaseDomain> extends BaseLogManager {

    /**
     * 数据文件目录
     */
    protected String dbDir;

    /**
     * 获取实体类型
     *
     * @return 实体类型
     */
    public abstract Class<T> getDomainClass();

    /**
     * 设置DB目录
     *
     * @param dbDir DB目录
     */
    public void setDbDir(String dbDir) {
        this.dbDir = dbDir;
    }

    /**
     * 保存/更新
     *
     * @param domains 实体集合。支持一个或多个
     * @return 成功保存记录数
     */
    public int save(Object... domains) {
        return save(true, domains);
    }

    /**
     * 保存/更新
     *
     * @param isNewTransaction 是否开启新事务
     * @param domains          实体集合。支持一个或多个
     * @return 成功保存记录数
     */
    @SuppressWarnings("unchecked")
    public int save(boolean isNewTransaction, Object... domains) {
        Object[] toSave = preSave(isNewTransaction, domains);
        if (CollectionUtil.isEmpty(toSave)) {
            return 0;
        }
        Map<String, List<T>> domainMap = new HashMap<String, List<T>>();
        int size = toSave.length;
        int updateIndex = -1;
        int successCount = 0;
        int i = 0;
        while (i < size) {
            T domain = (T) toSave[i];
            try {
                domain.check();
            } catch (RuntimeException e) {
                logOperation("第" + (i + 1) + "条数据校验不通过。原因：" + e.getMessage());
                i++;
                continue;
            }
            successCount++;
            String backDbFileName = domain.backDbFileName();
            List<T> domainList = domainMap.get(backDbFileName);
            if (domainList == null) {
                domainList = query(isNewTransaction ? domain.dbFileName() : domain.backDbFileName());
                if (CollectionUtil.isEmpty(domainList)) {
                    domainList = new ArrayList<T>();
                }
                domainMap.put(backDbFileName, domainList);
            }
            if (CollectionUtil.isEmpty(domainList)) {
                domainList.add(domain);
                i++;
                continue;
            }
            for (int j = 0; j < domainList.size(); j++) {
                if (domain.equals(domainList.get(j))) {
                    updateIndex = j;
                }
            }
            if (updateIndex > -1) {
                domainList.remove(updateIndex);
                domainList.add(updateIndex, domain);
                updateIndex = -1;
            } else {
                domainList.add(domain);
            }
            i++;
        }
        debugTime("文件与数据对应关系建立");
        for (Map.Entry<String, List<T>> item : domainMap.entrySet()) {
            List<T> domainList = item.getValue();
            List<String> eJsonList = new ArrayList<String>();
            for (Object domain : domainList) {
                eJsonList.add(object2Json(domain));
            }
            try {
                writeLines(dbDir + File.separatorChar + item.getKey(), eJsonList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        debugTime("保存DB文件");
        return successCount;
    }

    /**
     * 保存/更新前操作
     * <p>
     * 保存/更新前如有特殊操作，请覆盖此方法
     * </p>
     *
     * @param isNewTransaction 是否开启新事务
     * @param domains          实体集合。支持一个或多个
     * @return 待保存的实体集合
     */
    @SuppressWarnings("unchecked")
    protected Object[] preSave(boolean isNewTransaction, Object... domains) {
        for (Object obj : domains) {
            T domain = (T) obj;
            if (domain.getSerialNo() < 0) {
                domain.generateSerialNo();
            }
        }
        return CollectionUtil.toArray(CollectionUtil.newList(domains), Object.class);
    }

    /**
     * 查询
     *
     * @param dbFileSuffix DB文件后缀
     * @return 实体列表
     */
    protected List<T> query(final String dbFileSuffix) {
        List<String> dbList = readDBFile(dbFileSuffix);
        List<T> domainList = new ArrayList<T>();
        if (CollectionUtil.isEmpty(dbList)) {
            return domainList;
        }
        for (String line : dbList) {
            domainList.add(json2Object(line, getDomainClass()));
        }
        debugTime("构建实体对象");
        return domainList;
    }

    /**
     * 读取DB文件
     *
     * @param dbFileSuffix DB文件后缀
     * @return 内容
     */
    protected List<String> readDBFile(final String dbFileSuffix) {
        File file = new File(dbDir);
        File[] dbFiles = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(dbFileSuffix) || name.endsWith(DB_FILE_NAME_SPLIT + dbFileSuffix);
            }
        });
        List<String> dbList = new ArrayList<String>();
        if (CollectionUtil.isEmpty(dbFiles)) {
            return dbList;
        }
        debugTime("获取DB文件");
        for (File dbFile : dbFiles) {
            debugTime("开始读取文件" + dbFile.getName());
            try {
                dbList.addAll(readLines(dbFile.getPath()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            debugTime("结束读取文件" + dbFile.getName());
        }
        return dbList;
    }

    /**
     * 读取DB文件
     *
     * @return 内容
     */
    private List<String> readDBFile() {
        return readDBFile(getDomainClass().getSimpleName() + DB_SUFFIX);
    }

    /**
     * 分页查询
     *
     * @param currentPage 当前页码
     * @param conditions  查询条件列表
     * @return 组装结果的分页对象
     */
    public Pager<T> queryPager(int currentPage, List<Condition> conditions) {
        Pager<T> pager = new Pager<T>(currentPage, conditions);
        if (pager.inValid()) {
            throw new RuntimeException("分页无效");
        }
        List<String> selectedList = query(conditions);
        pager.setTotalSize(selectedList.size());
        List<String> pagerList = selectedList.subList(pager.beginIndex(), pager.endIndex());
        List<T> domainList = new ArrayList<T>();
        for (String db : pagerList) {
            domainList.add(json2Object(db, getDomainClass()));
        }
        return pager.setResult(domainList);
    }

    /**
     * 根据查询条件筛选数据
     *
     * @param conditions 查询条件列表
     * @return 数据列表
     */
    private List<String> query(List<Condition> conditions) {
        List<String> dbList = readDBFile();
        List<String> selectedList = new ArrayList<String>();
        if (CollectionUtil.isEmpty(dbList)) {
            return selectedList;
        }
        if (CollectionUtil.isEmpty(conditions)) {
            selectedList.addAll(dbList);
            return selectedList;
        }
        for (String db : dbList) {
            boolean isSelected = true;
            for (Condition condition : conditions) {
                String fieldValue = JsonUtil.getFieldValue(db, condition.getFieldName());
                if (!fieldValue.toLowerCase().contains(String.valueOf(condition.getFieldValue()).toLowerCase().trim())) {
                    isSelected = false;
                    break;
                }
            }
            if (isSelected) {
                selectedList.add(db);
            }
        }
        return selectedList;
    }

    /**
     * 不分页查询
     *
     * @param conditions 查询条件
     * @return 结果集
     */
    public List<T> queryAll(List<Condition> conditions) {
        List<T> domainList = new ArrayList<T>();
        List<String> selectedList = query(conditions);
        for (String db : selectedList) {
            domainList.add(json2Object(db, getDomainClass()));
        }
        return domainList;
    }

    /**
     * 查询
     *
     * @param isNewTransaction 是否开启新事务
     * @param dbType           DB文件类型。如果为空，则默认获取全部
     * @return 实体列表
     */
    protected List<T> query(boolean isNewTransaction, String... dbType) {
        List<T> domainList = new ArrayList<T>();
        final String domainName = getDomainClass().getSimpleName();
        final String dbSuffix = isNewTransaction ? domainName + DB_SUFFIX : domainName + DB_SUFFIX + BACK_SUFFIX;
        if (!CollectionUtil.isEmpty(dbType)) {
            for (String sId : dbType) {
                String dbFileSuffix = sId + DB_FILE_NAME_SPLIT + dbSuffix;
                domainList.addAll(query(dbFileSuffix));
            }
            return domainList;
        }
        File dbDirFile = new File(dbDir);
        File[] dbFiles = dbDirFile.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(dbSuffix);
            }
        });
        for (File dbFile : dbFiles) {
            String dbFileName = dbFile.getName();
            domainList.addAll(query(dbFileName));
        }
        return domainList;
    }

    /**
     * 查询
     *
     * @param dbType DB文件类型。如果为空，则默认获取全部
     * @return 实体列表
     */
    public List<T> query(String... dbType) {
        return query(true, dbType);
    }

    /**
     * 查询全部
     *
     * @param isNewTransaction 是否开启新事务
     * @return 实体列表
     */
    protected List<T> queryAll(boolean isNewTransaction) {
        return query(isNewTransaction);
    }

    /**
     * 查询全部
     *
     * @return 实体列表
     */
    public List<T> queryAll() {
        return queryAll(true);
    }

    /**
     * 删除
     *
     * @param domains 待删除集合。支持一个或多个
     * @return 成功删除记录数
     */
    @SuppressWarnings("unchecked")
    public int delete(Object... domains) {
        if (CollectionUtil.isEmpty(domains)) {
            return 0;
        }
        Map<String, List<T>> domainMap = new HashMap<String, List<T>>();
        int updateIndex = -1;
        int successCount = 0;
        List<T> specialHandlingDomains = afterDelete(domains);
        for (Object domain1 : domains) {
            T domain = (T) domain1;
            String backDbFileName = domain.backDbFileName();
            List<T> domainList = domainMap.get(backDbFileName);
            if (domainList == null) {
                domainList = query(domain.dbFileName());
                if (CollectionUtil.isEmpty(domainList)) {
                    continue;
                }
                domainMap.put(backDbFileName, domainList);
            }
            for (int j = 0; j < domainList.size(); j++) {
                if (domain.equals(domainList.get(j))) {
                    updateIndex = j;
                    break;
                }
            }
            if (updateIndex > -1) {
                domainList.remove(updateIndex);
                updateIndex = -1;
                successCount++;
            }
        }
        updateIndex = -1;
        for (T specialHandlingDomain : specialHandlingDomains) {
            String backDbFileName = specialHandlingDomain.backDbFileName();
            List<T> domainList = domainMap.get(backDbFileName);
            if (domainList == null) {
                domainList = query(specialHandlingDomain.dbFileName());
                if (CollectionUtil.isEmpty(domainList)) {
                    domainList = new ArrayList<T>();
                }
                domainMap.put(backDbFileName, domainList);
            }
            if (CollectionUtil.isEmpty(domainList)) {
                domainList.add(specialHandlingDomain);
                continue;
            }
            for (int j = 0; j < domainList.size(); j++) {
                if (specialHandlingDomain.equals(domainList.get(j))) {
                    updateIndex = j;
                }
            }
            if (updateIndex > -1) {
                domainList.remove(updateIndex);
                domainList.add(updateIndex, specialHandlingDomain);
                updateIndex = -1;
            }
        }
        for (Map.Entry<String, List<T>> item : domainMap.entrySet()) {
            List<T> domainList = item.getValue();
            List<String> eJsonList = new ArrayList<String>();
            for (Object domain : domainList) {
                eJsonList.add(object2Json(domain));
            }
            try {
                writeLines(dbDir + File.separatorChar + item.getKey(), eJsonList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return successCount;
    }

    /**
     * 删除后执行逻辑
     * <p>
     * 删除后如有特殊操作，请自行覆盖
     * </p>
     *
     * @param domains 待删除实体集合
     * @return 需要特殊处理的实体集合。默认为空
     */
    protected List<T> afterDelete(Object... domains) {
        return CollectionUtil.newList();
    }

    /**
     * 校验数据行是否含有指定属性的指定值
     *
     * @param fieldName  属性名
     * @param fieldValue 属性值
     * @param oneDb      某行数据
     * @return 如果含有即为真
     */
    protected boolean hasFieldValue(String fieldName, Object fieldValue, String oneDb) {
        String fieldStr = "\"" + fieldName + "\":\"" + String.valueOf(fieldValue) + "\"";
        String fieldStr1 = "\"" + fieldName + "\":" + String.valueOf(fieldValue);
        return oneDb.contains(fieldStr) || oneDb.contains(fieldStr1);
    }
}
