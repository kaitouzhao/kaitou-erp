package kaitou.ppp.dao.tech.impl;

import kaitou.ppp.dao.BaseDao;
import kaitou.ppp.dao.tech.InstallPermissionDao;
import kaitou.ppp.domain.tech.TechInstallPermission;

/**
 * 装机权限管理DAO实现.
 * User: 赵立伟
 * Date: 2015/5/19
 * Time: 19:06
 */
public class InstallPermissionDaoImpl extends BaseDao<TechInstallPermission> implements InstallPermissionDao {
    @Override
    public Class<TechInstallPermission> getDomainClass() {
        return TechInstallPermission.class;
    }
}
