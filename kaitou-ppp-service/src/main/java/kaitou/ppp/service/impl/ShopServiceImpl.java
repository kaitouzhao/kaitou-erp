package kaitou.ppp.service.impl;

import com.womai.bsp.tool.utils.BeanCopyUtil;
import com.womai.bsp.tool.utils.CollectionUtil;
import com.womai.bsp.tool.utils.ExcelUtil;
import kaitou.ppp.domain.card.CardApplicationRecord;
import kaitou.ppp.domain.shop.*;
import kaitou.ppp.domain.warranty.IpfEquipment;
import kaitou.ppp.manager.card.CardApplicationRecordManager;
import kaitou.ppp.manager.listener.ShopUpdateListener;
import kaitou.ppp.manager.shop.*;
import kaitou.ppp.manager.system.RemoteRegistryManager;
import kaitou.ppp.manager.system.SystemSettingsManager;
import kaitou.ppp.manager.warranty.IpfEquipmentManager;
import kaitou.ppp.rmi.ServiceClient;
import kaitou.ppp.rmi.service.RemoteShopService;
import kaitou.ppp.service.BaseExcelService;
import kaitou.ppp.service.ShopService;
import org.joda.time.DateTime;

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static com.womai.bsp.tool.utils.BeanCopyUtil.copyBean;
import static kaitou.ppp.rmi.ServiceClient.queryServicesOfListener;
import static kaitou.ppp.service.ServiceInvokeManager.*;

/**
 * 认定店业务处理层实现.
 * User: 赵立伟
 * Date: 2015/1/25
 * Time: 13:24
 */
public class ShopServiceImpl extends BaseExcelService implements ShopService {
    private ShopManager shopManager;
    private ShopRTSManager shopRTSManager;
    private ShopPayManager shopPayManager;
    private ShopDetailManager shopDetailManager;
    private IpfEquipmentManager ipfEquipmentManager;
    private ShopContractManager shopContractManager;
    private PartsLibraryManager partsLibraryManager;
    private SystemSettingsManager systemSettingsManager;
    private RemoteRegistryManager remoteRegistryManager;
    private List<ShopUpdateListener> shopUpdateListeners;
    private CardApplicationRecordManager cardApplicationRecordManager;

    public void setPartsLibraryManager(PartsLibraryManager partsLibraryManager) {
        this.partsLibraryManager = partsLibraryManager;
    }

    public void setIpfEquipmentManager(IpfEquipmentManager ipfEquipmentManager) {
        this.ipfEquipmentManager = ipfEquipmentManager;
    }

    public void setShopContractManager(ShopContractManager shopContractManager) {
        this.shopContractManager = shopContractManager;
    }

    public void setSystemSettingsManager(SystemSettingsManager systemSettingsManager) {
        this.systemSettingsManager = systemSettingsManager;
    }

    public void setRemoteRegistryManager(RemoteRegistryManager remoteRegistryManager) {
        this.remoteRegistryManager = remoteRegistryManager;
    }

    public void setCardApplicationRecordManager(CardApplicationRecordManager cardApplicationRecordManager) {
        this.cardApplicationRecordManager = cardApplicationRecordManager;
    }

    public void setShopUpdateListeners(List<ShopUpdateListener> shopUpdateListeners) {
        this.shopUpdateListeners = shopUpdateListeners;
    }

    public void setShopRTSManager(ShopRTSManager shopRTSManager) {
        this.shopRTSManager = shopRTSManager;
    }

    public void setShopPayManager(ShopPayManager shopPayManager) {
        this.shopPayManager = shopPayManager;
    }

    public void setShopManager(ShopManager shopManager) {
        this.shopManager = shopManager;
    }

    public void setShopDetailManager(ShopDetailManager shopDetailManager) {
        this.shopDetailManager = shopDetailManager;
    }

    @Override
    public void importShops(File srcFile) {
        saveOrUpdateShop(CollectionUtil.toArray(readFromExcel(srcFile, Shop.class), Shop.class));
    }

    @Override
    public void exportShops(File targetFile) {
        export2Excel(shopManager.query(), targetFile, Shop.class);
    }

    @Override
    public void importShopDetails(File srcFile) {
        saveOrUpdateShopDetail(CollectionUtil.toArray(readFromExcel(srcFile, ShopDetail.class), ShopDetail.class));
    }

