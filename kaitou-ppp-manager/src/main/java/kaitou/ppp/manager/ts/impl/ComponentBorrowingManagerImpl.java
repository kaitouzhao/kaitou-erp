package kaitou.ppp.manager.ts.impl;

import kaitou.ppp.domain.ts.ComponentBorrowing;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.ts.ComponentBorrowingManager;

/**
 * 零件借用管理DAO事务控制层实现.
 * User: 赵立伟
 * Date: 2015/5/21
 * Time: 15:05
 */
public class ComponentBorrowingManagerImpl extends BaseFileDaoManager<ComponentBorrowing> implements ComponentBorrowingManager {
    @Override
    public Class<ComponentBorrowing> domainClass() {
        return ComponentBorrowing.class;
    }
}
