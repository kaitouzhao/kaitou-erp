package kaitou.ppp.app.ui.table.queryobject;

import kaitou.ppp.domain.warranty.WarrantyPrint;

/**
 * 打印头保修查询对象.
 * User: 赵立伟
 * Date: 2015/5/18
 * Time: 12:41
 */
public class WarrantyPrintQueryObject extends BaseQueryObject<WarrantyPrint> {
    @Override
    public boolean autoResizeMode() {
        return false;
    }
}
