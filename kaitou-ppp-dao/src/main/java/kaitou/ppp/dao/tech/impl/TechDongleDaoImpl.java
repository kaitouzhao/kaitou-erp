package kaitou.ppp.dao.tech.impl;

import kaitou.ppp.dao.BaseDao;
import kaitou.ppp.dao.tech.TechDongleDao;
import kaitou.ppp.domain.tech.TechDongle;

/**
 * dongle记录DAO实现.
 * User: 赵立伟
 * Date: 2015/6/13
 * Time: 23:37
 */
public class TechDongleDaoImpl extends BaseDao<TechDongle> implements TechDongleDao {
    @Override
    public Class<TechDongle> getDomainClass() {
        return TechDongle.class;
    }
}
