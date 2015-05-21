package kaitou.ppp.manager.ts.impl;

import kaitou.ppp.domain.ts.TSSDSPermission;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.ts.TSSDSPermissionManager;

/**
 * TS SDS权限管理DAO事务控制层实现.
 * User: 赵立伟
 * Date: 2015/5/20
 * Time: 17:16
 */
public class TSSDSPermissionManagerImpl extends BaseFileDaoManager<TSSDSPermission> implements TSSDSPermissionManager {
    @Override
    public Class<TSSDSPermission> domainClass() {
        return TSSDSPermission.class;
    }
}
