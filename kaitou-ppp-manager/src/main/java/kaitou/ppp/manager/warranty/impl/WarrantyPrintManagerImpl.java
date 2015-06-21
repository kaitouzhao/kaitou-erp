package kaitou.ppp.manager.warranty.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.domain.shop.ShopDetail;
import kaitou.ppp.domain.warranty.WarrantyPrint;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.listener.ShopUpdateListener;
import kaitou.ppp.manager.warranty.WarrantyPrintManager;

import java.util.List;

/**
 * 打印头保修DAO事务控制层实现.
 * User: 赵立伟
 * Date: 2015/5/18
 * Time: 11:48
 */
public class WarrantyPrintManagerImpl extends BaseFileDaoManager<WarrantyPrint> implements WarrantyPrintManager, ShopUpdateListener {
    @Override
    public Class<WarrantyPrint> domainClass() {
        return WarrantyPrint.class;
    }

    @Override
    public void updateShopEvent(Shop... shops) {
        List<WarrantyPrint> warrantyPrints = query();
        if (CollectionUtil.isEmpty(warrantyPrints) || CollectionUtil.isEmpty(shops)) {
            return;
        }
        for (WarrantyPrint warrantyPrint : warrantyPrints) {
            for (Shop shop : shops) {
                if (shop.getId().equals(warrantyPrint.getShopId())) {
                    warrantyPrint.setShopName(shop.getName());
                    break;
                }
            }
        }
        save(warrantyPrints);
    }

    @Override
    public void updateShopDetailEvent(ShopDetail... shopDetails) {

    }

    @Override
    public void updateShopIdEvent(Shop... shops) {
        List<WarrantyPrint> warrantyPrintList = queryAll();
        for (WarrantyPrint warrantyPrint : warrantyPrintList) {
            for (Shop shop : shops) {
                if (!shop.getName().equals(warrantyPrint.getShopName())) {
                    continue;
                }
                warrantyPrint.setShopId(shop.getId());
            }
        }
        save(warrantyPrintList);
    }
}
