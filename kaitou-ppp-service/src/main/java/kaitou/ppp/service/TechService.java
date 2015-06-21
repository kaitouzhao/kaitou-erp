package kaitou.ppp.service;

import kaitou.ppp.dao.support.Condition;
import kaitou.ppp.dao.support.Pager;
import kaitou.ppp.domain.tech.*;

import java.io.File;
import java.util.List;

/**
 * 技术管理服务.
 * User: 赵立伟
 * Date: 2015/5/19
 * Time: 10:36
 */
public interface TechService {

    /**
     * 导入手册权限
     *
     * @param srcFile 源文件
     */
    public void importManualPermissions(File srcFile);

    /**
     * 导出手册权限
     *
     * @param targetFile 目标文件
     */
    public void exportManualPermissions(File targetFile);

    /**
     * 查询手册权限
     *
     * @return 手册权限列表
     */
    public List<TechManualPermissions> queryManualPermissions();

    /**
     * 保存/更新手册权限
     *
     * @param techManualPermissions 手册权限记录
     */
    public void saveOrUpdateManualPermissions(TechManualPermissions... techManualPermissions);

    /**
     * 删除手册权限
     *
     * @param manualPermissions 手册权限
     */
    public void deleteManualPermissions(Object... manualPermissions);

    /**
     * 导入SOID识别码
     *
     * @param srcFile 源文件
     */
    public void importSOIDCode(File srcFile);

    /**
     * 导出SOID识别码
     *
     * @param targetFile 目标文件
     */
    public void exportSOIDCode(File targetFile);

    /**
     * 查询SOID识别码
     *
     * @return SOID识别码列表
     */
    public List<SOIDCode> querySOIDCode();

    /**
     * 保存/更新SOID识别码
     *
     * @param soidCodes SOID识别码
     */
    public void saveOrUpdateSOIDCode(SOIDCode... soidCodes);

    /**
     * 删除SOID识别码
     *
     * @param soidCodes SOID识别码
     */
    public void deleteSOIDCode(Object... soidCodes);

    /**
     * 导入SDS权限管理
     *
     * @param srcFile 源文件
     */
    public void importSDSPermissions(File srcFile);

    /**
     * 导出SDS权限管理
     *
     * @param targetFile 目标文件
     */
    public void exportSDSPermissions(File targetFile);

    /**
     * 查询SDS权限管理
     *
     * @return SDS权限管理列表
     */
    public List<TechSDSPermission> querySDSPermissions();

    /**
     * 保存/更新SDS权限管理
     *
     * @param techSdsPermissions SDS权限管理
     */
    public void saveOrUpdateSDSPermissions(TechSDSPermission... techSdsPermissions);

    /**
     * 删除SDS权限管理
     *
     * @param sdsPermissions SDS权限管理
     */
    public void deleteSDSPermissions(Object... sdsPermissions);

    /**
     * 导入技术支援
     *
     * @param srcFile 源文件
     */
    public void importTechSupport(File srcFile);

    /**
     * 导出技术支援
     *
     * @param targetFile   目标文件
     * @param numberOfYear 导出年份。可以是单年，也可以使多年
     */
    public void exportTechSupport(File targetFile, String... numberOfYear);

    /**
     * 查询技术支援
     *
     * @return 技术支援列表
     */
    public List<TechSupport> queryTechSupport();

    /**
     * 保存/更新技术支援
     *
     * @param techSupport 技术支援
     */
    public void saveOrUpdateTechSupport(TechSupport... techSupport);

    /**
     * 删除技术支援
     *
     * @param techSupport 技术支援
     */
    public void deleteTechSupport(Object... techSupport);

    /**
     * 导入装机权限
     *
     * @param srcFile 源文件
     */
    public void importInstallPermission(File srcFile);

    /**
     * 导出装机权限
     *
     * @param targetFile 目标文件
     */
    public void exportInstallPermission(File targetFile);

    /**
     * 查询装机权限
     *
     * @return 装机权限列表
     */
    public List<TechInstallPermission> queryInstallPermission();

    /**
     * 保存/更新装机权限
     *
     * @param techInstallPermission 装机权限
     */
    public void saveOrUpdateInstallPermission(TechInstallPermission... techInstallPermission);

    /**
     * 删除装机权限
     *
     * @param installPermission 装机权限
     */
    public void deleteInstallPermission(Object... installPermission);

    /**
     * 导入dongle记录
     *
     * @param srcFile 源文件
     */
    public void importTechDongles(File srcFile);

    /**
     * 导出dongle记录
     *
     * @param targetFile 目标文件
     */
    public void exportTechDongles(File targetFile);

    /**
     * 查询dongle记录
     *
     * @return dongle记录列表
     */
    public List<TechDongle> queryTechDongles();

    /**
     * 保存/更新dongle记录
     *
     * @param techDongles dongle记录
     */
    public void saveOrUpdateTechDongles(TechDongle... techDongles);

    /**
     * 删除dongle记录
     *
     * @param techDongles dongle记录
     */
    public void deleteTechDongles(Object... techDongles);

    /**
     * 分页查询
     *
     * @param currentPage 当前页码
     * @param conditions  查询条件列表
     * @return 封装结果集的分页对象
     */
    public Pager<TechDongle> queryTechDonglePager(int currentPage, List<Condition> conditions);

    /**
     * 不分页查询
     *
     * @param conditions 查询条件列表
     * @return 结果集
     */
    public List<TechDongle> queryTechDongle(List<Condition> conditions);
}
