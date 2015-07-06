package kaitou.ppp.manager.ts.impl;

import kaitou.ppp.domain.ts.EngineerTS;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.ts.TSEngineerManager;

/**
 * TS工程师DAO事务控制层实现.
 * User: 赵立伟
 * Date: 2015/6/23
 * Time: 14:56
 */
public class TSEngineerManagerImpl extends BaseFileDaoManager<EngineerTS> implements TSEngineerManager {
    @Override
    public Class<EngineerTS> domainClass() {
        return EngineerTS.class;
    }
}
