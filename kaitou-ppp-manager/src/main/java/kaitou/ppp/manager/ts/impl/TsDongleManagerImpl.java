package kaitou.ppp.manager.ts.impl;

import kaitou.ppp.domain.ts.TSDongle;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.ts.TsDongleManager;

/**
 * dongle记录DAO事务控制层实现.
 * User: 赵立伟
 * Date: 2015/6/13
 * Time: 23:51
 */
public class TsDongleManagerImpl extends BaseFileDaoManager<TSDongle> implements TsDongleManager {
    @Override
    public Class<TSDongle> domainClass() {
        return TSDongle.class;
    }
}
