package kaitou.ppp.service.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.domain.warranty.WarrantyFee;
import kaitou.ppp.domain.warranty.WarrantyParts;
import kaitou.ppp.manager.shop.ShopManager;
import kaitou.ppp.manager.system.RemoteRegistryManager;
import kaitou.ppp.manager.system.SystemSettingsManager;
import kaitou.ppp.manager.warranty.WarrantyFeeManager;
import kaitou.ppp.manager.warranty.WarrantyPartsManager;
import kaitou.ppp.rmi.ServiceClient;
import kaitou.ppp.rmi.service.RemoteWarrantyService;
import kaitou.ppp.service.BaseExcelService;
import kaitou.ppp.service.WarrantyService;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * 保修管理服务层实现.
 * User: 赵立伟
 * Date: 2015/5/7
 * Time: 10:14
 */
public class WarrantyServiceImpl extends BaseExcelService implements WarrantyService {

    private WarrantyFeeManager warrantyFeeManager;
    private WarrantyPartsManager warrantyPartsManager;
    private ShopManager shopManager;
    private SystemSettingsManager systemSettingsManager;
    private RemoteRegistryManager remoteRegistryManager;

    public void setShopManager(ShopManager shopManager) {
        this.shopManager = shopManager;
    }

    public void setSystemSettingsManager(SystemSettingsManager systemSettingsManager) {
        this.systemSettingsManager = systemSettingsManager;
    }

    public void setRemoteRegistryManager(RemoteRegistryManager remoteRegistryManager) {
        this.remoteRegistryManager = remoteRegistryManager;
    }

    public void setWarrantyPartsManager(WarrantyPartsManager warrantyPartsManager) {
        this.warrantyPartsManager = warrantyPartsManager;
    }

    public void setWarrantyFeeManager(WarrantyFeeManager warrantyFeeManager) {
        this.warrantyFeeManager = warrantyFeeManager;
    }

    @Override
    public void importWarrantyFee(File srcFile) {
        saveOrUpdateWarrantyFee(CollectionUtil.toArray(readFromExcel(srcFile, WarrantyFee.class), WarrantyFee.class));
    }

    @Override
    public void exportWarrantyFee(File targetFile) {
        export2Excel(warrantyFeeManager.query(), targetFile, WarrantyFee.class);
    }

    @Override
    public List<WarrantyFee> queryWarrantyFee() {
        return warrantyFeeManager.query();
    }

    @Override
    public void saveOrUpdateWarrantyFee(WarrantyFee... warrantyFee) {
        if (CollectionUtil.isEmpty(warrantyFee)) {
            return;
        }
        final List<WarrantyFee> warrantyFees = new ArrayList<WarrantyFee>();
        for (WarrantyFee fee : warrantyFee) {
            fee.noDoubt();
            fee.setShopId(shopManager.getCachedIdByName(fee.getShopName()));
            if (StringUtils.isEmpty(fee.getShopId())) {
                fee.doInDoubt();
            }
            warrantyFees.add(fee);
        }
        logOperation("成功导入/更新保修费记录数：" + warrantyFeeManager.save(warrantyFees));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteWarrantyService> remoteWarrantyServices = ServiceClient.queryServicesOfListener(RemoteWarrantyService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteWarrantyServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新保修费记录");
                for (RemoteWarrantyService remoteWarrantyService : remoteWarrantyServices) {
                    try {
                        remoteWarrantyService.saveWarrantyFee(warrantyFees);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void deleteWarrantyFee(final Object... warrantyFee) {
        if (CollectionUtil.isEmpty(warrantyFee)) {
            return;
        }
        logOperation("成功删除保修费记录个数：" + warrantyFeeManager.delete(warrantyFee));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteWarrantyService> remoteWarrantyServices = ServiceClient.queryServicesOfListener(RemoteWarrantyService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteWarrantyServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新删除保修费记录");
                for (RemoteWarrantyService remoteWarrantyService : remoteWarrantyServices) {
                    try {
                        remoteWarrantyService.deleteWarrantyFee(warrantyFee);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void importWarrantyParts(File srcFile) {
        saveOrUpdateWarrantyParts(CollectionUtil.toArray(readFromExcel(srcFile, WarrantyParts.class), WarrantyParts.class));
    }

    @Override
    public void exportWarrantyParts(File targetFile, String... numberOfYear) {
        export2Excel(warrantyPartsManager.query(numberOfYear), targetFile, WarrantyParts.class);
    }

    @Override
    public List<WarrantyParts> queryWarrantyParts() {
        return warrantyPartsManager.query();
    }

    @Override
    public void saveOrUpdateWarrantyParts(WarrantyParts... warrantyParts) {
        if (CollectionUtil.isEmpty(warrantyParts)) {
            return;
        }
        final List<WarrantyParts> warrantyPartsList = new ArrayList<WarrantyParts>();
        for (WarrantyParts parts : warrantyParts) {
            parts.setShopId(shopManager.getCachedIdByName(parts.getShopName()));
            warrantyPartsList.add(parts);
        }
        logOperation("成功导入/更新保修及索赔零件数：" + warrantyPartsManager.save(warrantyPartsList));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteWarrantyService> remoteWarrantyServices = ServiceClient.queryServicesOfListener(RemoteWarrantyService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteWarrantyServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新保修及索赔零件");
                for (RemoteWarrantyService remoteWarrantyService : remoteWarrantyServices) {
                    try {
                        remoteWarrantyService.saveWarrantyParts(warrantyPartsList);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void deleteWarrantyParts(final Object... warrantyParts) {
        if (CollectionUtil.isEmpty(warrantyParts)) {
            return;
        }
        logOperation("成功删除保修及索赔零件个数：" + warrantyPartsManager.delete(warrantyParts));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteWarrantyService> remoteWarrantyServices = ServiceClient.queryServicesOfListener(RemoteWarrantyService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteWarrantyServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新删除保修及索赔零件");
                for (RemoteWarrantyService remoteWarrantyService : remoteWarrantyServices) {
                    try {
                        remoteWarrantyService.deleteWarrantyParts(warrantyParts);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }
}
