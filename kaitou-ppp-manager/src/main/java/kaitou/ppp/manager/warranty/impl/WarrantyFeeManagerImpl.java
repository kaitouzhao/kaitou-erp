package kaitou.ppp.manager.warranty.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.domain.shop.ShopDetail;
import kaitou.ppp.domain.warranty.WarrantyFee;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.listener.ShopUpdateListener;
import kaitou.ppp.manager.warranty.WarrantyFeeManager;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 保修费DAO事务管理层实现.
 * User: 赵立伟
 * Date: 2015/5/6
 * Time: 14:58
 */
public class WarrantyFeeManagerImpl extends BaseFileDaoManager<WarrantyFee> implements WarrantyFeeManager, ShopUpdateListener {
    @Override
    public Class<WarrantyFee> domainClass() {
        return WarrantyFee.class;
    }

    @Override
    public List<WarrantyFee> query() {
        List<WarrantyFee> warrantyFees = super.query();
        Collections.sort(warrantyFees, new Comparator<WarrantyFee>() {
            @Override
            public int compare(WarrantyFee o1, WarrantyFee o2) {
                try {
                    return Long.valueOf(o1.getWarrantyCardNo().substring(5)).compareTo(Long.valueOf(o2.getWarrantyCardNo().substring(5)));
                } catch (Exception e) {
                    return 0;
                }
            }
        });
        return warrantyFees;
    }

    @Override
    public void updateShopEvent(Shop... shops) {
        List<WarrantyFee> warrantyFees = query();
        if (CollectionUtil.isEmpty(warrantyFees) || CollectionUtil.isEmpty(shops)) {
            return;
        }
        for (WarrantyFee warrantyFee : warrantyFees) {
            for (Shop shop : shops) {
                if (shop.getId().equals(warrantyFee.getShopId())) {
                    warrantyFee.setShopName(shop.getName());
                    break;
                }
            }
        }
        save(warrantyFees);
    }

    @Override
    public void updateShopDetailEvent(ShopDetail... shopDetails) {

    }
}
