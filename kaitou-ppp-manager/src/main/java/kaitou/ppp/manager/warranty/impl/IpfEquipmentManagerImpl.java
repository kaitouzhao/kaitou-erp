package kaitou.ppp.manager.warranty.impl;

import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.domain.shop.ShopDetail;
import kaitou.ppp.domain.warranty.IpfEquipment;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.listener.ShopUpdateListener;
import kaitou.ppp.manager.warranty.IpfEquipmentManager;

import java.util.List;

/**
 * iPF设备DAO事务控制层实现.
 * User: 赵立伟
 * Date: 2015/6/28
 * Time: 11:48
 */
public class IpfEquipmentManagerImpl extends BaseFileDaoManager<IpfEquipment> implements IpfEquipmentManager, ShopUpdateListener {
    @Override
    public Class<IpfEquipment> domainClass() {
        return IpfEquipment.class;
    }

    @Override
    public void updateShopEvent(Shop... shops) {
        List<IpfEquipment> equipments = queryAll();
        for (IpfEquipment equipment : equipments) {
            for (Shop shop : shops) {
                if (!shop.getId().equals(equipment.getShopId())) {
                    continue;
                }
                equipment.setShopName(shop.getName());
                equipment.setSaleRegion(shop.getSaleRegion());
                equipment.setShopArea(shop.getProvince());
            }
        }
        save(equipments);
    }

    @Override
    public void updateShopDetailEvent(ShopDetail... shopDetails) {

    }

    @Override
    public void updateShopIdEvent(Shop... shops) {
        List<IpfEquipment> equipments = queryAll();
        for (IpfEquipment equipment : equipments) {
            for (Shop shop : shops) {
                if (!shop.getName().equals(equipment.getShopName())) {
                    continue;
                }
                equipment.setShopId(shop.getId());
            }
        }
        save(equipments);
    }
}
