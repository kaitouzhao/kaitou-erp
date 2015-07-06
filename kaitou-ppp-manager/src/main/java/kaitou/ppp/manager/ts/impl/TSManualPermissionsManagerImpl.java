package kaitou.ppp.manager.ts.impl;

import kaitou.ppp.domain.ts.EngineerTS;
import kaitou.ppp.domain.ts.TSManualPermissions;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.listener.EngineerTSUpdateListener;
import kaitou.ppp.manager.ts.TSManualPermissionsManager;

import java.util.List;

/**
 * TS手册权限管理DAO事务控制层实现.
 * User: 赵立伟
 * Date: 2015/5/20
 * Time: 13:54
 */
public class TSManualPermissionsManagerImpl extends BaseFileDaoManager<TSManualPermissions> implements TSManualPermissionsManager, EngineerTSUpdateListener {
    @Override
    public Class<TSManualPermissions> domainClass() {
        return TSManualPermissions.class;
    }

    @Override
    public void updateEngineerEvent(EngineerTS... engineers) {
        List<TSManualPermissions> permissionsList = queryAll();
        for (TSManualPermissions permission : permissionsList) {
            for (EngineerTS engineerTS : engineers) {
                if (engineerTS.getEmployeeNo().equals(permission.getEmployeeNo())) {
                    permission.setEngineerName(engineerTS.getEngineerName());
                    permission.setSaleRegion(engineerTS.getSaleRegion());
                    permission.setEmail(engineerTS.getEmail());
                }
            }
        }
        save(permissionsList);
    }
}
