package kaitou.ppp.manager.ts.impl;

import kaitou.ppp.domain.ts.ComponentBorrowing;
import kaitou.ppp.domain.ts.EngineerTS;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.listener.EngineerTSUpdateListener;
import kaitou.ppp.manager.ts.ComponentBorrowingManager;

import java.util.List;

/**
 * 零件借用管理DAO事务控制层实现.
 * User: 赵立伟
 * Date: 2015/5/21
 * Time: 15:05
 */
public class ComponentBorrowingManagerImpl extends BaseFileDaoManager<ComponentBorrowing> implements ComponentBorrowingManager, EngineerTSUpdateListener {
    @Override
    public Class<ComponentBorrowing> domainClass() {
        return ComponentBorrowing.class;
    }

    @Override
    public void updateEngineerEvent(EngineerTS... engineers) {
        List<ComponentBorrowing> componentBorrowingList = queryAll();
        for (ComponentBorrowing borrowing : componentBorrowingList) {
            for (EngineerTS engineerTS : engineers) {
                if (engineerTS.getEmployeeNo().equals(borrowing.getEmployeeNo())) {
                    borrowing.setBorrowingPerson(engineerTS.getEngineerName());
                }
            }
        }
        save(componentBorrowingList);
    }
}
