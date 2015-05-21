package kaitou.ppp.dao.ts.impl;

import kaitou.ppp.dao.BaseDao;
import kaitou.ppp.dao.ts.ComponentBorrowingDao;
import kaitou.ppp.domain.ts.ComponentBorrowing;

/**
 * 零件借用管理DAO实现.
 * User: 赵立伟
 * Date: 2015/5/21
 * Time: 14:59
 */
public class ComponentBorrowingDaoImpl extends BaseDao<ComponentBorrowing> implements ComponentBorrowingDao {
    @Override
    public Class<ComponentBorrowing> getDomainClass() {
        return ComponentBorrowing.class;
    }
}
