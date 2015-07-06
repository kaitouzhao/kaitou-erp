package kaitou.ppp.service.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.common.log.BaseLogManager;
import kaitou.ppp.domain.system.DBVersion;
import kaitou.ppp.manager.system.LocalDBVersionManager;
import kaitou.ppp.service.LocalDBVersionService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kaitou.ppp.common.utils.FileUtil.*;

/**
 * 本地DB版本库业务层实现.
 * User: 赵立伟
 * Date: 2015/4/11
 * Time: 9:46
 */
public class LocalDBVersionServiceImpl extends BaseLogManager implements LocalDBVersionService {

    private String dbDir;
    private LocalDBVersionManager localDBVersionManager;

    public void setDbDir(String dbDir) {
        this.dbDir = dbDir;
    }

    public void setLocalDBVersionManager(LocalDBVersionManager localDBVersionManager) {
        this.localDBVersionManager = localDBVersionManager;
    }

    @Override
    public List<DBVersion> getToUpgradeList(List<DBVersion> remoteDbVersions) {
        List<DBVersion> toUpgradeList = new ArrayList<DBVersion>();
        if (CollectionUtil.isEmpty(remoteDbVersions)) {
            return toUpgradeList;
        }
        for (DBVersion remoteDBVersion : remoteDbVersions) {
            long remoteVersion = remoteDBVersion.getLatestVersion();
            String dbFileName = remoteDBVersion.getDbFileName();
            if (remoteVersion > localDBVersionManager.getDBLatestVersion(dbFileName)) {
                toUpgradeList.add(remoteDBVersion);
            }
        }
        return toUpgradeList;
    }

    @Override
    public void upgradeByRemoteDBs(Map<DBVersion, List<String>> remoteDBs) {
        if (CollectionUtil.isEmpty(remoteDBs)) {
            return;
        }
        Map<String, Long> toUpgradeDBMap = new HashMap<String, Long>();
        for (Map.Entry<DBVersion, List<String>> item : remoteDBs.entrySet()) {
            DBVersion remoteDBVersion = item.getKey();
            String dbFileName = remoteDBVersion.getDbFileName();
            String dbFilePath = dbDir + File.separatorChar + dbFileName;
            String backDbFilePath = dbFilePath + ".back";
            copy(dbFilePath, backDbFilePath);
            try {
                List<String> localDBList = readLines(dbFilePath);
                List<String> remoteDBList = item.getValue();
                delete(dbFilePath);
                writeLines(dbFilePath, mergedDB(remoteDBList, localDBList));
                toUpgradeDBMap.put(dbFileName, remoteDBVersion.getLatestVersion());
                logOperation("已同步升级：" + dbFileName);
            } catch (IOException e) {
                logSystemEx(e);
                copy(backDbFilePath, dbFilePath);
            }
            delete(backDbFilePath);
        }
        if (CollectionUtil.isEmpty(toUpgradeDBMap)) {
            return;
        }
        localDBVersionManager.upgrade(toUpgradeDBMap);
    }

    private static final String SERIAL_NO_FIELD_NAME = "serialNo";

    /**
     * 合并DB
     *
     * @param remoteDBList 远程DB列表
     * @param localDBList  本地DB列表
     * @return 合并后的DB列表
     */
    private List<String> mergedDB(List<String> remoteDBList, List<String> localDBList) {
        List<String> mergedDBList = new ArrayList<String>();
        mergedDBList.addAll(remoteDBList);
        for (String localDB : localDBList) {
            String localSerialNoValue = getSerialNoValue(localDB);
            boolean isExists = false;
            for (String remoteDB : remoteDBList) {
                if (hasFieldValue(SERIAL_NO_FIELD_NAME, localSerialNoValue, remoteDB)) {
                    isExists = true;
                    break;
                }
            }
            if (!isExists) {
                mergedDBList.add(localDB);
            }
        }
        return mergedDBList;
    }

    /**
     * 获取序列号
     *
     * @param db 一行db
     * @return 序列号
     */
    private String getSerialNoValue(String db) {
        String substring = db.substring(db.indexOf("\"" + SERIAL_NO_FIELD_NAME + "\":") + 11);
        return substring.substring(0, substring.indexOf(","));
    }

    /**
     * 校验数据行是否含有指定属性的指定值
     *
     * @param fieldName  属性名
     * @param fieldValue 属性值
     * @param oneDb      某行数据
     * @return 如果含有即为真
     */
    private boolean hasFieldValue(String fieldName, Object fieldValue, String oneDb) {
        String fieldStr = "\"" + fieldName + "\":\"" + String.valueOf(fieldValue) + "\"";
        String fieldStr1 = "\"" + fieldName + "\":" + String.valueOf(fieldValue);
        return oneDb.contains(fieldStr) || oneDb.contains(fieldStr1);
    }

    @Override
    public void cacheDBLatestVersion() {
        localDBVersionManager.cacheDBLatestVersion();
    }
}
