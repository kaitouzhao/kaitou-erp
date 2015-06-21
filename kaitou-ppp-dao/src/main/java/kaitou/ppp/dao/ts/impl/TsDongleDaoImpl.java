package kaitou.ppp.dao.ts.impl;

import kaitou.ppp.dao.BaseDao;
import kaitou.ppp.dao.ts.TsDongleDao;
import kaitou.ppp.domain.ts.TSDongle;

/**
 * dongle记录DAO实现.
 * User: 赵立伟
 * Date: 2015/6/13
 * Time: 23:39
 */
public class TsDongleDaoImpl extends BaseDao<TSDongle> implements TsDongleDao {
    @Override
    public Class<TSDongle> getDomainClass() {
        return TSDongle.class;
    }
}
