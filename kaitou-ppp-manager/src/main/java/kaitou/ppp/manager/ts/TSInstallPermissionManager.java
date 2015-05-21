package kaitou.ppp.manager.ts;

import kaitou.ppp.domain.ts.TSInstallPermission;

import java.util.List;

/**
 * TS装机权限管理DAO事务控制层.
 * User: 赵立伟
 * Date: 2015/5/21
 * Time: 10:33
 */
public interface TSInstallPermissionManager {

    /**
     * 导入TS装机权限
     *
     * @param tsInstallPermissions TS装机权限列表
     * @return 成功执行个数
     */
    public int save(List<TSInstallPermission> tsInstallPermissions);

    /**
     * 获取TS装机权限
     *
     * @return TS装机权限
     */
    public List<TSInstallPermission> queryAll();

    /**
     * 删除
     *
     * @param tsInstallPermissions 待删除集合。支持一个或多个
     * @return 成功执行条数
     */
    public int delete(Object... tsInstallPermissions);
}
