package kaitou.ppp.dao.tech.impl;

import kaitou.ppp.dao.BaseDao;
import kaitou.ppp.dao.tech.TechSupportDao;
import kaitou.ppp.domain.tech.TechSupport;

/**
 * 技术支援管理DAO实现.
 * User: 赵立伟
 * Date: 2015/5/19
 * Time: 18:19
 */
public class TechSupportDaoImpl extends BaseDao<TechSupport> implements TechSupportDao {
    @Override
    public Class<TechSupport> getDomainClass() {
        return TechSupport.class;
    }
}
