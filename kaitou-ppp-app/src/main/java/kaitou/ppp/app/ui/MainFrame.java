/*
 * Created by JFormDesigner on Thu Feb 05 10:56:52 CST 2015
 */

package kaitou.ppp.app.ui;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.app.ui.dialog.InputHint;
import kaitou.ppp.app.ui.dialog.OnlineConfig;
import kaitou.ppp.app.ui.dialog.OperationHint;
import kaitou.ppp.app.ui.dialog.ReportErrorHint;
import kaitou.ppp.app.ui.table.QueryFrame;
import kaitou.ppp.app.ui.table.QueryFrameNew;
import kaitou.ppp.app.ui.table.queryobject.*;
import kaitou.ppp.common.utils.NetworkUtil;
import kaitou.ppp.domain.card.CardApplicationRecord;
import kaitou.ppp.domain.engineer.Engineer;
import kaitou.ppp.domain.engineer.EngineerTraining;
import kaitou.ppp.domain.shop.*;
import kaitou.ppp.domain.system.DBVersion;
import kaitou.ppp.domain.tech.*;
import kaitou.ppp.domain.ts.*;
import kaitou.ppp.domain.warranty.WarrantyConsumables;
import kaitou.ppp.domain.warranty.WarrantyFee;
import kaitou.ppp.domain.warranty.WarrantyParts;
import kaitou.ppp.domain.warranty.WarrantyPrint;
import kaitou.ppp.rmi.ServiceClient;
import kaitou.ppp.rmi.service.RemoteDBVersionService;
import kaitou.ppp.rmi.service.RemoteRegistryService;
import kaitou.ppp.service.LocalDBVersionService;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import static com.womai.bsp.tool.utils.PropertyUtil.getValue;
import static kaitou.ppp.app.SpringContextManager.*;
import static kaitou.ppp.app.ui.UIUtil.*;

/**
 * 主界面
 *
 * @author 赵立伟
 */
public class MainFrame extends JFrame {

    private static final String FRAME_TITLE = "PPP-ERP主界面";
    private static final int UPGRADE_DB_VERSIONS_WAIT_TIME = 3000;
    private static boolean isOnline = false;
    private static boolean syncOk = false;

    /**
     * 联机状态
     */
    private static enum OnlineStatus {
        ONLINE_PREPARING_FLAG("（联机中......）"), ONLINE_FLAG("（已联机）"), OFFLINE_FLAG("（未联机）");
        private String displayValue;

        OnlineStatus(String displayValue) {
            this.displayValue = displayValue;
        }

    }

