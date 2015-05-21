package kaitou.ppp.dao.ts.impl;

import kaitou.ppp.dao.BaseDao;
import kaitou.ppp.dao.ts.TSTrainingDao;
import kaitou.ppp.domain.ts.TSTraining;

/**
 * TS培训记录DAO实现.
 * User: 赵立伟
 * Date: 2015/5/20
 * Time: 10:57
 */
public class TSTrainingDaoImpl extends BaseDao<TSTraining> implements TSTrainingDao {
    @Override
    public Class<TSTraining> getDomainClass() {
        return TSTraining.class;
    }
}
