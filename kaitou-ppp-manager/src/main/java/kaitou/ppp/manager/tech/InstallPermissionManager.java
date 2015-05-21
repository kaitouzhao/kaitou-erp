package kaitou.ppp.manager.tech;

import kaitou.ppp.domain.tech.TechInstallPermission;

import java.util.List;

/**
 * 装机权限管理DAO事务控制层.
 * User: 赵立伟
 * Date: 2015/5/19
 * Time: 19:08
 */
public interface InstallPermissionManager {

    /**
     * 导入装机权限
     *
     * @param techInstallPermissions 装机权限列表
     * @return 成功执行个数
     */
    public int save(List<TechInstallPermission> techInstallPermissions);

    /**
     * 获取装机权限
     *
     * @return 装机权限列表
     */
    public List<TechInstallPermission> queryAll();

    /**
     * 删除
     *
     * @param installPermissions 待删除集合。支持一个或多个
     * @return 成功执行条数
     */
    public int delete(Object... installPermissions);
}
