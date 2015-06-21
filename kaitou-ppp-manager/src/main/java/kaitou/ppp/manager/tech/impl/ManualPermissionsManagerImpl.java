package kaitou.ppp.manager.tech.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.common.utils.FileUtil;
import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.domain.shop.ShopDetail;
import kaitou.ppp.domain.tech.TechManualPermissions;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.listener.ShopUpdateListener;
import kaitou.ppp.manager.tech.ManualPermissionsManager;

import java.io.File;
import java.util.List;

/**
 * 手册权限管理DAO事务控制层实现.
 * User: 赵立伟
 * Date: 2015/5/19
 * Time: 10:25
 */
public class ManualPermissionsManagerImpl extends BaseFileDaoManager<TechManualPermissions> implements ManualPermissionsManager, ShopUpdateListener {
    @Override
    public Class<TechManualPermissions> domainClass() {
        return TechManualPermissions.class;
    }

    @Override
    public List<TechManualPermissions> query() {
        return super.query();
    }

    @Override
    public void updateShopEvent(Shop... shops) {
        List<TechManualPermissions> techManualPermissionsList = query();
        if (CollectionUtil.isEmpty(techManualPermissionsList) || CollectionUtil.isEmpty(shops)) {
            return;
        }
        for (TechManualPermissions techManualPermissions : techManualPermissionsList) {
            for (Shop shop : shops) {
                if (shop.getId().equals(techManualPermissions.getShopId())) {
                    techManualPermissions.setShopName(shop.getName());
                    break;
                }
            }
        }
        save(techManualPermissionsList);
    }

    @Override
    public void updateShopDetailEvent(ShopDetail... shopDetails) {

    }

    @Override
    public void updateShopIdEvent(Shop... shops) {
        List<TechManualPermissions> permissions = queryAll();
        FileUtil.delete(dbDir + File.separatorChar + "TechManualPermissions.kdb");
        for (TechManualPermissions permission : permissions) {
            for (Shop shop : shops) {
                if (!shop.getName().equals(permission.getShopName())) {
                    continue;
                }
                permission.setShopId(shop.getId());
            }
        }
        save(permissions);
    }
}
