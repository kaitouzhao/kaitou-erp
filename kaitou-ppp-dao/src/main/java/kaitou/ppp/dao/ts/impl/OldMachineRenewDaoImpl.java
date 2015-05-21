package kaitou.ppp.dao.ts.impl;

import kaitou.ppp.dao.BaseDao;
import kaitou.ppp.dao.ts.OldMachineRenewDao;
import kaitou.ppp.domain.ts.OldMachineRenew;

/**
 * 旧机翻新管理DAO实现.
 * User: 赵立伟
 * Date: 2015/5/21
 * Time: 12:46
 */
public class OldMachineRenewDaoImpl extends BaseDao<OldMachineRenew> implements OldMachineRenewDao {
    @Override
    public Class<OldMachineRenew> getDomainClass() {
        return OldMachineRenew.class;
    }
}
