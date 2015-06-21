package kaitou.ppp.manager.warranty.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.common.utils.FileUtil;
import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.domain.shop.ShopDetail;
import kaitou.ppp.domain.warranty.WarrantyFee;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.listener.ShopUpdateListener;
import kaitou.ppp.manager.warranty.WarrantyFeeManager;

import java.io.File;
import java.io.FilenameFilter;
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

    @Override
    public void updateShopIdEvent(Shop... shops) {
        List<WarrantyFee> warrantyFeeList = queryAll();
        File[] files = new File(dbDir).listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith("WarrantyFee.kdb");
            }
        });
        for (File file : files) {
            FileUtil.delete(file.getPath());
        }
        for (WarrantyFee warrantyFee : warrantyFeeList) {
            for (Shop shop : shops) {
                if (!shop.getName().equals(warrantyFee.getShopName())) {
                    continue;
                }
                warrantyFee.setShopId(shop.getId());
            }
        }
        save(warrantyFeeList);
    }
}
