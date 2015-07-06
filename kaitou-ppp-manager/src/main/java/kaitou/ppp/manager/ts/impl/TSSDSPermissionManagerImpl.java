package kaitou.ppp.manager.ts.impl;

import kaitou.ppp.domain.ts.EngineerTS;
import kaitou.ppp.domain.ts.TSSDSPermission;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.listener.EngineerTSUpdateListener;
import kaitou.ppp.manager.ts.TSSDSPermissionManager;

import java.util.List;

/**
 * TS SDS权限管理DAO事务控制层实现.
 * User: 赵立伟
 * Date: 2015/5/20
 * Time: 17:16
 */
public class TSSDSPermissionManagerImpl extends BaseFileDaoManager<TSSDSPermission> implements TSSDSPermissionManager, EngineerTSUpdateListener {
    @Override
    public Class<TSSDSPermission> domainClass() {
        return TSSDSPermission.class;
    }

    @Override
    public void updateEngineerEvent(EngineerTS... engineers) {
        List<TSSDSPermission> permissionList = queryAll();
        for (TSSDSPermission permission : permissionList) {
            for (EngineerTS engineerTS : engineers) {
                if (engineerTS.getEmployeeNo().equals(permission.getEmployeeNo())) {
                    permission.setEngineerName(engineerTS.getEngineerName());
                    permission.setSaleRegion(engineerTS.getSaleRegion());
                    permission.setCity(engineerTS.getCity());
                    permission.setEmail(engineerTS.getEmail());
                }
            }
        }
        save(permissionList);
    }
}
