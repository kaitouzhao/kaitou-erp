package kaitou.ppp.manager.shop.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.domain.shop.ShopContract;
import kaitou.ppp.domain.shop.ShopDetail;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.listener.ShopUpdateListener;
import kaitou.ppp.manager.shop.ShopContractManager;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 认定店合同信息DAO事务控制层实现.
 * User: 赵立伟
 * Date: 2015/5/17
 * Time: 21:27
 */
public class ShopContractManagerImpl extends BaseFileDaoManager<ShopContract> implements ShopContractManager, ShopUpdateListener {
    @Override
    public Class<ShopContract> domainClass() {
        return ShopContract.class;
    }

    @Override
    public List<ShopContract> query() {
        List<ShopContract> shopContracts = super.query();
        Collections.sort(shopContracts, new Comparator<ShopContract>() {
            @Override
            public int compare(ShopContract o1, ShopContract o2) {
                try {
                    return Long.valueOf(o1.getShopId().substring(3)).compareTo(Long.valueOf(o2.getShopId().substring(3)));
                } catch (Exception e) {
                    return 0;
                }
            }
        });
        return shopContracts;
    }

    @Override
    public void updateShopEvent(Shop... shops) {
        List<ShopContract> shopContracts = query();
        if (CollectionUtil.isEmpty(shopContracts) || CollectionUtil.isEmpty(shops)) {
            return;
        }
        for (ShopContract shopContract : shopContracts) {
            for (Shop shop : shops) {
                if (shop.getId().equals(shopContract.getShopId())) {
                    shopContract.setShopName(shop.getName());
                    break;
                }
            }
        }
        save(shopContracts);
    }

    @Override
    public void updateShopDetailEvent(ShopDetail... shopDetails) {

    }
}
