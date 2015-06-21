package kaitou.ppp.service;

import kaitou.ppp.dao.support.Condition;
import kaitou.ppp.dao.support.Pager;
import kaitou.ppp.domain.ts.*;

import java.io.File;
import java.util.List;

/**
 * TS管理服务层.
 * User: 赵立伟
 * Date: 2015/5/20
 * Time: 11:21
 */
public interface TSService {

    /**
     * 导入TS培训记录
     *
     * @param srcFile 源文件
     */
    public void importTSTraining(File srcFile);

    /**
     * 导出TS培训记录
     *
     * @param targetFile   目标文件
     * @param numberOfYear 查询年份，可以是单年，也可以是多年
     */
    public void exportTSTraining(File targetFile, String... numberOfYear);

    /**
     * 查询TS培训记录
     *
     * @return TS培训记录
     */
    public List<TSTraining> queryTSTraining();

    /**
     * 保存/更新TS培训记录
     *
     * @param tsTraining TS培训记录
     */
    public void saveOrUpdateTSTraining(TSTraining... tsTraining);

    /**
     * 删除TS培训记录
     *
     * @param tsTraining TS培训记录
     */
    public void deleteTSTraining(Object... tsTraining);

    /**
     * 导入TS手册权限
     *
     * @param srcFile 源文件
     */
    public void importTSManualPermission(File srcFile);

    /**
     * 导出TS手册权限
     *
     * @param targetFile   目标文件
     * @param numberOfYear 查询年份，可以是单年，也可以是多年
     */
    public void exportTSManualPermission(File targetFile, String... numberOfYear);

    /**
     * 查询TS手册权限
     *
     * @return TS手册权限
     */
    public List<TSManualPermissions> queryTSManualPermission();

    /**
     * 保存/更新TS手册权限
     *
     * @param tsManualPermissions TS手册权限
     */
    public void saveOrUpdateTSManualPermission(TSManualPermissions... tsManualPermissions);

    /**
     * 删除TS手册权限
     *
     * @param tsManualPermission TS手册权限
     */
    public void deleteTSManualPermission(Object... tsManualPermission);

    /**
     * 导入TS SDS权限
     *
     * @param srcFile 源文件
     */
    public void importTSSDSPermission(File srcFile);

    /**
     * 导出TS SDS权限
     *
     * @param targetFile 目标文件
     */
    public void exportTSSDSPermission(File targetFile);

    /**
     * 查询TS SDS权限
     *
     * @return TS SDS权限
     */
    public List<TSSDSPermission> queryTSSDSPermission();

    /**
     * 保存/更新TS SDS权限
     *
     * @param tsSDSPermissions TS SDS权限
     */
    public void saveOrUpdateTSSDSPermission(TSSDSPermission... tsSDSPermissions);

    /**
     * 删除TS SDS权限
     *
     * @param tsSDSPermission TS SDS权限
     */
    public void deleteTSSDSPermission(Object... tsSDSPermission);

    /**
     * 获取TS SDS权限到期时间提醒
     *
     * @return 提醒语
     */
    public List<TSSDSPermission> getTSSDSEndDateReminder();

    /**
     * 分页查询
     *
     * @param currentPage 当前页码
     * @param conditions  查询条件列表
     * @return 封装结果集的分页对象
     */
    public Pager<TSSDSPermission> queryTSSDSPermissionPager(int currentPage, List<Condition> conditions);

    /**
     * 不分页查询
     *
     * @param conditions 查询条件列表
     * @return 结果集
     */
    public List<TSSDSPermission> queryTSSDSPermission(List<Condition> conditions);

    /**
     * 导入TS装机权限
     *
     * @param srcFile 源文件
     */
    public void importTSInstallPermission(File srcFile);

    /**
     * 导出TS装机权限
     *
     * @param targetFile 目标文件
     */
    public void exportTSInstallPermission(File targetFile);

    /**
     * 查询TS装机权限
     *
     * @return TS装机权限
     */
    public List<TSInstallPermission> queryTSInstallPermission();

    /**
     * 保存/更新TS装机权限
     *
     * @param tsInstallPermissions TS装机权限
     */
    public void saveOrUpdateTSInstallPermission(TSInstallPermission... tsInstallPermissions);

    /**
     * 删除TS装机权限
     *
     * @param tsInstallPermission TS装机权限
     */
    public void deleteTSInstallPermission(Object... tsInstallPermission);

    /**
     * 导入旧机翻新记录
     *
     * @param srcFile 源文件
     */
    public void importOldMachineRenew(File srcFile);

