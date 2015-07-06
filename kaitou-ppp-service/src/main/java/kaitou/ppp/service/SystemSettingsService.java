package kaitou.ppp.service;

import kaitou.ppp.domain.system.OperationLog;

import java.util.List;

/**
 * 系统设置业务处理层.
 * User: 赵立伟
 * Date: 2015/1/22
 * Time: 14:20
 */
public interface SystemSettingsService {
    /**
     * 更新系统设置
     */
    public void updateSystemSettings();

    /**
     * 更新上次文件选择路径
     *
     * @param lastFileChooserPath 上次文件选择路径
     */
    public void updateLastFileChooserPath(String lastFileChooserPath);

    /**
     * 获取系统设置值
     *
     * @param fieldName 属性
     * @return 值
     */
    public String getSystemSetting(String fieldName);

    /**
     * 更新本机ip
     *
     * @param localIp 本机ip
     */
    public void updateLocalIp(String localIp);

    /**
     * 获取本机ip
     *
     * @return 本机ip
     */
    public String getLocalIp();

    /**
     * 备份PPP文件夹
     *
     * @param targetFilePath 目标文件路径
     */
    public void backPPP(String targetFilePath);

    /**
     * 查询今天操作日志
     * <p>
     * 按照操作时间倒序排列
     * </p>
     *
     * @return 操作日志对象列表
     */
    public List<OperationLog> queryTodayOperationLogs();
}
