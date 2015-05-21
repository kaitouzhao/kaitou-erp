package kaitou.ppp.dao.tech;

import kaitou.ppp.domain.tech.TechManualPermissions;

import java.util.List;

/**
 * 手册权限管理DAO.
 * User: 赵立伟
 * Date: 2015/5/19
 * Time: 10:18
 */
public interface ManualPermissionsDao {

    /**
     * 添加/更新
     *
     * @param manualPermissions 手册权限集合，可以是单个，也可是多个
     * @return 成功记录数
     */
    public int save(Object... manualPermissions);

    /**
     * 查询手册权限
     *
     * @return 手册权限
     */
    public List<TechManualPermissions> query();

    /**
     * 删除
     *
     * @param manualPermissions 待删除集合。支持一个或多个
     * @return 成功执行记录数
     */
    public int delete(Object... manualPermissions);
}
