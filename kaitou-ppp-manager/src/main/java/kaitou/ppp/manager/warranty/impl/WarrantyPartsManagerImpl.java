package kaitou.ppp.manager.warranty.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.domain.shop.ShopDetail;
import kaitou.ppp.domain.warranty.WarrantyParts;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.listener.ShopUpdateListener;
import kaitou.ppp.manager.warranty.WarrantyPartsManager;

import java.util.List;

/**
 * 保修零件及索赔零件DAO事务控制层实现.
 * User: 赵立伟
 * Date: 2015/5/10
 * Time: 16:18
 */
public class WarrantyPartsManagerImpl extends BaseFileDaoManager<WarrantyParts> implements WarrantyPartsManager, ShopUpdateListener {

    @Override
    public Class<WarrantyParts> domainClass() {
        return WarrantyParts.class;
    }

    @Override
    public void updateShopEvent(Shop... shops) {
        List<WarrantyParts> warrantyPartsList = query();
        if (CollectionUtil.isEmpty(warrantyPartsList) || CollectionUtil.isEmpty(shops)) {
            return;
        }
        for (WarrantyParts warrantyPart : warrantyPartsList) {
            for (Shop shop : shops) {
                if (shop.getId().equals(warrantyPart.getShopId())) {
                    warrantyPart.setShopName(shop.getName());
                    break;
                }
            }
        }
        save(warrantyPartsList);
    }

    @Override
    public void updateShopDetailEvent(ShopDetail... shopDetails) {

    }
}
