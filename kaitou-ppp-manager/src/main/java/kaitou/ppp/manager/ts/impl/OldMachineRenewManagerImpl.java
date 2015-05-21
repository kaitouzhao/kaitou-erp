package kaitou.ppp.manager.ts.impl;

import kaitou.ppp.domain.ts.OldMachineRenew;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.ts.OldMachineRenewManager;

/**
 * 旧机翻新管理DAO事务控制层实现.
 * User: 赵立伟
 * Date: 2015/5/21
 * Time: 12:51
 */
public class OldMachineRenewManagerImpl extends BaseFileDaoManager<OldMachineRenew> implements OldMachineRenewManager {
    @Override
    public Class<OldMachineRenew> domainClass() {
        return OldMachineRenew.class;
    }
}