    @Override
    public void deleteShops(final Object... shops) {
        logOperation("成功删除认定店个数：" + shopManager.delete(shops));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteShopService> remoteShopServices = queryServicesOfListener(RemoteShopService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteShopServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务删除认定店基本信息");
                for (RemoteShopService remoteShopService : remoteShopServices) {
                    try {
                        remoteShopService.deleteShop(shops);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void deleteShopDetails(final Object... details) {
        logOperation("成功删除认定店认定级别个数：" + shopDetailManager.delete(details));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteShopService> remoteShopServices = queryServicesOfListener(RemoteShopService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteShopServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务删除认定店认定级别");
                for (RemoteShopService remoteShopService : remoteShopServices) {
                    try {
                        remoteShopService.deleteShopDetail(details);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void deleteShopPays(final Object... pays) {
        logOperation("成功删除认定店账户个数：" + shopPayManager.delete(pays));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteShopService> remoteShopServices = queryServicesOfListener(RemoteShopService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteShopServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务删除认定店账户");
                for (RemoteShopService remoteShopService : remoteShopServices) {
                    try {
                        remoteShopService.deleteShopPay(pays);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void deleteShopRTSs(final Object... rts) {
        logOperation("成功删除认定店RTS个数：" + shopRTSManager.delete(rts));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteShopService> remoteShopServices = queryServicesOfListener(RemoteShopService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteShopServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务删除认定店RTS");
                for (RemoteShopService remoteShopService : remoteShopServices) {
                    try {
                        remoteShopService.deleteShopRTS(rts);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void cacheAllShops() {
        shopManager.cachedShop();
        shopDetailManager.cacheShop();
    }

    @Override
    public void importRTSs(File srcFile) {
        saveOrUpdateShopRTS(CollectionUtil.toArray(readFromExcel(srcFile, ShopRTS.class), ShopRTS.class));
    }

    @Override
    public void exportRTSs(File targetFile) {
        export2Excel(shopRTSManager.query(), targetFile, ShopRTS.class);
    }

    @Override
    public void importPays(File srcFile) {
        saveOrUpdateShopPay(CollectionUtil.toArray(readFromExcel(srcFile, ShopPay.class), ShopPay.class));
    }

    @Override
    public void exportPays(File targetFile) {
        export2Excel(shopPayManager.query(), targetFile, ShopPay.class);
    }

    @Override
    public void exportAll(File targetFile) {
        List<Shop> allShops = shopManager.query();
        List<ShopAll> shopAlls = new ArrayList<ShopAll>();
        int currentYear = new DateTime().getYear();
        ShopAll shopAll;
        for (Shop shop : allShops) {
            List<CachedShopDetail> details = shopManager.queryCachedShopDetails(shop.getId());
            for (CachedShopDetail detail : details) {
                try {
                    if (currentYear > Integer.valueOf(detail.getNumberOfYear())) {
                        continue;
                    }
                    shopAll = new ShopAll();
                    copyBean(shop, shopAll);
                    copyBean(detail, shopAll);
                    shopAlls.add(shopAll);
                } catch (NumberFormatException e) {
                    logSystemInfo("认定年份不正确。认定店编码：" + shop.getId());
                }
            }
        }
        export2Excel(shopAlls, targetFile, ShopAll.class);
    }

    @Override
    public List<Shop> queryAllShops() {
        return shopManager.query();
    }

    @Override
    public List<ShopDetail> queryAllDetails() {
        return shopDetailManager.query();
    }

    @Override
    public List<ShopRTS> queryAllRTSs() {
        return shopRTSManager.query();
    }

    @Override
    public List<ShopPay> queryAllPays() {
        return shopPayManager.query();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void saveOrUpdateShop(final Shop... shop) {
        final List<Shop> shops = CollectionUtil.newList(shop);
        int successCount = shopManager.save(shops);
        logOperation("成功导入/更新认定店数：" + successCount);
        if (successCount <= 0) {
            return;
        }
        asynchronousRun(new InvokeRunnable() {
            @Override
            public void run() throws RemoteException {
                logOperation("联动更新与认定店基本信息相关的数据");
                for (ShopUpdateListener listener : shopUpdateListeners) {
                    listener.updateShopEvent(shop);
                }
            }
        });
        asynchronousRun(new InvokeRunnable() {
            @Override
            public void run() throws RemoteException {
                List<RemoteShopService> remoteShopServices = queryRemoteService(RemoteShopService.class);
                logOperation("通知已注册的远程服务更新认定店基本信息");
                for (RemoteShopService remoteShopService : remoteShopServices) {
                    remoteShopService.saveShops(shops);
                }
            }
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public void saveOrUpdateShopDetail(ShopDetail... detail) {
        final List<ShopDetail> shopDetails = CollectionUtil.newList(detail);
        int successCount = 0;
        if (CollectionUtil.isEmpty(shopDetails)) {
            logOperation("成功导入/更新认定店认定级别数：" + successCount);
            return;
        }
        for (ShopDetail shopDetail : shopDetails) {
            CachedShop cachedShop = shopManager.getCachedShop(shopDetail.getId());
            shopDetail.setSaleRegion(cachedShop.getSaleRegion());
        }
        successCount = shopDetailManager.save(shopDetails);
        logOperation("成功导入/更新认定店认定级别数：" + successCount);
        if (successCount <= 0) {
            return;
        }
        asynchronousRun(new InvokeRunnable() {
            @Override
            public void run() throws RemoteException {
                for (ShopUpdateListener listener : shopUpdateListeners) {
                    listener.updateShopDetailEvent(CollectionUtil.toArray(shopDetails, ShopDetail.class));
                }
            }
        });
        asynchronousRun(new InvokeRunnable() {
            @Override
            public void run() throws RemoteException {
                List<RemoteShopService> remoteShopServices = queryRemoteService(RemoteShopService.class);
                logOperation("通知已注册的远程服务更新认定店认定级别");
                for (RemoteShopService remoteShopService : remoteShopServices) {
                    remoteShopService.saveShopDetails(shopDetails);
                }
            }
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public void saveOrUpdateShopRTS(ShopRTS... rts) {
        final List shopRTSes = CollectionUtil.newList(rts);
        logOperation("成功导入/更新RTS数：" + shopRTSManager.save(shopRTSes));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteShopService> remoteShopServices = queryServicesOfListener(RemoteShopService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteShopServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新认定店RTS");
                for (RemoteShopService remoteShopService : remoteShopServices) {
                    try {
                        remoteShopService.saveShopRTSes(shopRTSes);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void saveOrUpdateShopPay(ShopPay... pay) {
        final List shopPays = CollectionUtil.newList(pay);
        logOperation("成功导入/更新认定店帐号信息数：" + shopPayManager.save(shopPays));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteShopService> remoteShopServices = queryServicesOfListener(RemoteShopService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteShopServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新认定店帐号信息");
                for (RemoteShopService remoteShopService : remoteShopServices) {
                    try {
                        remoteShopService.saveShopPays(shopPays);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void countShopEquipment(File targetFile) {
        List<CardApplicationRecord> cardApplicationRecords = cardApplicationRecordManager.query();
        List<ShopEquipmentCount> counts = new ArrayList<ShopEquipmentCount>();
        for (CardApplicationRecord cardApplicationRecord : cardApplicationRecords) {
            ShopEquipmentCount count = new ShopEquipmentCount();
            count.setShopId(cardApplicationRecord.getShopId());
            int index = counts.indexOf(count);
            if (index < 0) {
                count.setShopName(cardApplicationRecord.getShopName());
                counts.add(count);
            } else {
                count = counts.get(index);
            }
            if ("DGS".equals(cardApplicationRecord.getAllModels())) {
                count.setDgs(count.getDgs() + 1);
                continue;
            }
            if ("DP".equals(cardApplicationRecord.getAllModels())) {
                count.setDp(count.getDp() + 1);
                continue;
            }
            if ("PGA".equals(cardApplicationRecord.getAllModels())) {
                count.setPga(count.getPga() + 1);
                continue;
            }
            if ("TDS".equals(cardApplicationRecord.getAllModels())) {
                count.setTds(count.getTds() + 1);
                continue;
            }
            if ("IPF".equals(cardApplicationRecord.getAllModels())) {
                count.setIpf(count.getIpf() + 1);
            }
        }
        List<IpfEquipment> ipfEquipmentList = ipfEquipmentManager.queryAll();
        for (IpfEquipment ipfEquipment : ipfEquipmentList) {
            ShopEquipmentCount count = new ShopEquipmentCount();
            count.setShopId(ipfEquipment.getShopId());
            int index = counts.indexOf(count);
            if (index < 0) {
                count.setShopName(ipfEquipment.getShopName());
                counts.add(count);
            } else {
                count = counts.get(index);
            }
            count.setIpf(count.getIpf() + 1);
        }
        export2Excel(counts, targetFile, ShopEquipmentCount.class);
    }

    @Override
    public void importShopContracts(File srcFile) {
        saveOrUpdateShopContracts(CollectionUtil.toArray(readFromExcel(srcFile, ShopContract.class), ShopContract.class));
    }

    @Override
    public void exportShopContracts(File targetFile) {
        export2Excel(shopContractManager.query(), targetFile, ShopContract.class);
    }

    @Override
    public List<ShopContract> queryShopContracts() {
        return shopContractManager.query();
    }

    @Override
    public void saveOrUpdateShopContracts(ShopContract... shopContracts) {
        if (CollectionUtil.isEmpty(shopContracts)) {
            return;
        }
        final List<ShopContract> shopContractList = new ArrayList<ShopContract>();
        for (ShopContract shopContract : shopContracts) {
            shopContract.setShopId(shopManager.getCachedIdByName(shopContract.getShopName()));
            shopContractList.add(shopContract);
        }
        logOperation("成功导入/更新认定店合同信息数：" + shopContractManager.save(shopContractList));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteShopService> remoteShopServices = ServiceClient.queryServicesOfListener(RemoteShopService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteShopServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新认定店合同信息");
                for (RemoteShopService remoteShopService : remoteShopServices) {
                    try {
                        remoteShopService.saveShopContracts(shopContractList);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void deleteShopContract(final Object... shopContracts) {
        if (CollectionUtil.isEmpty(shopContracts)) {
            return;
        }
        logOperation("成功删除认定店合同信息数：" + shopContractManager.delete(shopContracts));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteShopService> remoteShopServices = ServiceClient.queryServicesOfListener(RemoteShopService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteShopServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新删除认定店合同信息");
                for (RemoteShopService remoteShopService : remoteShopServices) {
                    try {
                        remoteShopService.deleteShopContracts(shopContracts);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void importPartsLibrary(File srcFile) {
        saveOrUpdatePartsLibrary(CollectionUtil.toArray(readFromExcel(srcFile, PartsLibrary.class), PartsLibrary.class));
    }

    @Override
    public void exportPartsLibrary(File targetFile) {
        export2Excel(partsLibraryManager.query(), targetFile, PartsLibrary.class);
    }

    @Override
    public List<PartsLibrary> queryPartsLibrary() {
        return partsLibraryManager.query();
    }

    @Override
    public void saveOrUpdatePartsLibrary(PartsLibrary... partsLibraries) {
        if (CollectionUtil.isEmpty(partsLibraries)) {
            return;
        }
        final List<PartsLibrary> partsLibraryArrayList = new ArrayList<PartsLibrary>();
        for (PartsLibrary partLibrary : partsLibraries) {
            partLibrary.setShopId(shopManager.getCachedIdByName(partLibrary.getShopName()));
            partsLibraryArrayList.add(partLibrary);
        }
        logOperation("成功导入/更新零件备库数：" + partsLibraryManager.save(partsLibraryArrayList));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteShopService> remoteShopServices = ServiceClient.queryServicesOfListener(RemoteShopService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteShopServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新零件备库信息");
                for (RemoteShopService remoteShopService : remoteShopServices) {
                    try {
                        remoteShopService.savePartsLibrary(partsLibraryArrayList);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void deletePartsLibrary(final Object... partsLibraries) {
        if (CollectionUtil.isEmpty(partsLibraries)) {
            return;
        }
        logOperation("成功删除零件备库数：" + partsLibraryManager.delete(partsLibraries));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteShopService> remoteShopServices = ServiceClient.queryServicesOfListener(RemoteShopService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteShopServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新删除零件备库");
                for (RemoteShopService remoteShopService : remoteShopServices) {
                    try {
                        remoteShopService.deletePartsLibrary(partsLibraries);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void updateShopId(File srcFile) {
        List<String[]> dataList = ExcelUtil.readExcel(srcFile, "基础", 3, 2);
        List<Shop> shops = shopManager.query();
        List<Shop> newShops = new ArrayList<Shop>();
        for (Shop shop : shops) {
            Shop newShop = new Shop();
            BeanCopyUtil.copyBean(shop, newShop);
            for (String[] data : dataList) {
                if (!shop.getName().equals(data[1])) {
                    continue;
                }
                newShop.setId(data[0]);
                newShop.setProvince(data[2]);
            }
            newShops.add(newShop);
        }
        deleteShops(CollectionUtil.toArray(shops, Shop.class));
        saveOrUpdateShop(CollectionUtil.toArray(newShops, Shop.class));
        for (ShopUpdateListener listener : shopUpdateListeners) {
            listener.updateShopIdEvent(CollectionUtil.toArray(newShops, Shop.class));
        }
    }
}
