package kaitou.ppp.manager.ts.impl;

import kaitou.ppp.domain.ts.TSInstallPermission;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.ts.TSInstallPermissionManager;

/**
 * TS装机权限管理DAO事务控制层实现.
 * User: 赵立伟
 * Date: 2015/5/21
 * Time: 10:37
 */
public class TSInstallPermissionManagerImpl extends BaseFileDaoManager<TSInstallPermission> implements TSInstallPermissionManager {
    @Override
    public Class<TSInstallPermission> domainClass() {
        return TSInstallPermission.class;
    }
}
