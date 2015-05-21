package kaitou.ppp.manager.tech;

import kaitou.ppp.domain.tech.TechManualPermissions;

import java.util.List;

/**
 * 手册权限管理DAO事务控制层.
 * User: 赵立伟
 * Date: 2015/5/19
 * Time: 10:23
 */
public interface ManualPermissionsManager {

    /**
     * 导入手册权限
     *
     * @param techManualPermissions 手册权限列表
     * @return 成功执行个数
     */
    public int save(List<TechManualPermissions> techManualPermissions);

    /**
     * 获取手册权限
     *
     * @return 手册权限列表
     */
    public List<TechManualPermissions> query();

    /**
     * 删除
     *
     * @param manualPermissions 待删除集合。支持一个或多个
     * @return 成功执行条数
     */
    public int delete(Object... manualPermissions);
}
