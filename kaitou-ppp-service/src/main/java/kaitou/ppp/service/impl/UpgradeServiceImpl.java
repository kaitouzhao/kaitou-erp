package kaitou.ppp.service.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.common.log.BaseLogManager;
import kaitou.ppp.common.utils.FileUtil;
import kaitou.ppp.domain.card.CardApplicationRecord;
import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.domain.shop.ShopPay;
import kaitou.ppp.domain.system.SysCode;
import kaitou.ppp.domain.ts.*;
import kaitou.ppp.domain.warranty.WarrantyFee;
import kaitou.ppp.manager.card.CardApplicationRecordManager;
import kaitou.ppp.manager.shop.ShopManager;
import kaitou.ppp.manager.shop.ShopPayManager;
import kaitou.ppp.manager.system.SystemSettingsManager;
import kaitou.ppp.manager.ts.*;
import kaitou.ppp.manager.warranty.WarrantyFeeManager;
import kaitou.ppp.service.ServiceInvokeManager;
import kaitou.ppp.service.UpgradeService;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.rmi.RemoteException;
import java.util.List;

import static com.womai.bsp.tool.utils.BeanCopyUtil.copyBean;
import static kaitou.ppp.common.utils.FileUtil.copy;
import static kaitou.ppp.common.utils.FileUtil.delete;
import static kaitou.ppp.service.ServiceInvokeManager.asynchronousRun;

/**
 * 版本升级操作实现.
 * User: 赵立伟
 * Date: 2015/2/8
 * Time: 0:15
 */
public class UpgradeServiceImpl extends BaseLogManager implements UpgradeService {
    /**
     * DB文件目录
     */
    private String dbDir;
    /**
     * 配置文件目录
     */
    private String confDir;
    private ShopManager shopManager;
    private ShopPayManager shopPayManager;

    private WarrantyFeeManager warrantyFeeManager;

    private SystemSettingsManager systemSettingsManager;

    private TsDongleManager tsDongleManager;
    private TSEngineerManager tsEngineerManager;
    private TSTrainingManager tsTrainingManager;
    private ToolRecipientsManager toolRecipientsManager;
    private TSSDSPermissionManager tssdsPermissionManager;
    private ComponentBorrowingManager componentBorrowingManager;
    private TSInstallPermissionManager tsInstallPermissionManager;
    private TSManualPermissionsManager tsManualPermissionsManager;

    private CardApplicationRecordManager cardApplicationRecordManager;

    public void setTsDongleManager(TsDongleManager tsDongleManager) {
        this.tsDongleManager = tsDongleManager;
    }

    public void setTsEngineerManager(TSEngineerManager tsEngineerManager) {
        this.tsEngineerManager = tsEngineerManager;
    }

    public void setTsTrainingManager(TSTrainingManager tsTrainingManager) {
        this.tsTrainingManager = tsTrainingManager;
    }

    public void setToolRecipientsManager(ToolRecipientsManager toolRecipientsManager) {
        this.toolRecipientsManager = toolRecipientsManager;
    }

    public void setTsSDSPermissionManager(TSSDSPermissionManager tssdsPermissionManager) {
        this.tssdsPermissionManager = tssdsPermissionManager;
    }

    public void setComponentBorrowingManager(ComponentBorrowingManager componentBorrowingManager) {
        this.componentBorrowingManager = componentBorrowingManager;
    }

    public void setTsInstallPermissionManager(TSInstallPermissionManager tsInstallPermissionManager) {
        this.tsInstallPermissionManager = tsInstallPermissionManager;
    }

    public void setTsManualPermissionsManager(TSManualPermissionsManager tsManualPermissionsManager) {
        this.tsManualPermissionsManager = tsManualPermissionsManager;
    }

    public void setWarrantyFeeManager(WarrantyFeeManager warrantyFeeManager) {
        this.warrantyFeeManager = warrantyFeeManager;
    }

    public void setShopManager(ShopManager shopManager) {
        this.shopManager = shopManager;
    }

    public void setShopPayManager(ShopPayManager shopPayManager) {
        this.shopPayManager = shopPayManager;
    }

    public void setSystemSettingsManager(SystemSettingsManager systemSettingsManager) {
        this.systemSettingsManager = systemSettingsManager;
    }

    public void setCardApplicationRecordManager(CardApplicationRecordManager cardApplicationRecordManager) {
        this.cardApplicationRecordManager = cardApplicationRecordManager;
    }

