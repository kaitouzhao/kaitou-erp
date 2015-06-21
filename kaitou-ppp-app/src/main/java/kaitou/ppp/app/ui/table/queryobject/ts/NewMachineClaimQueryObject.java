package kaitou.ppp.app.ui.table.queryobject.ts;

import kaitou.ppp.app.ui.table.queryobject.BaseQueryObject;
import kaitou.ppp.domain.ts.NewMachineClaim;

/**
 * 新装机索赔查询对象.
 * User: 赵立伟
 * Date: 2015/5/21
 * Time: 13:13
 */
public class NewMachineClaimQueryObject extends BaseQueryObject<NewMachineClaim> {
    @Override
    public boolean autoResizeMode() {
        return false;
    }
}
