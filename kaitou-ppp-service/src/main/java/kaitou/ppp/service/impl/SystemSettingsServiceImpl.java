package kaitou.ppp.service.impl;

import kaitou.ppp.common.log.BaseLogManager;
import kaitou.ppp.manager.system.SystemSettingsManager;
import kaitou.ppp.service.SystemSettingsService;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;

import static kaitou.ppp.common.utils.ZipUtil.zip;

/**
 * 系统设置业务处理层实现.
 * User: 赵立伟
 * Date: 2015/1/22
 * Time: 14:21
 */
public class SystemSettingsServiceImpl extends BaseLogManager implements SystemSettingsService {

    private SystemSettingsManager systemSettingsManager;
    /**
     * ppp文件目录
     */
    private String pppDir;

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
}