    /**
     * 启动入口
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        asynchronousInit();

        new MainFrame();
    }

    /**
     * 异步初始化
     * <ul>
     * <li>更新系统设置</li>
     * <li>备份DB</li>
     * <li>缓存认定店</li>
     * </ul>
     */
    private static void asynchronousInit() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getSystemSettingsService().updateSystemSettings();
                getDbService().backupDB();
                getShopService().cacheAllShops();
            }
        }).start();
    }

    /**
     * 异步联机
     * <ul>
     * <li>如果本机IP未设置，则不予联机</li>
     * <li>如果本机是主机，则启动服务，等待注册</li>
     * <li>如果本机非主机，则启动服务，注册</li>
     * </ul>
     */
    private void asynchronousOnline() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String localIp = NetworkUtil.getLocalIp();
                    if ("127.0.0.1".equals(localIp)) {
                        localIp = getSystemSettingsService().getLocalIp();
                    }
                    List<String> remoteRegistryIps = getLocalRegistryService().queryRegistryIps();
                    if (StringUtils.isEmpty(localIp) || CollectionUtil.isEmpty(remoteRegistryIps)) {
                        setTitleByOnlineStatus(OnlineStatus.OFFLINE_FLAG);
                        return;
                    }
                    if (!getServiceProvider().start(localIp)) {
                        setTitleByOnlineStatus(OnlineStatus.OFFLINE_FLAG);
                        return;
                    }
                    logOp("IP：" + localIp);
                    logOp("已启动服务监听");
                    getSystemSettingsService().updateLocalIp(localIp);
                    getLocalRegistryService().updateRegistryIps(CollectionUtil.newList(localIp));
                    setTitleByOnlineStatus(OnlineStatus.ONLINE_FLAG);
                    for (String hostIp : remoteRegistryIps) {
                        if (localIp.equals(hostIp)) {
                            continue;
                        }
                        RemoteRegistryService remoteRegistryService = ServiceClient.getRemoteService(RemoteRegistryService.class, hostIp);
                        if (remoteRegistryService == null) {
                            continue;
                        }
                        List<String> registryIpsFromHost = remoteRegistryService.register(localIp);
                        logOp("远程注册成功，待更新IP列表：" + CollectionUtil.list2Str(registryIpsFromHost, ","));
                        getLocalRegistryService().updateRegistryIps(registryIpsFromHost);
                    }
                } catch (RemoteException e) {
                    logSystemEx(e);
                    setTitleByOnlineStatus(OnlineStatus.OFFLINE_FLAG);
                }
            }
        }).start();
    }

    /**
     * 根据联机状态变更窗体标题
     *
     * @param onlineStatus 联机状态
     */
    private void setTitleByOnlineStatus(OnlineStatus onlineStatus) {
        if (OnlineStatus.ONLINE_FLAG == onlineStatus) {
            isOnline = true;
        }
        setTitle(FRAME_TITLE + onlineStatus.displayValue);
        setVisible(true);
    }

    public MainFrame() {
        asynchronousOnline();

        initComponents();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setTitleByOnlineStatus(OnlineStatus.ONLINE_PREPARING_FLAG);

        asynchronousUpgradeDBVersions();
    }

    /**
     * 异步同步DB版本库
     */
    private void asynchronousUpgradeDBVersions() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(UPGRADE_DB_VERSIONS_WAIT_TIME);
                } catch (InterruptedException e) {
                    logSystemEx(e);
                }
                LocalDBVersionService localDBVersionService = getLocalDBVersionService();
                localDBVersionService.cacheDBLatestVersion();
                logOp("已缓存本地DB版本库");
                List<RemoteDBVersionService> remoteDBVersionServices = ServiceClient.queryServicesOfListener(RemoteDBVersionService.class, getLocalRegistryService().queryRegistryIps(), getSystemSettingsService().getLocalIp());
                if (CollectionUtil.isNotEmpty(remoteDBVersionServices)) {
                    try {
                        for (RemoteDBVersionService remoteDBVersionService : remoteDBVersionServices) {
                            List<DBVersion> remoteDBVersions = remoteDBVersionService.queryRemoteDBVersions();
                            if (CollectionUtil.isEmpty(remoteDBVersions)) {
                                continue;
                            }
                            List<DBVersion> toUpgradeList = localDBVersionService.getToUpgradeList(remoteDBVersions);
                            if (CollectionUtil.isEmpty(toUpgradeList)) {
                                continue;
                            }
                            Map<DBVersion, List<String>> toUpgradeDBMap = remoteDBVersionService.queryRemoteDBs(toUpgradeList);
                            if (CollectionUtil.isEmpty(toUpgradeDBMap)) {
                                continue;
                            }
                            localDBVersionService.upgradeByRemoteDBs(toUpgradeDBMap);
                        }
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
                syncOk = true;
            }
        }).start();
    }

    private void importShopBasicActionPerformed(ActionEvent e) {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getShopService().importShops(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void exportShopBasicActionPerformed(ActionEvent e) {
        try {
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            getShopService().exportShops(targetFile);
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void queryShopBasicActionPerformed(ActionEvent e) {
        new QueryFrame<Shop>(getShopService().queryAllShops(), new ShopQueryObject());
    }

    private void queryEngineerBasicActionPerformed(ActionEvent e) {
        new QueryFrame<Engineer>(getEngineerService().queryAllEngineers(), new EngineerQueryObject());
    }

    private void aboutItemActionPerformed(ActionEvent e) {
        new OperationHint(this, "当前系统版本：" + getValue("config.properties", "version"));
    }

    private void backupDBActionPerformed(ActionEvent e) {
        try {
            File targetFile = chooseExportFile("压缩文件", "zip");
            if (targetFile == null) return;
            getDbService().backupDB(targetFile.getPath());
            new OperationHint(this, "备份成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void recoveryDBActionPerformed(ActionEvent e) {
        try {
            File srcFile = chooseImportFile("压缩文件", "zip");
            if (srcFile == null) return;
            getDbService().recovery(srcFile.getPath());
            new OperationHint(this, "恢复成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void importEngineerBasicActionPerformed(ActionEvent e) {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getEngineerService().importEngineers(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void exportEngineerBasicActionPerformed(ActionEvent e) {
        try {
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            getEngineerService().exportEngineers(targetFile);
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void importEngineerTrainingActionPerformed(ActionEvent e) {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getEngineerService().importEngineerTrainings(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void queryEngineerTrainingActionPerformed(ActionEvent e) {
        new QueryFrame<EngineerTraining>(getEngineerService().queryAllTrainings(), new EngineerTrainingQueryObject());
    }

    private void exportEngineerTrainingActionPerformed(ActionEvent e) {
        try {
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            getEngineerService().exportTrainings(targetFile);
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void countEngineerByProductLineActionPerformed(ActionEvent e) {
        try {
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            getEngineerService().countEngineersByProductLine(targetFile);
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void countEngineerByShopActionPerformed(ActionEvent e) {
        try {
            InputHint inputHint = new InputHint(this, new String[]{"请选择产品线"});
            if (!inputHint.isOk()) {
                return;
            }
            String productLine = inputHint.getInputResult()[0];
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            getEngineerService().countEngineersByShop(productLine, targetFile);
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void importShopDetailActionPerformed(ActionEvent e) {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getShopService().importShopDetails(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void queryShopDetailActionPerformed(ActionEvent e) {
        new QueryFrame<ShopDetail>(getShopService().queryAllDetails(), new ShopDetailQueryObject());
    }

    private void exportShopDetailActionPerformed(ActionEvent e) {
        try {
            InputHint inputHint = new InputHint(this, new String[]{"导出认定年份"});
            if (!inputHint.isOk()) {
                return;
            }
            String numberOfYear = inputHint.getInputResult()[0];
            File targetFile = chooseExportFile("excel文件", "xls");
            if (targetFile == null) return;
            if (StringUtils.isEmpty(numberOfYear)) {
                getExportService().exportShopDetails(targetFile);
            } else {
                getExportService().exportShopDetails(targetFile, numberOfYear);
            }
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void importShopRTSActionPerformed(ActionEvent e) {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getShopService().importRTSs(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void queryShopRTSActionPerformed(ActionEvent e) {
        new QueryFrame<ShopRTS>(getShopService().queryAllRTSs(), new ShopRTSQueryObject());
    }

    private void exportShopRTSActionPerformed(ActionEvent e) {
        try {
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            getShopService().exportRTSs(targetFile);
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void importShopPayActionPerformed(ActionEvent e) {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getShopService().importPays(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void queryShopPayActionPerformed(ActionEvent e) {
        new QueryFrame<ShopPay>(getShopService().queryAllPays(), new ShopPayQueryObject());
    }

    private void exportShopPayActionPerformed(ActionEvent e) {
        try {
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            getShopService().exportPays(targetFile);
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void exportAllActionPerformed(ActionEvent e) {
        try {
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            getShopService().exportAll(targetFile);
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void genCardMenuActionPerformed(ActionEvent e) {
        try {
            getCardService().generateCards();
            new OperationHint(this, "生成成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void thisMouseClicked(MouseEvent e) {
    }

    private void importCardApplicationRecordActionPerformed(ActionEvent e) {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getCardService().importCardApplicationRecords(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void queryCardApplicationRecordActionPerformed(ActionEvent e) {
//        new QueryFrame<CardApplicationRecord>(getCardService().queryCardApplicationRecords(), new CardApplicationRecordQueryObject());
//        new QueryFrame<CardApplicationRecord>(new CardApplicationRecordQueryObject());
        new QueryFrameNew<CardApplicationRecord>(new CardApplicationRecordQueryObject());
    }

    private void exportCardApplicationRecordActionPerformed(ActionEvent e) {
        try {
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            getCardService().exportCardApplicationRecords(targetFile);
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void countShopEquipmentActionPerformed(ActionEvent e) {
        try {
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            getShopService().countShopEquipment(targetFile);
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void onlineSettingActionPerformed(ActionEvent e) {
        try {
            InputHint inputHint = new InputHint(this, new String[]{"本机ip", "主机ip"});
            if (!inputHint.isOk()) {
                return;
            }
            String localIp = inputHint.getInputResult()[0];
            if (StringUtils.isNotEmpty(localIp)) {
                getSystemSettingsService().updateLocalIp(localIp);
            }
            String hostIp = inputHint.getInputResult()[1];
            if (StringUtils.isNotEmpty(hostIp)) {
                getLocalRegistryService().updateRegistryIps(CollectionUtil.newList(hostIp));
            }
            new OperationHint(this, "设置成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void startOnlineActionPerformed(ActionEvent e) {
        if (isOnline) {
            new OperationHint(this, "已联机");
            return;
        }
        asynchronousOnline();
        setTitleByOnlineStatus(OnlineStatus.ONLINE_PREPARING_FLAG);
        new OperationHint(this, "正在联机中，请稍候......");
    }

    private void onlineConfigActionPerformed(ActionEvent e) {
        new OnlineConfig(this, isOnline, syncOk);
    }

    private void importWarrantyFeeActionPerformed(ActionEvent e) {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getWarrantyService().importWarrantyFee(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void exportWarrantyFeeActionPerformed(ActionEvent e) {
        try {
            InputHint inputHint = new InputHint(this, new String[]{"导出年份"});
            if (!inputHint.isOk()) {
                return;
            }
            String numberOfYear = inputHint.getInputResult()[0];
            File targetFile = chooseExportFile("excel文件", "xls");
            if (targetFile == null) return;
            if (StringUtils.isEmpty(numberOfYear)) {
                getWarrantyService().exportWarrantyFee(targetFile);
            } else {
                getWarrantyService().exportWarrantyFee(targetFile, numberOfYear);
            }
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void queryWarrantyFeeActionPerformed(ActionEvent e) {
        new QueryFrame<WarrantyFee>(getWarrantyService().queryWarrantyFee(), new WarrantyFeeQueryObject());
    }

    private void importWarrantyPartsActionPerformed(ActionEvent e) {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getWarrantyService().importWarrantyParts(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void queryWarrantyPartsActionPerformed(ActionEvent e) {
        new QueryFrame<WarrantyParts>(getWarrantyService().queryWarrantyParts(), new WarrantyPartsQueryObject());
    }

    private void exportWarrantyPartsActionPerformed(ActionEvent e) {
        try {
            InputHint inputHint = new InputHint(this, new String[]{"导出年份"});
            if (!inputHint.isOk()) {
                return;
            }
            String numberOfYear = inputHint.getInputResult()[0];
            File targetFile = chooseExportFile("excel文件", "xls");
            if (targetFile == null) return;
            if (StringUtils.isEmpty(numberOfYear)) {
                getWarrantyService().exportWarrantyParts(targetFile);
            } else {
                getWarrantyService().exportWarrantyParts(targetFile, numberOfYear);
            }
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void reportErrorActionPerformed(ActionEvent e) {
        new ReportErrorHint(this);
    }

    private void queryShopContractsActionPerformed(ActionEvent e) {
        new QueryFrame<ShopContract>(getShopService().queryShopContracts(), new ShopContractQueryObject());
    }

    private void importShopContractsActionPerformed(ActionEvent e) {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getShopService().importShopContracts(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void exportShopContractsActionPerformed(ActionEvent e) {
        try {
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            getShopService().exportShopContracts(targetFile);
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void queryPartsLibraryActionPerformed(ActionEvent e) {
        new QueryFrame<PartsLibrary>(getShopService().queryPartsLibrary(), new PartsLibraryQueryObject());
    }

    private void importPartsLibraryActionPerformed(ActionEvent e) {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getShopService().importPartsLibrary(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void exportPartsLibraryActionPerformed(ActionEvent e) {
        try {
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            getShopService().exportPartsLibrary(targetFile);
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void queryPrintHeadActionPerformed(ActionEvent e) {
        new QueryFrame<WarrantyPrint>(getWarrantyService().queryWarrantyPrint(), new WarrantyPrintQueryObject());
    }

    private void importPrintHeadActionPerformed(ActionEvent e) {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getWarrantyService().importWarrantyPrint(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void exportPrintHeadActionPerformed(ActionEvent e) {
        try {
            InputHint inputHint = new InputHint(this, new String[]{"导出年份"});
            if (!inputHint.isOk()) {
                return;
            }
            String numberOfYear = inputHint.getInputResult()[0];
            File targetFile = chooseExportFile("excel文件", "xls");
            if (targetFile == null) return;
            if (StringUtils.isEmpty(numberOfYear)) {
                getWarrantyService().exportWarrantyPrint(targetFile);
            } else {
                getWarrantyService().exportWarrantyPrint(targetFile, numberOfYear);
            }
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void queryConsumablesActionPerformed(ActionEvent e) {
        new QueryFrame<WarrantyConsumables>(getWarrantyService().queryWarrantyConsumables(), new WarrantyConsumablesQueryObject());
    }

    private void importConsumablesActionPerformed(ActionEvent e) {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getWarrantyService().importWarrantyConsumables(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void exportConsumablesActionPerformed(ActionEvent e) {
        try {
            InputHint inputHint = new InputHint(this, new String[]{"导出年份"});
            if (!inputHint.isOk()) {
                return;
            }
            String numberOfYear = inputHint.getInputResult()[0];
            File targetFile = chooseExportFile("excel文件", "xls");
            if (targetFile == null) return;
            if (StringUtils.isEmpty(numberOfYear)) {
                getWarrantyService().exportWarrantyConsumables(targetFile);
            } else {
                getWarrantyService().exportWarrantyConsumables(targetFile, numberOfYear);
            }
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void queryTechManualActionPerformed(ActionEvent e) {
        new QueryFrame<TechManualPermissions>(getTechService().queryManualPermissions(), new ManualPermissionsQueryObject());
    }

    private void importTechManualActionPerformed(ActionEvent e) {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getTechService().importManualPermissions(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void exportTechManualActionPerformed(ActionEvent e) {
        try {
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            getTechService().exportManualPermissions(targetFile);
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void querySOIDCodeActionPerformed(ActionEvent e) {
        new QueryFrame<SOIDCode>(getTechService().querySOIDCode(), new SOIDCodeQueryObject());
    }

    private void importSOIDCodeActionPerformed(ActionEvent e) {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getTechService().importSOIDCode(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void exportSOIDCodeActionPerformed(ActionEvent e) {
        try {
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            getTechService().exportSOIDCode(targetFile);
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void querySDSPermissionActionPerformed(ActionEvent e) {
        new QueryFrame<TechSDSPermission>(getTechService().querySDSPermissions(), new SDSPermissionQueryObject());
    }

    private void importSDSPermissionActionPerformed(ActionEvent e) {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getTechService().importSDSPermissions(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void exportSDSPermissionActionPerformed(ActionEvent e) {
        try {
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            getTechService().exportSDSPermissions(targetFile);
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void queryTechSupportActionPerformed(ActionEvent e) {
        new QueryFrame<TechSupport>(getTechService().queryTechSupport(), new TechSupportQueryObject());
    }

    private void importTechSupportActionPerformed(ActionEvent e) {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getTechService().importTechSupport(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void exportTechSupportActionPerformed(ActionEvent e) {
        try {
            InputHint inputHint = new InputHint(this, new String[]{"导出年份"});
            if (!inputHint.isOk()) {
                return;
            }
            String numberOfYear = inputHint.getInputResult()[0];
            File targetFile = chooseExportFile("excel文件", "xls");
            if (targetFile == null) return;
            if (StringUtils.isEmpty(numberOfYear)) {
                getTechService().exportTechSupport(targetFile);
            } else {
                getTechService().exportTechSupport(targetFile, numberOfYear);
            }
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void queryInstallPermissionActionPerformed(ActionEvent e) {
        new QueryFrame<TechInstallPermission>(getTechService().queryInstallPermission(), new InstallPermissionQueryObject());
    }

    private void importInstallPermissionActionPerformed(ActionEvent e) {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getTechService().importInstallPermission(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void exportInstallPermissionActionPerformed(ActionEvent e) {
        try {
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            getTechService().exportInstallPermission(targetFile);
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void queryTSTrainingActionPerformed(ActionEvent e) {
        new QueryFrame<TSTraining>(getTsService().queryTSTraining(), new TSTrainingQueryObject());
    }

    private void importTSTrainingActionPerformed(ActionEvent e) {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getTsService().importTSTraining(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void exportTSTrainingActionPerformed(ActionEvent e) {
        try {
            InputHint inputHint = new InputHint(this, new String[]{"导出年份"});
            if (!inputHint.isOk()) {
                return;
            }
            String numberOfYear = inputHint.getInputResult()[0];
            File targetFile = chooseExportFile("excel文件", "xls");
            if (targetFile == null) return;
            if (StringUtils.isEmpty(numberOfYear)) {
                getTsService().exportTSTraining(targetFile);
            } else {
                getTsService().exportTSTraining(targetFile, numberOfYear);
            }
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void queryTSManualPermissionsActionPerformed(ActionEvent e) {
        new QueryFrame<TSManualPermissions>(getTsService().queryTSManualPermission(), new TSManualPermissionsQueryObject());
    }

    private void importTSManualPermissionsActionPerformed(ActionEvent e) {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getTsService().importTSManualPermission(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void exportTSManualPermissionsActionPerformed(ActionEvent e) {
        try {
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            getTsService().exportTSManualPermission(targetFile);
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void queryTSSDSPermissionActionPerformed(ActionEvent e) {
        new QueryFrame<TSSDSPermission>(getTsService().queryTSSDSPermission(), new TSSDSPermissionQueryObject());
    }

    private void importTSSDSPermissionActionPerformed(ActionEvent e) {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getTsService().importTSSDSPermission(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void exportTSSDSPermissionActionPerformed(ActionEvent e) {
        try {
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            getTsService().exportTSSDSPermission(targetFile);
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void queryTSInstallPermissionActionPerformed(ActionEvent e) {
        new QueryFrame<TSInstallPermission>(getTsService().queryTSInstallPermission(), new TSInstallPermissionQueryObject());
    }

    private void importTSInstallPermissionActionPerformed(ActionEvent e) {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getTsService().importTSInstallPermission(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void exportTSInstallPermissionActionPerformed(ActionEvent e) {
        try {
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            getTsService().exportTSInstallPermission(targetFile);
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void queryOldMachineRenewActionPerformed(ActionEvent e) {
        new QueryFrame<OldMachineRenew>(getTsService().queryOldMachineRenew(), new OldMachineRenewQueryObject());
    }

    private void importOldMachineRenewActionPerformed(ActionEvent e) {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getTsService().importOldMachineRenew(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void exportOldMachineRenewActionPerformed(ActionEvent e) {
        try {
            InputHint inputHint = new InputHint(this, new String[]{"导出年份"});
            if (!inputHint.isOk()) {
                return;
            }
            String numberOfYear = inputHint.getInputResult()[0];
            File targetFile = chooseExportFile("excel文件", "xls");
            if (targetFile == null) return;
            if (StringUtils.isEmpty(numberOfYear)) {
                getTsService().exportOldMachineRenew(targetFile);
            } else {
                getTsService().exportOldMachineRenew(targetFile, numberOfYear);
            }
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void queryNewMachineClaimActionPerformed(ActionEvent e) {
        new QueryFrame<NewMachineClaim>(getTsService().queryNewMachineClaim(), new NewMachineClaimQueryObject());
    }

    private void importNewMachineClaimActionPerformed(ActionEvent e) {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getTsService().importNewMachineClaim(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void exportNewMachineClaimActionPerformed(ActionEvent e) {
        try {
            InputHint inputHint = new InputHint(this, new String[]{"导出年份"});
            if (!inputHint.isOk()) {
                return;
            }
            String numberOfYear = inputHint.getInputResult()[0];
            File targetFile = chooseExportFile("excel文件", "xls");
            if (targetFile == null) return;
            if (StringUtils.isEmpty(numberOfYear)) {
                getTsService().exportNewMachineClaim(targetFile);
            } else {
                getTsService().exportNewMachineClaim(targetFile, numberOfYear);
            }
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void queryToolRecipientsActionPerformed(ActionEvent e) {
        new QueryFrame<ToolRecipients>(getTsService().queryToolRecipients(), new ToolRecipientsQueryObject());
    }

    private void importToolRecipientsActionPerformed(ActionEvent e) {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getTsService().importToolRecipients(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void exportToolRecipientsActionPerformed(ActionEvent e) {
        try {
            InputHint inputHint = new InputHint(this, new String[]{"导出年份"});
            if (!inputHint.isOk()) {
                return;
            }
            String numberOfYear = inputHint.getInputResult()[0];
            File targetFile = chooseExportFile("excel文件", "xls");
            if (targetFile == null) return;
            if (StringUtils.isEmpty(numberOfYear)) {
                getTsService().exportToolRecipients(targetFile);
            } else {
                getTsService().exportToolRecipients(targetFile, numberOfYear);
            }
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void queryComponentBorrowingActionPerformed(ActionEvent e) {
        new QueryFrame<ComponentBorrowing>(getTsService().queryComponentBorrowing(), new ComponentBorrowingQueryObject());
    }

    private void importComponentBorrowingActionPerformed(ActionEvent e) {
        try {
            File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
            if (srcFile == null) return;
            getTsService().importComponentBorrowing(srcFile);
            new OperationHint(this, "导入成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void exportComponentBorrowingActionPerformed(ActionEvent e) {
        try {
            InputHint inputHint = new InputHint(this, new String[]{"导出年份"});
            if (!inputHint.isOk()) {
                return;
            }
            String numberOfYear = inputHint.getInputResult()[0];
            File targetFile = chooseExportFile("excel文件", "xls");
            if (targetFile == null) return;
            if (StringUtils.isEmpty(numberOfYear)) {
                getTsService().exportComponentBorrowing(targetFile);
            } else {
                getTsService().exportComponentBorrowing(targetFile, numberOfYear);
            }
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        managerMenuBar = new JMenuBar();
        basicManagement = new JMenu();
        shopManagement = new JMenu();
        queryShopBasic = new JMenuItem();
        importShopBasic = new JMenuItem();
        exportShopBasic = new JMenuItem();
        shopDetailMenu = new JMenu();
        queryShopDetail = new JMenuItem();
        importShopDetail = new JMenuItem();
        exportShopDetail = new JMenuItem();
        shopContractMenu = new JMenu();
        queryShopContracts = new JMenuItem();
        importShopContracts = new JMenuItem();
        exportShopContracts = new JMenuItem();
        exportAll = new JMenuItem();
        countEngineerByProductLine = new JMenuItem();
        countEngineerByShop = new JMenuItem();
        shopEngineerManagement = new JMenu();
        queryEngineerBasic = new JMenuItem();
        importEngineerBasic = new JMenuItem();
        exportEngineerBasic = new JMenuItem();
        shopEquipmentList = new JMenu();
        countShopEquipment = new JMenuItem();
        partsLibrary = new JMenu();
        queryPartsLibrary = new JMenuItem();
        importPartsLibrary = new JMenuItem();
        exportPartsLibrary = new JMenuItem();
        warrantyManagement = new JMenu();
        cardRecordManagement = new JMenu();
        queryCardApplicationRecord = new JMenuItem();
        genCardMenu = new JMenuItem();
        importCardApplicationRecord = new JMenuItem();
        exportCardApplicationRecord = new JMenuItem();
        parts4Warranty = new JMenu();
        queryWarrantyParts = new JMenuItem();
        importWarrantyParts = new JMenuItem();
        exportWarrantyParts = new JMenuItem();
        warrantyFee = new JMenu();
        queryWarrantyFee = new JMenuItem();
        importWarrantyFee = new JMenuItem();
        exportWarrantyFee = new JMenuItem();
        printHead = new JMenu();
        queryPrintHead = new JMenuItem();
        importPrintHead = new JMenuItem();
        exportPrintHead = new JMenuItem();
        consumables = new JMenu();
        queryConsumables = new JMenuItem();
        importConsumables = new JMenuItem();
        exportConsumables = new JMenuItem();
        technicalManagement = new JMenu();
        techTraining = new JMenu();
        queryEngineerTraining = new JMenuItem();
        importEngineerTraining = new JMenuItem();
        exportEngineerTraining = new JMenuItem();
        techManualPermission = new JMenu();
        queryTechManual = new JMenuItem();
        importTechManual = new JMenuItem();
        exportTechManual = new JMenuItem();
        techSDSPermission = new JMenu();
        querySDSPermission = new JMenuItem();
        importSDSPermission = new JMenuItem();
        exportSDSPermission = new JMenuItem();
        techSupporter = new JMenu();
        queryTechSupport = new JMenuItem();
        importTechSupport = new JMenuItem();
        exportTechSupport = new JMenuItem();
        techSOID = new JMenu();
        querySOIDCode = new JMenuItem();
        importSOIDCode = new JMenuItem();
        exportSOIDCode = new JMenuItem();
        techInstallPermission = new JMenu();
        queryInstallPermission = new JMenuItem();
        importInstallPermission = new JMenuItem();
        exportInstallPermission = new JMenuItem();
        tsManagement = new JMenu();
        tsTraining = new JMenu();
        queryTSTraining = new JMenuItem();
        importTSTraining = new JMenuItem();
        exportTSTraining = new JMenuItem();
        tsManualPermission = new JMenu();
        queryTSManualPermissions = new JMenuItem();
        importTSManualPermissions = new JMenuItem();
        exportTSManualPermissions = new JMenuItem();
        tsSDSPermission = new JMenu();
        queryTSSDSPermission = new JMenuItem();
        importTSSDSPermission = new JMenuItem();
        exportTSSDSPermission = new JMenuItem();
        tsInstallPermission = new JMenu();
        queryTSInstallPermission = new JMenuItem();
        importTSInstallPermission = new JMenuItem();
        exportTSInstallPermission = new JMenuItem();
        renewManagement = new JMenu();
        queryOldMachineRenew = new JMenuItem();
        importOldMachineRenew = new JMenuItem();
        exportOldMachineRenew = new JMenuItem();
        newClaim = new JMenu();
        queryNewMachineClaim = new JMenuItem();
        importNewMachineClaim = new JMenuItem();
        exportNewMachineClaim = new JMenuItem();
        toolRecipients = new JMenu();
        queryToolRecipients = new JMenuItem();
        importToolRecipients = new JMenuItem();
        exportToolRecipients = new JMenuItem();
        componentBorrowing = new JMenu();
        queryComponentBorrowing = new JMenuItem();
        importComponentBorrowing = new JMenuItem();
        exportComponentBorrowing = new JMenuItem();
        onlineMenu = new JMenu();
        onlineSetting = new JMenuItem();
        startOnline = new JMenuItem();
        onlineConfig = new JMenuItem();
        helpMenu = new JMenu();
        aboutItem = new JMenuItem();
        backupDB = new JMenuItem();
        recoveryDB = new JMenuItem();
        reportError = new JMenuItem();

        //======== this ========
        setTitle("PPP-ERP\u4e3b\u754c\u9762");
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                thisMouseClicked(e);
            }
        });
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== managerMenuBar ========
        {

            //======== basicManagement ========
            {
                basicManagement.setText("\u57fa\u672c\u4fe1\u606f");

                //======== shopManagement ========
                {
                    shopManagement.setText("\u670d\u52a1\u8ba4\u5b9a\u5e97\u57fa\u672c\u4fe1\u606f");

                    //---- queryShopBasic ----
                    queryShopBasic.setText("\u67e5\u8be2");
                    queryShopBasic.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queryShopBasicActionPerformed(e);
                        }
                    });
                    shopManagement.add(queryShopBasic);

                    //---- importShopBasic ----
                    importShopBasic.setText("\u5bfc\u5165");
                    importShopBasic.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            importShopBasicActionPerformed(e);
                        }
                    });
                    shopManagement.add(importShopBasic);

                    //---- exportShopBasic ----
                    exportShopBasic.setText("\u5bfc\u51fa");
                    exportShopBasic.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportShopBasicActionPerformed(e);
                        }
                    });
                    shopManagement.add(exportShopBasic);

                    //======== shopDetailMenu ========
                    {
                        shopDetailMenu.setText("\u8ba4\u5b9a\u5e97\u8ba4\u5b9a\u7ea7\u522b");

                        //---- queryShopDetail ----
                        queryShopDetail.setText("\u67e5\u8be2");
                        queryShopDetail.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                queryShopDetailActionPerformed(e);
                            }
                        });
                        shopDetailMenu.add(queryShopDetail);

                        //---- importShopDetail ----
                        importShopDetail.setText("\u5bfc\u5165");
                        importShopDetail.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                importShopDetailActionPerformed(e);
                            }
                        });
                        shopDetailMenu.add(importShopDetail);

                        //---- exportShopDetail ----
                        exportShopDetail.setText("\u5bfc\u51fa\u5386\u5e74\u8ba4\u5b9a\u5e97\u7ea7\u522b");
                        exportShopDetail.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                exportShopDetailActionPerformed(e);
                            }
                        });
                        shopDetailMenu.add(exportShopDetail);
                    }
                    shopManagement.add(shopDetailMenu);

                    //======== shopContractMenu ========
                    {
                        shopContractMenu.setText("\u8ba4\u5b9a\u5e97\u5408\u540c\u4fe1\u606f");

                        //---- queryShopContracts ----
                        queryShopContracts.setText("\u67e5\u8be2");
                        queryShopContracts.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                queryShopContractsActionPerformed(e);
                            }
                        });
                        shopContractMenu.add(queryShopContracts);

                        //---- importShopContracts ----
                        importShopContracts.setText("\u5bfc\u5165");
                        importShopContracts.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                importShopContractsActionPerformed(e);
                            }
                        });
                        shopContractMenu.add(importShopContracts);

                        //---- exportShopContracts ----
                        exportShopContracts.setText("\u5bfc\u51fa");
                        exportShopContracts.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                exportShopContractsActionPerformed(e);
                            }
                        });
                        shopContractMenu.add(exportShopContracts);
                    }
                    shopManagement.add(shopContractMenu);

                    //---- exportAll ----
                    exportAll.setText("\u57fa\u7840\u4fe1\u606f\u5168\u5bfc\u51fa");
                    exportAll.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportAllActionPerformed(e);
                        }
                    });
                    shopManagement.add(exportAll);

                    //---- countEngineerByProductLine ----
                    countEngineerByProductLine.setText("\u4ea7\u54c1\u7ebf\u5728\u804c\u4eba\u6570");
                    countEngineerByProductLine.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            countEngineerByProductLineActionPerformed(e);
                        }
                    });
                    shopManagement.add(countEngineerByProductLine);

                    //---- countEngineerByShop ----
                    countEngineerByShop.setText("\u8ba4\u5b9a\u5e97\u5728\u804c\u4eba\u6570");
                    countEngineerByShop.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            countEngineerByShopActionPerformed(e);
                        }
                    });
                    shopManagement.add(countEngineerByShop);
                }
                basicManagement.add(shopManagement);

                //======== shopEngineerManagement ========
                {
                    shopEngineerManagement.setText("\u670d\u52a1\u8ba4\u5b9a\u5e97\u5de5\u7a0b\u5e08\u7ba1\u7406");

                    //---- queryEngineerBasic ----
                    queryEngineerBasic.setText("\u67e5\u8be2");
                    queryEngineerBasic.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queryEngineerBasicActionPerformed(e);
                        }
                    });
                    shopEngineerManagement.add(queryEngineerBasic);

                    //---- importEngineerBasic ----
                    importEngineerBasic.setText("\u5bfc\u5165");
                    importEngineerBasic.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            importEngineerBasicActionPerformed(e);
                        }
                    });
                    shopEngineerManagement.add(importEngineerBasic);

                    //---- exportEngineerBasic ----
                    exportEngineerBasic.setText("\u5bfc\u51fa");
                    exportEngineerBasic.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportEngineerBasicActionPerformed(e);
                        }
                    });
                    shopEngineerManagement.add(exportEngineerBasic);
                }
                basicManagement.add(shopEngineerManagement);

                //======== shopEquipmentList ========
                {
                    shopEquipmentList.setText("\u670d\u52a1\u8ba4\u5b9a\u5e97\u8bbe\u5907\u6e05\u5355");

                    //---- countShopEquipment ----
                    countShopEquipment.setText("\u8ba4\u5b9a\u5e97\u8bbe\u5907\u6570\u5bfc\u51fa");
                    countShopEquipment.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            countShopEquipmentActionPerformed(e);
                        }
                    });
                    shopEquipmentList.add(countShopEquipment);
                }
                basicManagement.add(shopEquipmentList);

                //======== partsLibrary ========
                {
                    partsLibrary.setText("\u96f6\u4ef6\u5907\u5e93\u7ba1\u7406");

                    //---- queryPartsLibrary ----
                    queryPartsLibrary.setText("\u67e5\u8be2");
                    queryPartsLibrary.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queryPartsLibraryActionPerformed(e);
                        }
                    });
                    partsLibrary.add(queryPartsLibrary);

                    //---- importPartsLibrary ----
                    importPartsLibrary.setText("\u5bfc\u5165");
                    importPartsLibrary.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            importPartsLibraryActionPerformed(e);
                        }
                    });
                    partsLibrary.add(importPartsLibrary);

                    //---- exportPartsLibrary ----
                    exportPartsLibrary.setText("\u5bfc\u51fa");
                    exportPartsLibrary.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportPartsLibraryActionPerformed(e);
                        }
                    });
                    partsLibrary.add(exportPartsLibrary);
                }
                basicManagement.add(partsLibrary);
            }
            managerMenuBar.add(basicManagement);

            //======== warrantyManagement ========
            {
                warrantyManagement.setText("\u4fdd\u4fee\u7ba1\u7406");

                //======== cardRecordManagement ========
                {
                    cardRecordManagement.setText("\u4fdd\u4fee\u5361\u4fe1\u606f\u5f55\u5165");

                    //---- queryCardApplicationRecord ----
                    queryCardApplicationRecord.setText("\u67e5\u8be2");
                    queryCardApplicationRecord.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queryCardApplicationRecordActionPerformed(e);
                        }
                    });
                    cardRecordManagement.add(queryCardApplicationRecord);

                    //---- genCardMenu ----
                    genCardMenu.setText("\u751f\u6210\u4fdd\u4fee\u5361");
                    genCardMenu.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            genCardMenuActionPerformed(e);
                        }
                    });
                    cardRecordManagement.add(genCardMenu);

                    //---- importCardApplicationRecord ----
                    importCardApplicationRecord.setText("\u5bfc\u5165");
                    importCardApplicationRecord.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            importCardApplicationRecordActionPerformed(e);
                        }
                    });
                    cardRecordManagement.add(importCardApplicationRecord);

                    //---- exportCardApplicationRecord ----
                    exportCardApplicationRecord.setText("\u5bfc\u51fa");
                    exportCardApplicationRecord.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportCardApplicationRecordActionPerformed(e);
                        }
                    });
                    cardRecordManagement.add(exportCardApplicationRecord);
                }
                warrantyManagement.add(cardRecordManagement);

                //======== parts4Warranty ========
                {
                    parts4Warranty.setText("\u4fdd\u4fee\u96f6\u4ef6\u53ca\u7d22\u8d54\u96f6\u4ef6");

                    //---- queryWarrantyParts ----
                    queryWarrantyParts.setText("\u67e5\u8be2");
                    queryWarrantyParts.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queryWarrantyPartsActionPerformed(e);
                        }
                    });
                    parts4Warranty.add(queryWarrantyParts);

                    //---- importWarrantyParts ----
                    importWarrantyParts.setText("\u5bfc\u5165");
                    importWarrantyParts.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            importWarrantyPartsActionPerformed(e);
                        }
                    });
                    parts4Warranty.add(importWarrantyParts);

                    //---- exportWarrantyParts ----
                    exportWarrantyParts.setText("\u5bfc\u51fa");
                    exportWarrantyParts.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportWarrantyPartsActionPerformed(e);
                        }
                    });
                    parts4Warranty.add(exportWarrantyParts);
                }
                warrantyManagement.add(parts4Warranty);

                //======== warrantyFee ========
                {
                    warrantyFee.setText("\u4fdd\u4fee\u8d39");

                    //---- queryWarrantyFee ----
                    queryWarrantyFee.setText("\u67e5\u8be2");
                    queryWarrantyFee.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queryWarrantyFeeActionPerformed(e);
                        }
                    });
                    warrantyFee.add(queryWarrantyFee);

                    //---- importWarrantyFee ----
                    importWarrantyFee.setText("\u5bfc\u5165");
                    importWarrantyFee.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            importWarrantyFeeActionPerformed(e);
                        }
                    });
                    warrantyFee.add(importWarrantyFee);

                    //---- exportWarrantyFee ----
                    exportWarrantyFee.setText("\u5bfc\u51fa");
                    exportWarrantyFee.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportWarrantyFeeActionPerformed(e);
                        }
                    });
                    warrantyFee.add(exportWarrantyFee);
                }
                warrantyManagement.add(warrantyFee);

                //======== printHead ========
                {
                    printHead.setText("\u6253\u5370\u5934\u4fdd\u4fee");

                    //---- queryPrintHead ----
                    queryPrintHead.setText("\u67e5\u8be2");
                    queryPrintHead.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queryPrintHeadActionPerformed(e);
                        }
                    });
                    printHead.add(queryPrintHead);

                    //---- importPrintHead ----
                    importPrintHead.setText("\u5bfc\u5165");
                    importPrintHead.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            importPrintHeadActionPerformed(e);
                        }
                    });
                    printHead.add(importPrintHead);

                    //---- exportPrintHead ----
                    exportPrintHead.setText("\u5bfc\u51fa");
                    exportPrintHead.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportPrintHeadActionPerformed(e);
                        }
                    });
                    printHead.add(exportPrintHead);
                }
                warrantyManagement.add(printHead);

                //======== consumables ========
                {
                    consumables.setText("\u8017\u6750\u4fdd\u4fee");

                    //---- queryConsumables ----
                    queryConsumables.setText("\u67e5\u8be2");
                    queryConsumables.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queryConsumablesActionPerformed(e);
                        }
                    });
                    consumables.add(queryConsumables);

                    //---- importConsumables ----
                    importConsumables.setText("\u5bfc\u5165");
                    importConsumables.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            importConsumablesActionPerformed(e);
                        }
                    });
                    consumables.add(importConsumables);

                    //---- exportConsumables ----
                    exportConsumables.setText("\u5bfc\u51fa");
                    exportConsumables.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportConsumablesActionPerformed(e);
                        }
                    });
                    consumables.add(exportConsumables);
                }
                warrantyManagement.add(consumables);
            }
            managerMenuBar.add(warrantyManagement);

            //======== technicalManagement ========
            {
                technicalManagement.setText("\u6280\u672f\u7ba1\u7406");

                //======== techTraining ========
                {
                    techTraining.setText("\u57f9\u8bad\u8bb0\u5f55");

                    //---- queryEngineerTraining ----
                    queryEngineerTraining.setText("\u67e5\u8be2");
                    queryEngineerTraining.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queryEngineerTrainingActionPerformed(e);
                        }
                    });
                    techTraining.add(queryEngineerTraining);

                    //---- importEngineerTraining ----
                    importEngineerTraining.setText("\u5bfc\u5165");
                    importEngineerTraining.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            importEngineerTrainingActionPerformed(e);
                        }
                    });
                    techTraining.add(importEngineerTraining);

                    //---- exportEngineerTraining ----
                    exportEngineerTraining.setText("\u5bfc\u51fa");
                    exportEngineerTraining.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportEngineerTrainingActionPerformed(e);
                        }
                    });
                    techTraining.add(exportEngineerTraining);
                }
                technicalManagement.add(techTraining);

                //======== techManualPermission ========
                {
                    techManualPermission.setText("\u624b\u518c\u6743\u9650\u7ba1\u7406");

                    //---- queryTechManual ----
                    queryTechManual.setText("\u67e5\u8be2");
                    queryTechManual.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queryTechManualActionPerformed(e);
                        }
                    });
                    techManualPermission.add(queryTechManual);

                    //---- importTechManual ----
                    importTechManual.setText("\u5bfc\u5165");
                    importTechManual.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            importTechManualActionPerformed(e);
                        }
                    });
                    techManualPermission.add(importTechManual);

                    //---- exportTechManual ----
                    exportTechManual.setText("\u5bfc\u51fa");
                    exportTechManual.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportTechManualActionPerformed(e);
                        }
                    });
                    techManualPermission.add(exportTechManual);
                }
                technicalManagement.add(techManualPermission);

                //======== techSDSPermission ========
                {
                    techSDSPermission.setText("SDS\u6743\u9650\u7ba1\u7406");

                    //---- querySDSPermission ----
                    querySDSPermission.setText("\u67e5\u8be2");
                    querySDSPermission.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            querySDSPermissionActionPerformed(e);
                        }
                    });
                    techSDSPermission.add(querySDSPermission);

                    //---- importSDSPermission ----
                    importSDSPermission.setText("\u5bfc\u5165");
                    importSDSPermission.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            importSDSPermissionActionPerformed(e);
                        }
                    });
                    techSDSPermission.add(importSDSPermission);

                    //---- exportSDSPermission ----
                    exportSDSPermission.setText("\u5bfc\u51fa");
                    exportSDSPermission.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportSDSPermissionActionPerformed(e);
                        }
                    });
                    techSDSPermission.add(exportSDSPermission);
                }
                technicalManagement.add(techSDSPermission);

                //======== techSupporter ========
                {
                    techSupporter.setText("\u6280\u672f\u652f\u63f4\u7ba1\u7406");

                    //---- queryTechSupport ----
                    queryTechSupport.setText("\u67e5\u8be2");
                    queryTechSupport.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queryTechSupportActionPerformed(e);
                        }
                    });
                    techSupporter.add(queryTechSupport);

                    //---- importTechSupport ----
                    importTechSupport.setText("\u5bfc\u5165");
                    importTechSupport.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            importTechSupportActionPerformed(e);
                        }
                    });
                    techSupporter.add(importTechSupport);

                    //---- exportTechSupport ----
                    exportTechSupport.setText("\u5bfc\u51fa");
                    exportTechSupport.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportTechSupportActionPerformed(e);
                        }
                    });
                    techSupporter.add(exportTechSupport);
                }
                technicalManagement.add(techSupporter);

                //======== techSOID ========
                {
                    techSOID.setText("SOID\u8bc6\u522b\u7801");

                    //---- querySOIDCode ----
                    querySOIDCode.setText("\u67e5\u8be2");
                    querySOIDCode.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            querySOIDCodeActionPerformed(e);
                        }
                    });
                    techSOID.add(querySOIDCode);

                    //---- importSOIDCode ----
                    importSOIDCode.setText("\u5bfc\u5165");
                    importSOIDCode.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            importSOIDCodeActionPerformed(e);
                        }
                    });
                    techSOID.add(importSOIDCode);

                    //---- exportSOIDCode ----
                    exportSOIDCode.setText("\u5bfc\u51fa");
                    exportSOIDCode.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportSOIDCodeActionPerformed(e);
                        }
                    });
                    techSOID.add(exportSOIDCode);
                }
                technicalManagement.add(techSOID);

                //======== techInstallPermission ========
                {
                    techInstallPermission.setText("\u88c5\u673a\u6743\u9650\u7ba1\u7406");

                    //---- queryInstallPermission ----
                    queryInstallPermission.setText("\u67e5\u8be2");
                    queryInstallPermission.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queryInstallPermissionActionPerformed(e);
                        }
                    });
                    techInstallPermission.add(queryInstallPermission);

                    //---- importInstallPermission ----
                    importInstallPermission.setText("\u5bfc\u5165");
                    importInstallPermission.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            importInstallPermissionActionPerformed(e);
                        }
                    });
                    techInstallPermission.add(importInstallPermission);

                    //---- exportInstallPermission ----
                    exportInstallPermission.setText("\u5bfc\u51fa");
                    exportInstallPermission.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportInstallPermissionActionPerformed(e);
                        }
                    });
                    techInstallPermission.add(exportInstallPermission);
                }
                technicalManagement.add(techInstallPermission);
            }
            managerMenuBar.add(technicalManagement);

            //======== tsManagement ========
            {
                tsManagement.setText("TS\u7ba1\u7406");

                //======== tsTraining ========
                {
                    tsTraining.setText("\u57f9\u8bad\u8bb0\u5f55");

                    //---- queryTSTraining ----
                    queryTSTraining.setText("\u67e5\u8be2");
                    queryTSTraining.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queryTSTrainingActionPerformed(e);
                        }
                    });
                    tsTraining.add(queryTSTraining);

                    //---- importTSTraining ----
                    importTSTraining.setText("\u5bfc\u5165");
                    importTSTraining.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            importTSTrainingActionPerformed(e);
                        }
                    });
                    tsTraining.add(importTSTraining);

                    //---- exportTSTraining ----
                    exportTSTraining.setText("\u5bfc\u51fa");
                    exportTSTraining.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportTSTrainingActionPerformed(e);
                        }
                    });
                    tsTraining.add(exportTSTraining);
                }
                tsManagement.add(tsTraining);

                //======== tsManualPermission ========
                {
                    tsManualPermission.setText("\u624b\u518c\u6743\u9650\u7ba1\u7406");

                    //---- queryTSManualPermissions ----
                    queryTSManualPermissions.setText("\u67e5\u8be2");
                    queryTSManualPermissions.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queryTSManualPermissionsActionPerformed(e);
                        }
                    });
                    tsManualPermission.add(queryTSManualPermissions);

                    //---- importTSManualPermissions ----
                    importTSManualPermissions.setText("\u5bfc\u5165");
                    importTSManualPermissions.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            importTSManualPermissionsActionPerformed(e);
                        }
                    });
                    tsManualPermission.add(importTSManualPermissions);

                    //---- exportTSManualPermissions ----
                    exportTSManualPermissions.setText("\u5bfc\u51fa");
                    exportTSManualPermissions.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportTSManualPermissionsActionPerformed(e);
                        }
                    });
                    tsManualPermission.add(exportTSManualPermissions);
                }
                tsManagement.add(tsManualPermission);

                //======== tsSDSPermission ========
                {
                    tsSDSPermission.setText("SDS\u6743\u9650\u7ba1\u7406");

                    //---- queryTSSDSPermission ----
                    queryTSSDSPermission.setText("\u67e5\u8be2");
                    queryTSSDSPermission.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queryTSSDSPermissionActionPerformed(e);
                        }
                    });
                    tsSDSPermission.add(queryTSSDSPermission);

                    //---- importTSSDSPermission ----
                    importTSSDSPermission.setText("\u5bfc\u5165");
                    importTSSDSPermission.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            importTSSDSPermissionActionPerformed(e);
                        }
                    });
                    tsSDSPermission.add(importTSSDSPermission);

                    //---- exportTSSDSPermission ----
                    exportTSSDSPermission.setText("\u5bfc\u51fa");
                    exportTSSDSPermission.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportTSSDSPermissionActionPerformed(e);
                        }
                    });
                    tsSDSPermission.add(exportTSSDSPermission);
                }
                tsManagement.add(tsSDSPermission);

                //======== tsInstallPermission ========
                {
                    tsInstallPermission.setText("\u88c5\u673a\u6743\u9650\u7ba1\u7406");

                    //---- queryTSInstallPermission ----
                    queryTSInstallPermission.setText("\u67e5\u8be2");
                    queryTSInstallPermission.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queryTSInstallPermissionActionPerformed(e);
                        }
                    });
                    tsInstallPermission.add(queryTSInstallPermission);

                    //---- importTSInstallPermission ----
                    importTSInstallPermission.setText("\u5bfc\u5165");
                    importTSInstallPermission.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            importTSInstallPermissionActionPerformed(e);
                        }
                    });
                    tsInstallPermission.add(importTSInstallPermission);

                    //---- exportTSInstallPermission ----
                    exportTSInstallPermission.setText("\u5bfc\u51fa");
                    exportTSInstallPermission.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportTSInstallPermissionActionPerformed(e);
                        }
                    });
                    tsInstallPermission.add(exportTSInstallPermission);
                }
                tsManagement.add(tsInstallPermission);

                //======== renewManagement ========
                {
                    renewManagement.setText("\u65e7\u673a\u7ffb\u65b0\u7ba1\u7406");

                    //---- queryOldMachineRenew ----
                    queryOldMachineRenew.setText("\u67e5\u8be2");
                    queryOldMachineRenew.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queryOldMachineRenewActionPerformed(e);
                        }
                    });
                    renewManagement.add(queryOldMachineRenew);

                    //---- importOldMachineRenew ----
                    importOldMachineRenew.setText("\u5bfc\u5165");
                    importOldMachineRenew.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            importOldMachineRenewActionPerformed(e);
                        }
                    });
                    renewManagement.add(importOldMachineRenew);

                    //---- exportOldMachineRenew ----
                    exportOldMachineRenew.setText("\u5bfc\u51fa");
                    exportOldMachineRenew.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportOldMachineRenewActionPerformed(e);
                        }
                    });
                    renewManagement.add(exportOldMachineRenew);
                }
                tsManagement.add(renewManagement);

                //======== newClaim ========
                {
                    newClaim.setText("\u65b0\u88c5\u673a\u7d22\u8d54\u7ba1\u7406");

                    //---- queryNewMachineClaim ----
                    queryNewMachineClaim.setText("\u67e5\u8be2");
                    queryNewMachineClaim.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queryNewMachineClaimActionPerformed(e);
                        }
                    });
                    newClaim.add(queryNewMachineClaim);

                    //---- importNewMachineClaim ----
                    importNewMachineClaim.setText("\u5bfc\u5165");
                    importNewMachineClaim.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            importNewMachineClaimActionPerformed(e);
                        }
                    });
                    newClaim.add(importNewMachineClaim);

                    //---- exportNewMachineClaim ----
                    exportNewMachineClaim.setText("\u5bfc\u51fa");
                    exportNewMachineClaim.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportNewMachineClaimActionPerformed(e);
                        }
                    });
                    newClaim.add(exportNewMachineClaim);
                }
                tsManagement.add(newClaim);

                //======== toolRecipients ========
                {
                    toolRecipients.setText("\u5de5\u5177\u9886\u7528\u8bb0\u5f55");

                    //---- queryToolRecipients ----
                    queryToolRecipients.setText("\u67e5\u8be2");
                    queryToolRecipients.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queryToolRecipientsActionPerformed(e);
                        }
                    });
                    toolRecipients.add(queryToolRecipients);

                    //---- importToolRecipients ----
                    importToolRecipients.setText("\u5bfc\u5165");
                    importToolRecipients.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            importToolRecipientsActionPerformed(e);
                        }
                    });
                    toolRecipients.add(importToolRecipients);

                    //---- exportToolRecipients ----
                    exportToolRecipients.setText("\u5bfc\u51fa");
                    exportToolRecipients.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportToolRecipientsActionPerformed(e);
                        }
                    });
                    toolRecipients.add(exportToolRecipients);
                }
                tsManagement.add(toolRecipients);

                //======== componentBorrowing ========
                {
                    componentBorrowing.setText("\u96f6\u4ef6\u501f\u7528\u7ba1\u7406");

                    //---- queryComponentBorrowing ----
                    queryComponentBorrowing.setText("\u67e5\u8be2");
                    queryComponentBorrowing.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queryComponentBorrowingActionPerformed(e);
                        }
                    });
                    componentBorrowing.add(queryComponentBorrowing);

                    //---- importComponentBorrowing ----
                    importComponentBorrowing.setText("\u5bfc\u5165");
                    importComponentBorrowing.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            importComponentBorrowingActionPerformed(e);
                        }
                    });
                    componentBorrowing.add(importComponentBorrowing);

                    //---- exportComponentBorrowing ----
                    exportComponentBorrowing.setText("\u5bfc\u51fa");
                    exportComponentBorrowing.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportComponentBorrowingActionPerformed(e);
                        }
                    });
                    componentBorrowing.add(exportComponentBorrowing);
                }
                tsManagement.add(componentBorrowing);
            }
            managerMenuBar.add(tsManagement);

            //======== onlineMenu ========
            {
                onlineMenu.setText("\u8054\u673a");

                //---- onlineSetting ----
                onlineSetting.setText("\u8bbe\u7f6e");
                onlineSetting.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        onlineSettingActionPerformed(e);
                    }
                });
                onlineMenu.add(onlineSetting);

                //---- startOnline ----
                startOnline.setText("\u542f\u52a8");
                startOnline.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        startOnlineActionPerformed(e);
                    }
                });
                onlineMenu.add(startOnline);

                //---- onlineConfig ----
                onlineConfig.setText("\u914d\u7f6e");
                onlineConfig.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        onlineConfigActionPerformed(e);
                    }
                });
                onlineMenu.add(onlineConfig);
            }
            managerMenuBar.add(onlineMenu);

            //======== helpMenu ========
            {
                helpMenu.setText("\u5e2e\u52a9");

                //---- aboutItem ----
                aboutItem.setText("\u5173\u4e8e");
                aboutItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        aboutItemActionPerformed(e);
                    }
                });
                helpMenu.add(aboutItem);

                //---- backupDB ----
                backupDB.setText("\u5907\u4efd\u6570\u636e");
                backupDB.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        backupDBActionPerformed(e);
                    }
                });
                helpMenu.add(backupDB);

                //---- recoveryDB ----
                recoveryDB.setText("\u6062\u590d\u6570\u636e");
                recoveryDB.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        recoveryDBActionPerformed(e);
                    }
                });
                helpMenu.add(recoveryDB);

                //---- reportError ----
                reportError.setText("\u62a5\u544a\u9519\u8bef");
                reportError.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        reportErrorActionPerformed(e);
                    }
                });
                helpMenu.add(reportError);
            }
            managerMenuBar.add(helpMenu);
        }
        setJMenuBar(managerMenuBar);

        contentPane.setPreferredSize(new Dimension(400, 300));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JMenuBar managerMenuBar;
    private JMenu basicManagement;
    private JMenu shopManagement;
    private JMenuItem queryShopBasic;
    private JMenuItem importShopBasic;
    private JMenuItem exportShopBasic;
    private JMenu shopDetailMenu;
    private JMenuItem queryShopDetail;
    private JMenuItem importShopDetail;
    private JMenuItem exportShopDetail;
    private JMenu shopContractMenu;
    private JMenuItem queryShopContracts;
    private JMenuItem importShopContracts;
    private JMenuItem exportShopContracts;
    private JMenuItem exportAll;
    private JMenuItem countEngineerByProductLine;
    private JMenuItem countEngineerByShop;
    private JMenu shopEngineerManagement;
    private JMenuItem queryEngineerBasic;
    private JMenuItem importEngineerBasic;
    private JMenuItem exportEngineerBasic;
    private JMenu shopEquipmentList;
    private JMenuItem countShopEquipment;
    private JMenu partsLibrary;
    private JMenuItem queryPartsLibrary;
    private JMenuItem importPartsLibrary;
    private JMenuItem exportPartsLibrary;
    private JMenu warrantyManagement;
    private JMenu cardRecordManagement;
    private JMenuItem queryCardApplicationRecord;
    private JMenuItem genCardMenu;
    private JMenuItem importCardApplicationRecord;
    private JMenuItem exportCardApplicationRecord;
    private JMenu parts4Warranty;
    private JMenuItem queryWarrantyParts;
    private JMenuItem importWarrantyParts;
    private JMenuItem exportWarrantyParts;
    private JMenu warrantyFee;
    private JMenuItem queryWarrantyFee;
    private JMenuItem importWarrantyFee;
    private JMenuItem exportWarrantyFee;
    private JMenu printHead;
    private JMenuItem queryPrintHead;
    private JMenuItem importPrintHead;
    private JMenuItem exportPrintHead;
    private JMenu consumables;
    private JMenuItem queryConsumables;
    private JMenuItem importConsumables;
    private JMenuItem exportConsumables;
    private JMenu technicalManagement;
    private JMenu techTraining;
    private JMenuItem queryEngineerTraining;
    private JMenuItem importEngineerTraining;
    private JMenuItem exportEngineerTraining;
    private JMenu techManualPermission;
    private JMenuItem queryTechManual;
    private JMenuItem importTechManual;
    private JMenuItem exportTechManual;
    private JMenu techSDSPermission;
    private JMenuItem querySDSPermission;
    private JMenuItem importSDSPermission;
    private JMenuItem exportSDSPermission;
    private JMenu techSupporter;
    private JMenuItem queryTechSupport;
    private JMenuItem importTechSupport;
    private JMenuItem exportTechSupport;
    private JMenu techSOID;
    private JMenuItem querySOIDCode;
    private JMenuItem importSOIDCode;
    private JMenuItem exportSOIDCode;
    private JMenu techInstallPermission;
    private JMenuItem queryInstallPermission;
    private JMenuItem importInstallPermission;
    private JMenuItem exportInstallPermission;
    private JMenu tsManagement;
    private JMenu tsTraining;
    private JMenuItem queryTSTraining;
    private JMenuItem importTSTraining;
    private JMenuItem exportTSTraining;
    private JMenu tsManualPermission;
    private JMenuItem queryTSManualPermissions;
    private JMenuItem importTSManualPermissions;
    private JMenuItem exportTSManualPermissions;
    private JMenu tsSDSPermission;
    private JMenuItem queryTSSDSPermission;
    private JMenuItem importTSSDSPermission;
    private JMenuItem exportTSSDSPermission;
    private JMenu tsInstallPermission;
    private JMenuItem queryTSInstallPermission;
    private JMenuItem importTSInstallPermission;
    private JMenuItem exportTSInstallPermission;
    private JMenu renewManagement;
    private JMenuItem queryOldMachineRenew;
    private JMenuItem importOldMachineRenew;
    private JMenuItem exportOldMachineRenew;
    private JMenu newClaim;
    private JMenuItem queryNewMachineClaim;
    private JMenuItem importNewMachineClaim;
    private JMenuItem exportNewMachineClaim;
    private JMenu toolRecipients;
    private JMenuItem queryToolRecipients;
    private JMenuItem importToolRecipients;
    private JMenuItem exportToolRecipients;
    private JMenu componentBorrowing;
    private JMenuItem queryComponentBorrowing;
    private JMenuItem importComponentBorrowing;
    private JMenuItem exportComponentBorrowing;
    private JMenu onlineMenu;
    private JMenuItem onlineSetting;
    private JMenuItem startOnline;
    private JMenuItem onlineConfig;
    private JMenu helpMenu;
    private JMenuItem aboutItem;
    private JMenuItem backupDB;
    private JMenuItem recoveryDB;
    private JMenuItem reportError;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
