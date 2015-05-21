package kaitou.ppp.manager.ts;

import kaitou.ppp.domain.ts.TSManualPermissions;

import java.util.List;

/**
 * TS手册权限管理DAO事务控制层.
 * User: 赵立伟
 * Date: 2015/5/20
 * Time: 13:53
 */
public interface TSManualPermissionsManager {

    /**
     * 导入TS手册权限
     *
     * @param tsManualPermissions TS手册权限列表
     * @return 成功执行个数
     */
    public int save(List<TSManualPermissions> tsManualPermissions);

    /**
     * 获取TS手册权限
     *
     * @return TS手册权限
     */
    public List<TSManualPermissions> queryAll();

    /**
     * 删除
     *
     * @param tsManualPermissions 待删除集合。支持一个或多个
     * @return 成功执行条数
     */
    public int delete(Object... tsManualPermissions);
}
