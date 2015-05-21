package kaitou.ppp.dao.tech;

import kaitou.ppp.domain.tech.TechSDSPermission;

import java.util.List;

/**
 * SDS权限管理DAO.
 * User: 赵立伟
 * Date: 2015/5/19
 * Time: 16:42
 */
public interface SDSPermissionDao {

    /**
     * 添加/更新
     *
     * @param sdsPermissions SDS权限管理集合，可以是单个，也可是多个
     * @return 成功记录数
     */
    public int save(Object... sdsPermissions);

    /**
     * 查询SDS权限管理
     *
     * @return SDS权限管理
     */
    public List<TechSDSPermission> query();

    /**
     * 删除
     *
     * @param sdsPermissions 待删除集合。支持一个或多个
     * @return 成功执行记录数
     */
    public int delete(Object... sdsPermissions);
}
