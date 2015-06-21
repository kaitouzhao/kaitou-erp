package kaitou.ppp.manager.tech.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.common.utils.FileUtil;
import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.domain.shop.ShopDetail;
import kaitou.ppp.domain.tech.TechSDSPermission;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.listener.ShopUpdateListener;
import kaitou.ppp.manager.tech.SDSPermissionManager;

import java.io.File;
import java.util.List;

/**
 * SDS权限管理DAO事务控制层实现.
 * User: 赵立伟
 * Date: 2015/5/19
 * Time: 16:46
 */
public class SDSPermissionManagerImpl extends BaseFileDaoManager<TechSDSPermission> implements SDSPermissionManager, ShopUpdateListener {
    @Override
    public Class<TechSDSPermission> domainClass() {
        return TechSDSPermission.class;
    }

    @Override
    public List<TechSDSPermission> query() {
        return super.query();
    }

    @Override
    public void updateShopEvent(Shop... shops) {
        List<TechSDSPermission> techSdsPermissionList = query();
        if (CollectionUtil.isEmpty(techSdsPermissionList) || CollectionUtil.isEmpty(shops)) {
            return;
        }
        for (TechSDSPermission techSdsPermissions : techSdsPermissionList) {
            for (Shop shop : shops) {
                if (shop.getId().equals(techSdsPermissions.getShopId())) {
                    techSdsPermissions.setShopName(shop.getName());
                    break;
                }
            }
        }
        save(techSdsPermissionList);
    }

    @Override
    public void updateShopDetailEvent(ShopDetail... shopDetails) {

    }

    @Override
    public void updateShopIdEvent(Shop... shops) {
        List<TechSDSPermission> permissionList = queryAll();
        FileUtil.delete(dbDir + File.separatorChar + "TechSDSPermission.kdb");
        for (TechSDSPermission permission : permissionList) {
            for (Shop shop : shops) {
                if (!shop.getName().equals(permission.getShopName())) {
                    continue;
                }
                permission.setShopId(shop.getId());
            }
        }
        save(permissionList);
    }
}
