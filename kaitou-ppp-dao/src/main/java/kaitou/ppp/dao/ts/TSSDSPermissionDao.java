package kaitou.ppp.dao.ts;

import kaitou.ppp.domain.ts.TSSDSPermission;

import java.util.List;

/**
 * TS SDS权限管理DAO.
 * User: 赵立伟
 * Date: 2015/5/20
 * Time: 17:11
 */
public interface TSSDSPermissionDao {

    /**
     * 添加/更新
     *
     * @param tsSDSPermissions TS SDS权限集合，可以是单个，也可是多个
     * @return 成功记录数
     */
    public int save(Object... tsSDSPermissions);

    /**
     * 查询TS SDS权限
     *
     * @return TS SDS权限
     */
    public List<TSSDSPermission> queryAll();

    /**
     * 删除
     *
     * @param tsSDSPermissions 待删除集合。支持一个或多个
     * @return 成功执行记录数
     */
    public int delete(Object... tsSDSPermissions);
}
