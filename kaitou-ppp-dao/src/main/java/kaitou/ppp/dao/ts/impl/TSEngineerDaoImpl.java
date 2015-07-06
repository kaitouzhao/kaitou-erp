package kaitou.ppp.dao.ts.impl;

import kaitou.ppp.dao.BaseDao;
import kaitou.ppp.dao.ts.TSEngineerDao;
import kaitou.ppp.domain.ts.EngineerTS;

/**
 * TS工程师DAO实现.
 * User: 赵立伟
 * Date: 2015/6/23
 * Time: 14:53
 */
public class TSEngineerDaoImpl extends BaseDao<EngineerTS> implements TSEngineerDao {
    @Override
    public Class<EngineerTS> getDomainClass() {
        return EngineerTS.class;
    }
}
