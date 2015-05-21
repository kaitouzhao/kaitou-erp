package kaitou.ppp.manager.ts.impl;

import kaitou.ppp.domain.ts.TSTraining;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.ts.TSTrainingManager;

/**
 * TS培训记录DAO事务控制层实现.
 * User: 赵立伟
 * Date: 2015/5/20
 * Time: 11:14
 */
public class TSTrainingManagerImpl extends BaseFileDaoManager<TSTraining> implements TSTrainingManager {
    @Override
    public Class<TSTraining> domainClass() {
        return TSTraining.class;
    }
}
