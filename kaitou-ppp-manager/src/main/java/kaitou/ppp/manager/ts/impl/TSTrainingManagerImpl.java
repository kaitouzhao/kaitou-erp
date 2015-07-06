package kaitou.ppp.manager.ts.impl;

import kaitou.ppp.domain.ts.EngineerTS;
import kaitou.ppp.domain.ts.TSTraining;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.listener.EngineerTSUpdateListener;
import kaitou.ppp.manager.ts.TSTrainingManager;

import java.util.List;

/**
 * TS培训记录DAO事务控制层实现.
 * User: 赵立伟
 * Date: 2015/5/20
 * Time: 11:14
 */
public class TSTrainingManagerImpl extends BaseFileDaoManager<TSTraining> implements TSTrainingManager, EngineerTSUpdateListener {
    @Override
    public Class<TSTraining> domainClass() {
        return TSTraining.class;
    }

    @Override
    public void updateEngineerEvent(EngineerTS... engineers) {
        List<TSTraining> trainingList = queryAll();
        for (TSTraining training : trainingList) {
            for (EngineerTS engineerTS : engineers) {
                if (engineerTS.getEmployeeNo().equals(training.getEmployeeNo())) {
                    training.setEngineerName(engineerTS.getEngineerName());
                    training.setSaleRegion(engineerTS.getSaleRegion());
                }
            }
        }
        save(trainingList);
    }
}
