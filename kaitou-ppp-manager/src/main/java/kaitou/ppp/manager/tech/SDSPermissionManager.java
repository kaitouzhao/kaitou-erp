package kaitou.ppp.manager.tech;

import kaitou.ppp.domain.tech.TechSDSPermission;

import java.util.List;

/**
 * SDS权限管理DAO事务控制层.
 * User: 赵立伟
 * Date: 2015/5/19
 * Time: 16:45
 */
public interface SDSPermissionManager {

    /**
     * 导入SDS权限管理
     *
     * @param techSdsPermissions SDS权限管理列表
     * @return 成功执行个数
     */
    public int save(List<TechSDSPermission> techSdsPermissions);

    /**
     * 获取SDS权限管理
     *
     * @return SDS权限管理列表
     */
    public List<TechSDSPermission> query();

    /**
     * 删除
     *
     * @param sdsPermissions 待删除集合。支持一个或多个
     * @return 成功执行条数
     */
    public int delete(Object... sdsPermissions);
}
