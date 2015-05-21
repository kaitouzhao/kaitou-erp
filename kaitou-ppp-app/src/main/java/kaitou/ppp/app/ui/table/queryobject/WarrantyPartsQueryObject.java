package kaitou.ppp.app.ui.table.queryobject;

import kaitou.ppp.domain.warranty.WarrantyParts;

/**
 * 保修及索赔零件查询对象.
 * User: 赵立伟
 * Date: 2015/5/10
 * Time: 16:45
 */
public class WarrantyPartsQueryObject extends BaseQueryObject<WarrantyParts> {

    @Override
    public boolean autoResizeMode() {
        return false;
    }
}
