package kaitou.ppp.service.impl;

import kaitou.ppp.common.log.BaseLogManager;
import kaitou.ppp.common.utils.FileUtil;
import kaitou.ppp.domain.system.OperationLog;
import kaitou.ppp.manager.system.SystemSettingsManager;
import kaitou.ppp.service.SystemSettingsService;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static kaitou.ppp.common.utils.ZipUtil.zip;

/**
 * 系统设置业务处理层实现.
 * User: 赵立伟
 * Date: 2015/1/22
 * Time: 14:21
 */
public class SystemSettingsServiceImpl extends BaseLogManager implements SystemSettingsService {
    /**
     * PPP操作日志文件名
     */
    private static final String PPP_OPERATION_LOG_NAME = "kaitou-ppp-operation.log";
    private SystemSettingsManager systemSettingsManager;
    /**
     * ppp日志目录
     */
    private String logDir;
    /**
     * ppp文件目录
     */
    private String pppDir;

    public void setLogDir(String logDir) {
        this.logDir = logDir;
    }

    public void setPppDir(String pppDir) {
        this.pppDir = pppDir;
    }

    public void setSystemSettingsManager(SystemSettingsManager systemSettingsManager) {
        this.systemSettingsManager = systemSettingsManager;
    }

    @Override
    public void updateSystemSettings() {
        systemSettingsManager.updateSystemSettings();
    }

    @Override
    public void updateLastFileChooserPath(String lastFileChooserPath) {
        systemSettingsManager.updateLastFileChooserPath(lastFileChooserPath);
    }

    @Override
    public String getSystemSetting(String fieldName) {
        return systemSettingsManager.getSystemSetting(fieldName);
    }

    @Override
    public void updateLocalIp(String localIp) {
        if (StringUtils.isEmpty(localIp)) {
            return;
        }
        systemSettingsManager.updateLocalIp(localIp);
        logOperation("设置本机ip：" + localIp);
    }

    @Override
    public String getLocalIp() {
        return systemSettingsManager.getLocalIp();
    }

    @Override
    public void backPPP(String targetFilePath) {
        try {
            zip(pppDir, targetFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<OperationLog> queryTodayOperationLogs() {
        String todayLogFile = logDir + File.separatorChar + PPP_OPERATION_LOG_NAME;
        List<OperationLog> operationLogList = new ArrayList<OperationLog>();
        try {
            List<String> todayLogs = FileUtil.readLines(todayLogFile);
            OperationLog operationLog;
            for (int i = todayLogs.size() - 1; i > 0; i--) {
                operationLog = OperationLog.display(todayLogs.get(i));
                if (operationLog == null) {
                    continue;
                }
                operationLogList.add(operationLog);
            }
        } catch (IOException e) {
            logSystemEx(e);
        }
        return operationLogList;
    }
}
