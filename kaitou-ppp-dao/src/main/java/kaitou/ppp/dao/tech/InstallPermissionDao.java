package kaitou.ppp.dao.tech;

import kaitou.ppp.domain.tech.TechInstallPermission;

import java.util.List;

/**
 * 装机权限管理DAO.
 * User: 赵立伟
 * Date: 2015/5/19
 * Time: 19:04
 */
public interface InstallPermissionDao {

    /**
     * 添加/更新
     *
     * @param installPermissions 装机权限集合，可以是单个，也可是多个
     * @return 成功记录数
     */
    public int save(Object... installPermissions);

    /**
     * 查询装机权限
     *
     * @return 装机权限
     */
    public List<TechInstallPermission> queryAll();

    /**
     * 删除
     *
     * @param installPermissions 待删除集合。支持一个或多个
     * @return 成功执行记录数
     */
    public int delete(Object... installPermissions);
}
