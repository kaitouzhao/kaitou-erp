package kaitou.ppp.dao.ts.impl;

import kaitou.ppp.dao.BaseDao;
import kaitou.ppp.dao.ts.TSManualPermissionsDao;
import kaitou.ppp.domain.ts.TSManualPermissions;

/**
 * TS手册权限管理DAO实现.
 * User: 赵立伟
 * Date: 2015/5/20
 * Time: 13:51
 */
public class TSManualPermissionsDaoImpl extends BaseDao<TSManualPermissions> implements TSManualPermissionsDao {
    @Override
    public Class<TSManualPermissions> getDomainClass() {
        return TSManualPermissions.class;
    }
}
