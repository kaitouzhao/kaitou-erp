package kaitou.ppp.dao.tech.impl;

import kaitou.ppp.dao.BaseDao;
import kaitou.ppp.dao.tech.ManualPermissionsDao;
import kaitou.ppp.domain.tech.TechManualPermissions;

/**
 * 手册权限管理DAO实现.
 * User: 赵立伟
 * Date: 2015/5/19
 * Time: 10:20
 */
public class ManualPermissionsDaoImpl extends BaseDao<TechManualPermissions> implements ManualPermissionsDao {
    @Override
    public Class<TechManualPermissions> getDomainClass() {
        return TechManualPermissions.class;
    }
}
