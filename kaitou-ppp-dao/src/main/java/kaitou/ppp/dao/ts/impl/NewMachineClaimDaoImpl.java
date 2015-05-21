package kaitou.ppp.dao.ts.impl;

import kaitou.ppp.dao.BaseDao;
import kaitou.ppp.dao.ts.NewMachineClaimDao;
import kaitou.ppp.domain.ts.NewMachineClaim;

/**
 * 新装机索赔管理DAO实现.
 * User: 赵立伟
 * Date: 2015/5/21
 * Time: 12:45
 */
public class NewMachineClaimDaoImpl extends BaseDao<NewMachineClaim> implements NewMachineClaimDao {
    @Override
    public Class<NewMachineClaim> getDomainClass() {
        return NewMachineClaim.class;
    }
}
