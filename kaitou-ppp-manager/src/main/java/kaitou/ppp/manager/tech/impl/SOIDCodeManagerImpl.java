package kaitou.ppp.manager.tech.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.domain.shop.ShopDetail;
import kaitou.ppp.domain.tech.SOIDCode;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.listener.ShopUpdateListener;
import kaitou.ppp.manager.tech.SOIDCodeManager;

import java.util.List;

/**
 * SOID识别码DAO事务控制层实现.
 * User: 赵立伟
 * Date: 2015/5/19
 * Time: 11:33
 */
public class SOIDCodeManagerImpl extends BaseFileDaoManager<SOIDCode> implements SOIDCodeManager, ShopUpdateListener {
    @Override
    public Class<SOIDCode> domainClass() {
        return SOIDCode.class;
    }

    @Override
    public List<SOIDCode> query() {
        return super.query();
    }

    @Override
    public void updateShopEvent(Shop... shops) {
        List<SOIDCode> soidCodeList = query();
        if (CollectionUtil.isEmpty(soidCodeList) || CollectionUtil.isEmpty(shops)) {
            return;
        }
        for (SOIDCode soidCode : soidCodeList) {
            for (Shop shop : shops) {
                if (shop.getId().equals(soidCode.getShopId())) {
                    soidCode.setShopName(shop.getName());
                    break;
                }
            }
        }
        save(soidCodeList);
    }

    @Override
    public void updateShopDetailEvent(ShopDetail... shopDetails) {

    }
}
