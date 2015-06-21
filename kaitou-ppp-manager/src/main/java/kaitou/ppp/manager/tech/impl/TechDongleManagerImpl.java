package kaitou.ppp.manager.tech.impl;

import kaitou.ppp.domain.tech.TechDongle;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.tech.TechDongleManager;

/**
 * dongle记录DAO事务控制层实现.
 * User: 赵立伟
 * Date: 2015/6/13
 * Time: 23:48
 */
public class TechDongleManagerImpl extends BaseFileDaoManager<TechDongle> implements TechDongleManager {
    @Override
    public Class<TechDongle> domainClass() {
        return TechDongle.class;
    }
}
