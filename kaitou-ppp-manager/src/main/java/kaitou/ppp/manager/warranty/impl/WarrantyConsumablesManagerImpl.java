package kaitou.ppp.manager.warranty.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.domain.shop.ShopDetail;
import kaitou.ppp.domain.warranty.WarrantyConsumables;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.listener.ShopUpdateListener;
import kaitou.ppp.manager.warranty.WarrantyConsumablesManager;

import java.util.List;

/**
 * 耗材保修DAO事务控制层实现.
 * User: 赵立伟
 * Date: 2015/5/18
 * Time: 18:04
 */
public class WarrantyConsumablesManagerImpl extends BaseFileDaoManager<WarrantyConsumables> implements WarrantyConsumablesManager, ShopUpdateListener {
    @Override
    public Class<WarrantyConsumables> domainClass() {
        return WarrantyConsumables.class;
    }

    @Override
    public void updateShopEvent(Shop... shops) {
        List<WarrantyConsumables> warrantyConsumablesList = query();
        if (CollectionUtil.isEmpty(warrantyConsumablesList) || CollectionUtil.isEmpty(shops)) {
            return;
        }
        for (WarrantyConsumables warrantyConsumables : warrantyConsumablesList) {
            for (Shop shop : shops) {
                if (shop.getId().equals(warrantyConsumables.getShopId())) {
                    warrantyConsumables.setShopName(shop.getName());
                    break;
                }
            }
        }
        save(warrantyConsumablesList);
    }

    @Override
    public void updateShopDetailEvent(ShopDetail... shopDetails) {

    }
}
