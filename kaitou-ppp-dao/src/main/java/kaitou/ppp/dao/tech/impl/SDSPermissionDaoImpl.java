package kaitou.ppp.dao.tech.impl;

import kaitou.ppp.dao.BaseDao;
import kaitou.ppp.dao.tech.SDSPermissionDao;
import kaitou.ppp.domain.tech.TechSDSPermission;

/**
 * SDS权限管理DAO实现.
 * User: 赵立伟
 * Date: 2015/5/19
 * Time: 16:43
 */
public class SDSPermissionDaoImpl extends BaseDao<TechSDSPermission> implements SDSPermissionDao {
    @Override
    public Class<TechSDSPermission> getDomainClass() {
        return TechSDSPermission.class;
    }
}
