package kaitou.ppp.manager.ts.impl;

import kaitou.ppp.domain.ts.TSManualPermissions;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.ts.TSManualPermissionsManager;

/**
 * TS手册权限管理DAO事务控制层实现.
 * User: 赵立伟
 * Date: 2015/5/20
 * Time: 13:54
 */
public class TSManualPermissionsManagerImpl extends BaseFileDaoManager<TSManualPermissions> implements TSManualPermissionsManager {
    @Override
    public Class<TSManualPermissions> domainClass() {
        return TSManualPermissions.class;
    }
}
