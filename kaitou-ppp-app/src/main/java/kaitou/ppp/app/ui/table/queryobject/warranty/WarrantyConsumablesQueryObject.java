package kaitou.ppp.app.ui.table.queryobject.warranty;

import kaitou.ppp.app.ui.table.queryobject.BaseQueryObject;
import kaitou.ppp.domain.warranty.WarrantyConsumables;

/**
 * 耗材保修查询对象.
 * User: 赵立伟
 * Date: 2015/5/18
 * Time: 18:17
 */
public class WarrantyConsumablesQueryObject extends BaseQueryObject<WarrantyConsumables> {
    @Override
    public boolean autoResizeMode() {
        return false;
    }
}
