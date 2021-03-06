package kaitou.ppp.service.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.dao.support.Condition;
import kaitou.ppp.dao.support.Pager;
import kaitou.ppp.domain.shop.CachedShop;
import kaitou.ppp.domain.warranty.*;
import kaitou.ppp.manager.listener.WarrantyUpdateListener;
import kaitou.ppp.manager.shop.ShopManager;
import kaitou.ppp.manager.system.RemoteRegistryManager;
import kaitou.ppp.manager.system.SystemSettingsManager;
import kaitou.ppp.manager.warranty.*;
import kaitou.ppp.rmi.ServiceClient;
import kaitou.ppp.rmi.service.RemoteWarrantyService;
import kaitou.ppp.service.BaseExcelService;
import kaitou.ppp.service.WarrantyService;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static kaitou.ppp.service.ServiceInvokeManager.*;

/**
 * 保修管理服务层实现.
 * User: 赵立伟
 * Date: 2015/5/7
 * Time: 10:14
 */
public class WarrantyServiceImpl extends BaseExcelService implements WarrantyService {

    private ShopManager shopManager;
    private WarrantyFeeManager warrantyFeeManager;
    private IpfEquipmentManager ipfEquipmentManager;
    private WarrantyPartsManager warrantyPartsManager;
    private WarrantyPrintManager warrantyPrintManager;
    private SystemSettingsManager systemSettingsManager;
    private RemoteRegistryManager remoteRegistryManager;
    private WarrantyConsumablesManager warrantyConsumablesManager;
    private List<WarrantyUpdateListener> warrantyUpdateListeners;

    public void setWarrantyUpdateListeners(List<WarrantyUpdateListener> warrantyUpdateListeners) {
        this.warrantyUpdateListeners = warrantyUpdateListeners;
    }

    public void setIpfEquipmentManager(IpfEquipmentManager ipfEquipmentManager) {
        this.ipfEquipmentManager = ipfEquipmentManager;
    }

    public void setWarrantyConsumablesManager(WarrantyConsumablesManager warrantyConsumablesManager) {
        this.warrantyConsumablesManager = warrantyConsumablesManager;
    }

    public void setWarrantyPrintManager(WarrantyPrintManager warrantyPrintManager) {
        this.warrantyPrintManager = warrantyPrintManager;
    }

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
    public void importIpfEquipment(File srcFile) {
        saveOrUpdateIpfEquipment(CollectionUtil.toArray(readFromExcel(srcFile, IpfEquipment.class), IpfEquipment.class));
    }

    @Override
    public void exportIpfEquipment(File targetFile) {
        export2Excel(ipfEquipmentManager.queryAll(), targetFile, IpfEquipment.class);
    }

    @Override
    public List<IpfEquipment> queryIpfEquipment() {
        return ipfEquipmentManager.queryAll();
    }

    @Override
    public Pager<IpfEquipment> queryIpfEquipmentPager(int currentPage, List<Condition> conditions) {
        return ipfEquipmentManager.queryPager(currentPage, conditions);
    }

    @Override
    public List<IpfEquipment> queryIpfEquipment(List<Condition> conditions) {
        return ipfEquipmentManager.queryAll(conditions);
    }

    @Override
    public void saveOrUpdateIpfEquipment(IpfEquipment... equipments) {
        if (CollectionUtil.isEmpty(equipments)) {
            return;
        }
        final List<IpfEquipment> equipmentList = new ArrayList<IpfEquipment>();
        for (IpfEquipment equipment : equipments) {
            String shopId = shopManager.getCachedIdByName(equipment.getShopName());
            equipment.setShopId(shopId);
            if (!StringUtils.isEmpty(shopId)) {
                CachedShop shop = shopManager.getCachedShop(shopId);
                equipment.setSaleRegion(shop.getSaleRegion());
                equipment.setShopArea(shop.getProvince());
            }
            equipmentList.add(equipment);
        }
        logOperation("成功导入/更新iPF设备数：" + ipfEquipmentManager.save(equipmentList));
        asynchronousRun(new InvokeRunnable() {
            @Override
            public void run() throws RemoteException {
                List<RemoteWarrantyService> remoteWarrantyServices = queryRemoteService(RemoteWarrantyService.class);
                logOperation("通知已注册的远程服务更新iPF设备");
                for (RemoteWarrantyService remoteWarrantyService : remoteWarrantyServices) {
                    remoteWarrantyService.saveIpfEquipment(equipmentList);
                }
            }
        });
    }

