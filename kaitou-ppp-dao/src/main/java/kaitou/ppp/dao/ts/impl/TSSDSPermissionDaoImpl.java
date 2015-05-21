package kaitou.ppp.dao.ts.impl;

import kaitou.ppp.dao.BaseDao;
import kaitou.ppp.dao.ts.TSSDSPermissionDao;
import kaitou.ppp.domain.ts.TSSDSPermission;

/**
 * TS SDS权限管理DAO实现.
 * User: 赵立伟
 * Date: 2015/5/20
 * Time: 17:13
 */
public class TSSDSPermissionDaoImpl extends BaseDao<TSSDSPermission> implements TSSDSPermissionDao {
    @Override
    public Class<TSSDSPermission> getDomainClass() {
        return TSSDSPermission.class;
    }
}
