package kaitou.ppp.manager.tech.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.common.utils.FileUtil;
import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.domain.shop.ShopDetail;
import kaitou.ppp.domain.tech.TechInstallPermission;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.listener.ShopUpdateListener;
import kaitou.ppp.manager.tech.InstallPermissionManager;

import java.io.File;
import java.util.List;

/**
 * 装机权限管理DAO事务控制层实现.
 * User: 赵立伟
 * Date: 2015/5/19
 * Time: 19:09
 */
public class InstallPermissionManagerImpl extends BaseFileDaoManager<TechInstallPermission> implements InstallPermissionManager, ShopUpdateListener {
    @Override
    public Class<TechInstallPermission> domainClass() {
        return TechInstallPermission.class;
    }

    @Override
    public void updateShopEvent(Shop... shops) {
        List<TechInstallPermission> techInstallPermissionList = query();
        if (CollectionUtil.isEmpty(techInstallPermissionList) || CollectionUtil.isEmpty(shops)) {
            return;
        }
        for (TechInstallPermission techInstallPermissions : techInstallPermissionList) {
            for (Shop shop : shops) {
                if (shop.getId().equals(techInstallPermissions.getShopId())) {
                    techInstallPermissions.setShopName(shop.getName());
                    break;
                }
            }
        }
        save(techInstallPermissionList);
    }

    @Override
    public void updateShopDetailEvent(ShopDetail... shopDetails) {

    }

    @Override
    public void updateShopIdEvent(Shop... shops) {
        List<TechInstallPermission> permissions = queryAll();
        FileUtil.delete(dbDir + File.separatorChar + "TechInstallPermission.kdb");
        for (TechInstallPermission permission : permissions) {
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