    public void setConfDir(String confDir) {
        this.confDir = confDir;
    }

    public void setDbDir(String dbDir) {
        this.dbDir = dbDir;
    }

    @Override
    public void upgradeTo1Dot4() {
        File file = new File(dbDir);
        File[] files = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith(SysCode.SaleRegion.EAST_CHINA.getValue()) || name.startsWith(SysCode.SaleRegion.SOUTH_CHINA.getValue()) || name.startsWith(SysCode.SaleRegion.NORTH_CHINA.getValue()) || name.startsWith(SysCode.SaleRegion.WEST_CHINA.getValue());
            }
        });
        if (CollectionUtil.isEmpty(files)) {
            return;
        }
        for (File dbFile : files) {
            String fileName = dbFile.getName();
            String newFileName = SysCode.SaleRegion.convert2Code(fileName.substring(0, fileName.indexOf('_'))) + fileName.substring(fileName.indexOf('_'), fileName.length());
            String dbFilePath = dbFile.getPath();
            logSystemInfo("原文件：" + dbFilePath);
            String newDbFile = dbFile.getParent() + File.separatorChar + newFileName;
            logSystemInfo("新文件：" + newDbFile);
            copy(dbFilePath, newDbFile);
            delete(dbFilePath);
        }
    }

    @Override
    public void upgradeTo2Dot1() {
        if ("2.1".equals(systemSettingsManager.getSystemSetting(SysCode.LATEST_VERSION_KEY))) {
            return;
        }
        String systemSettingsDB = dbDir + File.separatorChar + "SystemSettings.kdb";
        if (new File(systemSettingsDB).exists()) {
            copy(systemSettingsDB, confDir + File.separatorChar + "SystemSettings.conf");
            delete(systemSettingsDB);
        }
        String remoteRegistryDB = dbDir + File.separatorChar + "RemoteRegistry.kdb";
        if (new File(remoteRegistryDB).exists()) {
            copy(remoteRegistryDB, confDir + File.separatorChar + "RemoteRegistry.conf");
            delete(remoteRegistryDB);
        }
        List<CardApplicationRecord> cardApplicationRecords = cardApplicationRecordManager.query();
        cardApplicationRecordManager.delete(CollectionUtil.toArray(cardApplicationRecords, CardApplicationRecord.class));
        cardApplicationRecordManager.save(cardApplicationRecords);
    }

    @Override
    public void upgradeTo3Dot1() {
        if ("3.1".equals(systemSettingsManager.getSystemSetting(SysCode.LATEST_VERSION_KEY))) {
            return;
        }
        List<ShopPay> shopPays = shopPayManager.query();
        if (CollectionUtil.isNotEmpty(shopPays)) {
            List<Shop> shops = shopManager.query();
            for (Shop shop : shops) {
                for (ShopPay shopPay : shopPays) {
                    if (shopPay.getId().equals(shop.getId()) && shopPay.getName().equals(shop.getName())) {
                        copyBean(shopPay, shop);
                    }
                }
            }
            try {
                shopManager.save(shops);
            } catch (Exception e) {
                logSystemEx(e);
            }
        }
    }

    @Override
    public void upgradeTo3Dot2() {
        if ("3.2".equals(systemSettingsManager.getSystemSetting(SysCode.LATEST_VERSION_KEY))) {
            return;
        }
        List<WarrantyFee> warrantyFees = warrantyFeeManager.query();
        if (CollectionUtil.isEmpty(warrantyFees)) {
            return;
        }
        warrantyFeeManager.save(warrantyFees);
        FileUtil.delete(dbDir + File.separatorChar + "WarrantyFee.kdb");
    }

    @Override
    public void upgradeTo3Dot3() {
        if ("3.3".equals(systemSettingsManager.getSystemSetting(SysCode.LATEST_VERSION_KEY))) {
            return;
        }
        asynchronousRun(new ServiceInvokeManager.InvokeRunnable() {
            @Override
            public void run() throws RemoteException {
                List<CardApplicationRecord> cardApplicationRecords = cardApplicationRecordManager.query();
                for (CardApplicationRecord cardApplicationRecord : cardApplicationRecords) {
                    String shopId = shopManager.getCachedIdByName(cardApplicationRecord.getShopName());
                    if (StringUtils.isNotEmpty(shopId)) {
                        cardApplicationRecord.setShopId(shopId);
                        cardApplicationRecord.setSaleRegion(shopManager.getCachedShop(shopId).getSaleRegion());
                    }
                }
                logOperation("成功导入/更新保修卡生成记录数：" + cardApplicationRecordManager.save(cardApplicationRecords));
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public void upgradeTo3Dot4() {
        if ("3.4".equals(systemSettingsManager.getSystemSetting(SysCode.LATEST_VERSION_KEY))) {
            return;
        }
        List<Shop> shops = shopManager.query();
        for (Shop shop : shops) {
            if (!"PPP0191".equals(shop.getId())) {
                continue;
            }
            shop.setName("广西南宁八图数码信息有限公司");
            shopManager.save(CollectionUtil.newList(shop));
            break;
        }
    }

    @Override
    public void upgradeTo3Dot7() {
        if ("3.7".equals(systemSettingsManager.getSystemSetting(SysCode.LATEST_VERSION_KEY))) {
            return;
        }
        asynchronousRun(new ServiceInvokeManager.InvokeRunnable() {
            @Override
            public void run() throws RemoteException {
                List<EngineerTS> engineerTSList = tsEngineerManager.queryAll();
                List<ComponentBorrowing> componentBorrowingList = componentBorrowingManager.query();
                for (ComponentBorrowing borrowing : componentBorrowingList) {
                    for (EngineerTS engineerTS : engineerTSList) {
                        if (engineerTS.getEngineerName().equals(borrowing.getBorrowingPerson())) {
                            borrowing.setEmployeeNo(engineerTS.getEmployeeNo());
                        }
                    }
                }
                componentBorrowingManager.save(componentBorrowingList);
                List<ToolRecipients> toolRecipientsList = toolRecipientsManager.query();
                for (ToolRecipients toolRecipient : toolRecipientsList) {
                    for (EngineerTS engineerTS : engineerTSList) {
                        if (engineerTS.getEngineerName().equals(toolRecipient.getUseEngineerName())) {
                            toolRecipient.setEmployeeNo(engineerTS.getEmployeeNo());
                        }
                    }
                }
                toolRecipientsManager.save(toolRecipientsList);
                List<TSDongle> tsDongleList = tsDongleManager.queryAll();
                for (TSDongle dongle : tsDongleList) {
                    for (EngineerTS engineerTS : engineerTSList) {
                        if (engineerTS.getEngineerName().equals(dongle.getName())) {
                            dongle.setEmployeeNo(engineerTS.getEmployeeNo());
                        }
                    }
                }
                tsDongleManager.save(tsDongleList);
                List<TSInstallPermission> tsInstallPermissionList = tsInstallPermissionManager.queryAll();
                for (TSInstallPermission permission : tsInstallPermissionList) {
                    for (EngineerTS engineerTS : engineerTSList) {
                        if (engineerTS.getEngineerName().equals(permission.getEngineerName())) {
                            permission.setEmployeeNo(engineerTS.getEmployeeNo());
                        }
                    }
                }
                tsInstallPermissionManager.save(tsInstallPermissionList);
                List<TSManualPermissions> tsManualPermissionsList = tsManualPermissionsManager.queryAll();
                for (TSManualPermissions permission : tsManualPermissionsList) {
                    for (EngineerTS engineerTS : engineerTSList) {
                        if (engineerTS.getEngineerName().equals(permission.getEngineerName())) {
                            permission.setEmployeeNo(engineerTS.getEmployeeNo());
                        }
                    }
                }
                tsManualPermissionsManager.save(tsManualPermissionsList);
                List<TSSDSPermission> tsSDSPermissionList = tssdsPermissionManager.queryAll();
                for (TSSDSPermission permission : tsSDSPermissionList) {
                    for (EngineerTS engineerTS : engineerTSList) {
                        if (engineerTS.getEngineerName().equals(permission.getEngineerName())) {
                            permission.setEmployeeNo(engineerTS.getEmployeeNo());
                        }
                    }
                }
                tssdsPermissionManager.save(tsSDSPermissionList);
                List<TSTraining> tsTrainingList = tsTrainingManager.query();
                for (TSTraining training : tsTrainingList) {
                    for (EngineerTS engineerTS : engineerTSList) {
                        if (engineerTS.getEngineerName().equals(training.getEngineerName())) {
                            training.setEmployeeNo(engineerTS.getEmployeeNo());
                        }
                    }
                }
                tsTrainingManager.save(tsTrainingList);
            }
        });
    }
}