    /**
     * 导出旧机翻新记录
     *
     * @param targetFile   目标文件
     * @param numberOfYear 查询年份，可以是单年，也可以是多年
     */
    public void exportOldMachineRenew(File targetFile, String... numberOfYear);

    /**
     * 查询旧机翻新记录
     *
     * @return 旧机翻新记录
     */
    public List<OldMachineRenew> queryOldMachineRenew();

    /**
     * 保存/更新旧机翻新记录
     *
     * @param oldMachineRenew 旧机翻新记录
     */
    public void saveOrUpdateOldMachineRenew(OldMachineRenew... oldMachineRenew);

    /**
     * 删除旧机翻新记录
     *
     * @param oldMachineRenew 旧机翻新记录
     */
    public void deleteOldMachineRenew(Object... oldMachineRenew);

    /**
     * 导入新装机索赔记录
     *
     * @param srcFile 源文件
     */
    public void importNewMachineClaim(File srcFile);

    /**
     * 导出新装机索赔记录
     *
     * @param targetFile   目标文件
     * @param numberOfYear 查询年份，可以是单年，也可以是多年
     */
    public void exportNewMachineClaim(File targetFile, String... numberOfYear);

    /**
     * 查询新装机索赔记录
     *
     * @return 新装机索赔记录
     */
    public List<NewMachineClaim> queryNewMachineClaim();

    /**
     * 保存/更新新装机索赔记录
     *
     * @param newMachineClaim 新装机索赔记录
     */
    public void saveOrUpdateNewMachineClaim(NewMachineClaim... newMachineClaim);

    /**
     * 删除新装机索赔记录
     *
     * @param newMachineClaim 新装机索赔记录
     */
    public void deleteNewMachineClaim(Object... newMachineClaim);

    /**
     * 导入工具领用记录
     *
     * @param srcFile 源文件
     */
    public void importToolRecipients(File srcFile);

    /**
     * 导出工具领用记录
     *
     * @param targetFile   目标文件
     * @param numberOfYear 查询年份，可以是单年，也可以是多年
     */
    public void exportToolRecipients(File targetFile, String... numberOfYear);

    /**
     * 查询工具领用记录
     *
     * @return 工具领用记录
     */
    public List<ToolRecipients> queryToolRecipients();

    /**
     * 保存/更新工具领用记录
     *
     * @param toolRecipients 工具领用记录
     */
    public void saveOrUpdateToolRecipients(ToolRecipients... toolRecipients);

    /**
     * 删除工具领用记录
     *
     * @param toolRecipients 工具领用记录
     */
    public void deleteToolRecipients(Object... toolRecipients);

    /**
     * 导入零件借用记录
     *
     * @param srcFile 源文件
     */
    public void importComponentBorrowing(File srcFile);

    /**
     * 导出零件借用记录
     *
     * @param targetFile   目标文件
     * @param numberOfYear 查询年份，可以是单年，也可以是多年
     */
    public void exportComponentBorrowing(File targetFile, String... numberOfYear);

    /**
     * 查询零件借用记录
     *
     * @return 零件借用记录
     */
    public List<ComponentBorrowing> queryComponentBorrowing();

    /**
     * 保存/更新零件借用记录
     *
     * @param componentBorrowing 零件借用记录
     */
    public void saveOrUpdateComponentBorrowing(ComponentBorrowing... componentBorrowing);

    /**
     * 删除零件借用记录
     *
     * @param componentBorrowing 零件借用记录
     */
    public void deleteComponentBorrowing(Object... componentBorrowing);

    /**
     * 导入dongle记录
     *
     * @param srcFile 源文件
     */
    public void importTSDongles(File srcFile);

    /**
     * 导出dongle记录
     *
     * @param targetFile 目标文件
     */
    public void exportTSDongles(File targetFile);

    /**
     * 查询dongle记录
     *
     * @return dongle记录列表
     */
    public List<TSDongle> queryTSDongles();

    /**
     * 保存/更新dongle记录
     *
     * @param tsDongles dongle记录
     */
    public void saveOrUpdateTSDongles(TSDongle... tsDongles);

    /**
     * 删除dongle记录
     *
     * @param tsDongles dongle记录
     */
    public void deleteTSDongles(Object... tsDongles);

    /**
     * 分页查询
     *
     * @param currentPage 当前页码
     * @param conditions  查询条件列表
     * @return 封装结果集的分页对象
     */
    public Pager<TSDongle> queryTSDonglePager(int currentPage, List<Condition> conditions);

    /**
     * 不分页查询
     *
     * @param conditions 查询条件列表
     * @return 结果集
     */
    public List<TSDongle> queryTSDongle(List<Condition> conditions);
}