    @Override
    public void deleteIpfEquipment(final Object... equipments) {
        logOperation("成功删除iPF设备个数：" + ipfEquipmentManager.delete(equipments));
        asynchronousRun(new InvokeRunnable() {
            @Override
            public void run() throws RemoteException {
                List<RemoteWarrantyService> remoteWarrantyServices = queryRemoteService(RemoteWarrantyService.class);
                logOperation("通知已注册的远程服务更新删除iPF设备");
                for (RemoteWarrantyService remoteWarrantyService : remoteWarrantyServices) {
                    remoteWarrantyService.deleteIpfEquipment(equipments);
                }
            }
        });
    }

    @Override
    public void importWarrantyFee(File srcFile) {
        saveOrUpdateWarrantyFee(CollectionUtil.toArray(readFromExcel(srcFile, WarrantyFee.class), WarrantyFee.class));
    }

    @Override
    public void exportWarrantyFee(File targetFile, String... numberOfYear) {
        export2Excel(warrantyFeeManager.query(numberOfYear), targetFile, WarrantyFee.class);
    }

    @Override
    public List<WarrantyFee> queryWarrantyFee() {
        return warrantyFeeManager.query();
    }

    @Override
    public void saveOrUpdateWarrantyFee(final WarrantyFee... warrantyFee) {
        if (CollectionUtil.isEmpty(warrantyFee)) {
            return;
        }
        final List<WarrantyFee> warrantyFees = new ArrayList<WarrantyFee>();
        for (WarrantyFee fee : warrantyFee) {
            fee.setShopId(shopManager.getCachedIdByName(fee.getShopName()));
            warrantyFees.add(fee);
        }
        logOperation("成功导入/更新保修费记录数：" + warrantyFeeManager.save(warrantyFees));
        asynchronousRun(new InvokeRunnable() {
            @Override
            public void run() throws RemoteException {
                logOperation("联动更新与保修费相关的数据");
                for (WarrantyUpdateListener warrantyUpdateListener : warrantyUpdateListeners) {
                    warrantyUpdateListener.updateWarrantyFeeEvent(warrantyFee);
                }
            }
        });
        asynchronousRun(new InvokeRunnable() {
            @Override
            public void run() throws RemoteException {
                List<RemoteWarrantyService> remoteWarrantyServices = queryRemoteService(RemoteWarrantyService.class);
                logOperation("通知已注册的远程服务更新保修费记录");
                for (RemoteWarrantyService remoteWarrantyService : remoteWarrantyServices) {
                    remoteWarrantyService.saveWarrantyFee(warrantyFees);
                }
            }
        });
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
    public Pager<WarrantyParts> queryWarrantyPartsPager(int currentPage, List<Condition> conditions) {
        return warrantyPartsManager.queryPager(currentPage, conditions);
    }

    @Override
    public List<WarrantyParts> queryWarrantyParts(List<Condition> conditions) {
        return warrantyPartsManager.queryAll(conditions);
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

    @Override
    public void importWarrantyPrint(File srcFile) {
        saveOrUpdateWarrantyPrint(CollectionUtil.toArray(readFromExcel(srcFile, WarrantyPrint.class), WarrantyPrint.class));
    }

    @Override
    public void exportWarrantyPrint(File targetFile, String... numberOfYear) {
        export2Excel(warrantyPrintManager.query(numberOfYear), targetFile, WarrantyPrint.class);
    }

    @Override
    public List<WarrantyPrint> queryWarrantyPrint() {
        return warrantyPrintManager.query();
    }

    @Override
    public void saveOrUpdateWarrantyPrint(WarrantyPrint... warrantyPrint) {
        if (CollectionUtil.isEmpty(warrantyPrint)) {
            return;
        }
        final List<WarrantyPrint> warrantyPrints = new ArrayList<WarrantyPrint>();
        for (WarrantyPrint print : warrantyPrint) {
            print.setShopId(shopManager.getCachedIdByName(print.getShopName()));
            warrantyPrints.add(print);
        }
        logOperation("成功导入/更新打印头保修数：" + warrantyPrintManager.save(warrantyPrints));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteWarrantyService> remoteWarrantyServices = ServiceClient.queryServicesOfListener(RemoteWarrantyService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteWarrantyServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新打印头保修");
                for (RemoteWarrantyService remoteWarrantyService : remoteWarrantyServices) {
                    try {
                        remoteWarrantyService.saveWarrantyPrint(warrantyPrints);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void deleteWarrantyPrint(final Object... warrantyPrint) {
        if (CollectionUtil.isEmpty(warrantyPrint)) {
            return;
        }
        logOperation("成功删除打印头保修数：" + warrantyPrintManager.delete(warrantyPrint));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteWarrantyService> remoteWarrantyServices = ServiceClient.queryServicesOfListener(RemoteWarrantyService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteWarrantyServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新删除打印头保修");
                for (RemoteWarrantyService remoteWarrantyService : remoteWarrantyServices) {
                    try {
                        remoteWarrantyService.deleteWarrantyPrint(warrantyPrint);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void importWarrantyConsumables(File srcFile) {
        saveOrUpdateWarrantyConsumables(CollectionUtil.toArray(readFromExcel(srcFile, WarrantyConsumables.class), WarrantyConsumables.class));
    }

    @Override
    public void exportWarrantyConsumables(File targetFile, String... numberOfYear) {
        export2Excel(warrantyConsumablesManager.query(numberOfYear), targetFile, WarrantyConsumables.class);
    }

    @Override
    public List<WarrantyConsumables> queryWarrantyConsumables() {
        return warrantyConsumablesManager.query();
    }

    @Override
    public void saveOrUpdateWarrantyConsumables(WarrantyConsumables... warrantyConsumables) {
        if (CollectionUtil.isEmpty(warrantyConsumables)) {
            return;
        }
        final List<WarrantyConsumables> warrantyConsumablesList = new ArrayList<WarrantyConsumables>();
        for (WarrantyConsumables warrantConsumable : warrantyConsumables) {
            warrantConsumable.setShopId(shopManager.getCachedIdByName(warrantConsumable.getShopName()));
            warrantyConsumablesList.add(warrantConsumable);
        }
        logOperation("成功导入/更新耗材保修数：" + warrantyConsumablesManager.save(warrantyConsumablesList));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteWarrantyService> remoteWarrantyServices = ServiceClient.queryServicesOfListener(RemoteWarrantyService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteWarrantyServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新耗材保修");
                for (RemoteWarrantyService remoteWarrantyService : remoteWarrantyServices) {
                    try {
                        remoteWarrantyService.saveWarrantyConsumables(warrantyConsumablesList);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void deleteWarrantyConsumables(final Object... warrantyConsumables) {
        if (CollectionUtil.isEmpty(warrantyConsumables)) {
            return;
        }
        logOperation("成功删除耗材保修数：" + warrantyConsumablesManager.delete(warrantyConsumables));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteWarrantyService> remoteWarrantyServices = ServiceClient.queryServicesOfListener(RemoteWarrantyService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteWarrantyServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新删除耗材保修");
                for (RemoteWarrantyService remoteWarrantyService : remoteWarrantyServices) {
                    try {
                        remoteWarrantyService.deleteWarrantyConsumables(warrantyConsumables);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }
}
