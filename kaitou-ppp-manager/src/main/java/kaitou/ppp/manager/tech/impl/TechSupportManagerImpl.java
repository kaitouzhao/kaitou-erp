package kaitou.ppp.manager.tech.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.domain.shop.ShopDetail;
import kaitou.ppp.domain.tech.TechSupport;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.listener.ShopUpdateListener;
import kaitou.ppp.manager.tech.TechSupportManager;

import java.util.List;

/**
 * 技术支援管理DAO事务控制层实现.
 * User: 赵立伟
 * Date: 2015/5/19
 * Time: 18:24
 */
public class TechSupportManagerImpl extends BaseFileDaoManager<TechSupport> implements TechSupportManager, ShopUpdateListener {
    @Override
    public Class<TechSupport> domainClass() {
        return TechSupport.class;
    }

    @Override
    public void updateShopEvent(Shop... shops) {
        List<TechSupport> techSupportList = query();
        if (CollectionUtil.isEmpty(techSupportList) || CollectionUtil.isEmpty(shops)) {
            return;
        }
        for (TechSupport techSupport : techSupportList) {
            for (Shop shop : shops) {
                if (shop.getId().equals(techSupport.getShopId())) {
                    techSupport.setShopName(shop.getName());
                    break;
                }
            }
        }
        save(techSupportList);
    }

    @Override
    public void updateShopDetailEvent(ShopDetail... shopDetails) {

    }
}
