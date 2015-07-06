package kaitou.ppp.manager.ts.impl;

import kaitou.ppp.domain.ts.EngineerTS;
import kaitou.ppp.domain.ts.TSInstallPermission;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.listener.EngineerTSUpdateListener;
import kaitou.ppp.manager.ts.TSInstallPermissionManager;

import java.util.List;

/**
 * TS装机权限管理DAO事务控制层实现.
 * User: 赵立伟
 * Date: 2015/5/21
 * Time: 10:37
 */
public class TSInstallPermissionManagerImpl extends BaseFileDaoManager<TSInstallPermission> implements TSInstallPermissionManager, EngineerTSUpdateListener {
    @Override
    public Class<TSInstallPermission> domainClass() {
        return TSInstallPermission.class;
    }

    @Override
    public void updateEngineerEvent(EngineerTS... engineers) {
        List<TSInstallPermission> permissionList = queryAll();
        for (TSInstallPermission permission : permissionList) {
            for (EngineerTS engineerTS : engineers) {
                if (engineerTS.getEmployeeNo().equals(permission.getEmployeeNo())) {
                    permission.setEngineerName(engineerTS.getEngineerName());
                    permission.setSaleRegion(engineerTS.getSaleRegion());
                }
            }
        }
        save(permissionList);
    }
}
