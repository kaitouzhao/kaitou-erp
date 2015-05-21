package kaitou.ppp.dao.ts.impl;

import kaitou.ppp.dao.BaseDao;
import kaitou.ppp.dao.ts.TSInstallPermissionDao;
import kaitou.ppp.domain.ts.TSInstallPermission;

/**
 * TS装机权限管理DAO实现.
 * User: 赵立伟
 * Date: 2015/5/21
 * Time: 10:31
 */
public class TSInstallPermissionDaoImpl extends BaseDao<TSInstallPermission> implements TSInstallPermissionDao {
    @Override
    public Class<TSInstallPermission> getDomainClass() {
        return TSInstallPermission.class;
    }
}
