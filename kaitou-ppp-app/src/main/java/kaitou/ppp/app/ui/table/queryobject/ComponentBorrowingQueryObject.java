package kaitou.ppp.app.ui.table.queryobject;

import kaitou.ppp.domain.ts.ComponentBorrowing;

/**
 * 零件借用查询对象.
 * User: 赵立伟
 * Date: 2015/5/21
 * Time: 15:25
 */
public class ComponentBorrowingQueryObject extends BaseQueryObject<ComponentBorrowing> {
    @Override
    public boolean autoResizeMode() {
        return false;
    }
}
