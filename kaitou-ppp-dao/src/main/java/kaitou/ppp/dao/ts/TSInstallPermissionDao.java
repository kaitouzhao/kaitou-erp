package kaitou.ppp.dao.ts;

import kaitou.ppp.domain.ts.TSInstallPermission;

import java.util.List;

/**
 * TS装机权限管理DAO.
 * User: 赵立伟
 * Date: 2015/5/21
 * Time: 10:29
 */
public interface TSInstallPermissionDao {

    /**
     * 添加/更新
     *
     * @param tsInstallPermissions TS装机权限集合，可以是单个，也可是多个
     * @return 成功记录数
     */
    public int save(Object... tsInstallPermissions);

    /**
     * 查询TS装机权限
     *
     * @return TS装机权限
     */
    public List<TSInstallPermission> queryAll();

    /**
     * 删除
     *
     * @param tsInstallPermissions 待删除集合。支持一个或多个
     * @return 成功执行记录数
     */
    public int delete(Object... tsInstallPermissions);
}
