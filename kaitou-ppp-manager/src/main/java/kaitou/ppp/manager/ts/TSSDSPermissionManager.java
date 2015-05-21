package kaitou.ppp.manager.ts;

import kaitou.ppp.domain.ts.TSSDSPermission;

import java.util.List;

/**
 * TS SDS权限管理DAO事务控制层.
 * User: 赵立伟
 * Date: 2015/5/20
 * Time: 17:15
 */
public interface TSSDSPermissionManager {

    /**
     * 导入TS SDS权限
     *
     * @param tsSDSPermissions TS SDS权限列表
     * @return 成功执行个数
     */
    public int save(List<TSSDSPermission> tsSDSPermissions);

    /**
     * 获取TS SDS权限
     *
     * @return TS SDS权限
     */
    public List<TSSDSPermission> queryAll();

    /**
     * 删除
     *
     * @param tsSDSPermissions 待删除集合。支持一个或多个
     * @return 成功执行条数
     */
    public int delete(Object... tsSDSPermissions);
}
