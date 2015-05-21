package kaitou.ppp.manager.ts.impl;

import kaitou.ppp.domain.ts.NewMachineClaim;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.ts.NewMachineClaimManager;

/**
 * 新装机索赔管理DAO事务控制层实现.
 * User: 赵立伟
 * Date: 2015/5/21
 * Time: 12:52
 */
public class NewMachineClaimManagerImpl extends BaseFileDaoManager<NewMachineClaim> implements NewMachineClaimManager {
    @Override
    public Class<NewMachineClaim> domainClass() {
        return NewMachineClaim.class;
    }
}
