package kaitou.ppp.app.ui.table.queryobject;

import kaitou.ppp.domain.warranty.WarrantyFee;

/**
 * 保修费查询对象.
 * User: 赵立伟
 * Date: 2015/5/7
 * Time: 12:48
 */
public class WarrantyFeeQueryObject extends BaseQueryObject {
    @Override
    public Class domainClass() {
        return WarrantyFee.class;
    }
}
