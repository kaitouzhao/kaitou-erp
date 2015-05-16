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
import kaitou.ppp.app.ui.table.queryobject.*;
import kaitou.ppp.domain.card.CardApplicationRecord;
import kaitou.ppp.domain.engineer.Engineer;
import kaitou.ppp.domain.engineer.EngineerTraining;
import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.domain.shop.ShopDetail;
import kaitou.ppp.domain.shop.ShopPay;
import kaitou.ppp.domain.shop.ShopRTS;
import kaitou.ppp.domain.system.DBVersion;
import kaitou.ppp.domain.warranty.WarrantyFee;
import kaitou.ppp.domain.warranty.WarrantyParts;
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
                    String localIp = getSystemSettingsService().getLocalIp();
                    List<String> remoteRegistryIps = getLocalRegistryService().queryRegistryIps();
                    if (StringUtils.isEmpty(localIp) || CollectionUtil.isEmpty(remoteRegistryIps)) {
                        setTitleByOnlineStatus(OnlineStatus.OFFLINE_FLAG);
                        return;
                    }
                    if (!getServiceProvider().start(localIp)) {
                        setTitleByOnlineStatus(OnlineStatus.OFFLINE_FLAG);
                        return;
                    }
                    logOp("已启动服务监听");
                    setTitleByOnlineStatus(OnlineStatus.ONLINE_FLAG);
                    for (String hostIp : remoteRegistryIps) {
                        if (localIp.equals(hostIp)) {
                            continue;
                        }
                        RemoteRegistryService remoteRegistryService = ServiceClient.getRemoteService(RemoteRegistryService.class, hostIp);
                        if (remoteRegistryService == null) {
                            return;
                        }
                        List<String> registryIpsFromHost = remoteRegistryService.register(localIp);
                        logOp("远程注册成功，待更新IP列表：" + CollectionUtil.list2Str(registryIpsFromHost, ","));
                        getLocalRegistryService().updateRegistryIps(registryIpsFromHost);
                    }
                } catch (RemoteException e) {
                    handleEx(e);
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
        new QueryFrame<CardApplicationRecord>(getCardService().queryCardApplicationRecords(), new CardApplicationRecordQueryObject());
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
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            getWarrantyService().exportWarrantyFee(targetFile);
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
        rtsMenu = new JMenu();
        queryShopRTS = new JMenuItem();
        importShopRTS = new JMenuItem();
        exportShopRTS = new JMenuItem();
        shopPayMenu = new JMenu();
        queryShopPay = new JMenuItem();
        importShopPay = new JMenuItem();
        exportShopPay = new JMenuItem();
        exportAll = new JMenuItem();
        countEngineerByProductLine = new JMenuItem();
        countEngineerByShop = new JMenuItem();
        shopEngineerManagement = new JMenu();
        queryEngineerBasic = new JMenuItem();
        importEngineerBasic = new JMenuItem();
        exportEngineerBasic = new JMenuItem();
        shopEquipmentList = new JMenu();
        countShopEquipment = new JMenuItem();
        partsManagement = new JMenu();
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
        consumables = new JMenu();
        technicalManagement = new JMenu();
        techTraining = new JMenu();
        queryEngineerTraining = new JMenuItem();
        importEngineerTraining = new JMenuItem();
        exportEngineerTraining = new JMenuItem();
        techManualPermission = new JMenu();
        techSDSPermission = new JMenu();
        techSupporter = new JMenu();
        techSOID = new JMenu();
        techInstallPermission = new JMenu();
        tsManagement = new JMenu();
        tsTraining = new JMenu();
        tsManualPermission = new JMenu();
        tsSDSPermission = new JMenu();
        tsInstallPermission = new JMenu();
        renewManagement = new JMenu();
        newClaim = new JMenu();
        toolRecipients = new JMenu();
        componentBorrowing = new JMenu();
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
                        shopDetailMenu.setText("\u8ba4\u5b9a\u7ea7\u522b");

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

                    //======== rtsMenu ========
                    {
                        rtsMenu.setText("RTS");

                        //---- queryShopRTS ----
                        queryShopRTS.setText("\u67e5\u8be2");
                        queryShopRTS.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                queryShopRTSActionPerformed(e);
                            }
                        });
                        rtsMenu.add(queryShopRTS);

                        //---- importShopRTS ----
                        importShopRTS.setText("\u5bfc\u5165");
                        importShopRTS.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                importShopRTSActionPerformed(e);
                            }
                        });
                        rtsMenu.add(importShopRTS);

                        //---- exportShopRTS ----
                        exportShopRTS.setText("\u5bfc\u51fa");
                        exportShopRTS.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                exportShopRTSActionPerformed(e);
                            }
                        });
                        rtsMenu.add(exportShopRTS);
                    }
                    shopManagement.add(rtsMenu);

                    //======== shopPayMenu ========
                    {
                        shopPayMenu.setText("\u5e10\u53f7\u4fe1\u606f");

                        //---- queryShopPay ----
                        queryShopPay.setText("\u67e5\u8be2");
                        queryShopPay.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                queryShopPayActionPerformed(e);
                            }
                        });
                        shopPayMenu.add(queryShopPay);

                        //---- importShopPay ----
                        importShopPay.setText("\u5bfc\u5165");
                        importShopPay.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                importShopPayActionPerformed(e);
                            }
                        });
                        shopPayMenu.add(importShopPay);

                        //---- exportShopPay ----
                        exportShopPay.setText("\u5bfc\u51fa");
                        exportShopPay.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                exportShopPayActionPerformed(e);
                            }
                        });
                        shopPayMenu.add(exportShopPay);
                    }
                    shopManagement.add(shopPayMenu);

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

                //======== partsManagement ========
                {
                    partsManagement.setText("\u96f6\u4ef6\u5907\u5e93\u7ba1\u7406");
                }
                basicManagement.add(partsManagement);
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
                            importWarrantyFeeActionPerformed(e);
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
                            importWarrantyFeeActionPerformed(e);
                            exportWarrantyFeeActionPerformed(e);
                        }
                    });
                    warrantyFee.add(exportWarrantyFee);
                }
                warrantyManagement.add(warrantyFee);

                //======== printHead ========
                {
                    printHead.setText("\u6253\u5370\u5934\u4fdd\u4fee");
                }
                warrantyManagement.add(printHead);

                //======== consumables ========
                {
                    consumables.setText("\u8017\u6750\u4fdd\u4fee");
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
                }
                technicalManagement.add(techManualPermission);

                //======== techSDSPermission ========
                {
                    techSDSPermission.setText("SDS\u6743\u9650\u7ba1\u7406");
                }
                technicalManagement.add(techSDSPermission);

                //======== techSupporter ========
                {
                    techSupporter.setText("\u6280\u672f\u652f\u63f4\u7ba1\u7406");
                }
                technicalManagement.add(techSupporter);

                //======== techSOID ========
                {
                    techSOID.setText("SOID\u8bc6\u522b\u7801");
                }
                technicalManagement.add(techSOID);

                //======== techInstallPermission ========
                {
                    techInstallPermission.setText("\u88c5\u673a\u6743\u9650\u7ba1\u7406");
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
                }
                tsManagement.add(tsTraining);

                //======== tsManualPermission ========
                {
                    tsManualPermission.setText("\u624b\u518c\u6743\u9650\u7ba1\u7406");
                }
                tsManagement.add(tsManualPermission);

                //======== tsSDSPermission ========
                {
                    tsSDSPermission.setText("SDS\u6743\u9650\u7ba1\u7406");
                }
                tsManagement.add(tsSDSPermission);

                //======== tsInstallPermission ========
                {
                    tsInstallPermission.setText("\u88c5\u673a\u6743\u9650\u7ba1\u7406");
                }
                tsManagement.add(tsInstallPermission);

                //======== renewManagement ========
                {
                    renewManagement.setText("\u65e7\u673a\u7ffb\u65b0\u7ba1\u7406");
                }
                tsManagement.add(renewManagement);

                //======== newClaim ========
                {
                    newClaim.setText("\u65b0\u88c5\u673a\u7d22\u8d54\u7ba1\u7406");
                }
                tsManagement.add(newClaim);

                //======== toolRecipients ========
                {
                    toolRecipients.setText("\u5de5\u5177\u9886\u7528\u8bb0\u5f55");
                }
                tsManagement.add(toolRecipients);

                //======== componentBorrowing ========
                {
                    componentBorrowing.setText("\u96f6\u4ef6\u501f\u7528\u7ba1\u7406");
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
    private JMenu rtsMenu;
    private JMenuItem queryShopRTS;
    private JMenuItem importShopRTS;
    private JMenuItem exportShopRTS;
    private JMenu shopPayMenu;
    private JMenuItem queryShopPay;
    private JMenuItem importShopPay;
    private JMenuItem exportShopPay;
    private JMenuItem exportAll;
    private JMenuItem countEngineerByProductLine;
    private JMenuItem countEngineerByShop;
    private JMenu shopEngineerManagement;
    private JMenuItem queryEngineerBasic;
    private JMenuItem importEngineerBasic;
    private JMenuItem exportEngineerBasic;
    private JMenu shopEquipmentList;
    private JMenuItem countShopEquipment;
    private JMenu partsManagement;
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
    private JMenu consumables;
    private JMenu technicalManagement;
    private JMenu techTraining;
    private JMenuItem queryEngineerTraining;
    private JMenuItem importEngineerTraining;
    private JMenuItem exportEngineerTraining;
    private JMenu techManualPermission;
    private JMenu techSDSPermission;
    private JMenu techSupporter;
    private JMenu techSOID;
    private JMenu techInstallPermission;
    private JMenu tsManagement;
    private JMenu tsTraining;
    private JMenu tsManualPermission;
    private JMenu tsSDSPermission;
    private JMenu tsInstallPermission;
    private JMenu renewManagement;
    private JMenu newClaim;
    private JMenu toolRecipients;
    private JMenu componentBorrowing;
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
