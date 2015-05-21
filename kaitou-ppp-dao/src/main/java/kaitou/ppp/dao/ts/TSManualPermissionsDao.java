package kaitou.ppp.dao.ts;

import kaitou.ppp.domain.ts.TSManualPermissions;

import java.util.List;

/**
 * TS手册权限管理DAO.
 * User: 赵立伟
 * Date: 2015/5/20
 * Time: 13:49
 */
public interface TSManualPermissionsDao {

    /**
     * 添加/更新
     *
     * @param tsManualPermissions TS手册权限集合，可以是单个，也可是多个
     * @return 成功记录数
     */
    public int save(Object... tsManualPermissions);

    /**
     * 查询TS手册权限
     *
     * @return TS手册权限
     */
    public List<TSManualPermissions> queryAll();

    /**
     * 删除
     *
     * @param tsManualPermissions 待删除集合。支持一个或多个
     * @return 成功执行记录数
     */
    public int delete(Object... tsManualPermissions);
}
