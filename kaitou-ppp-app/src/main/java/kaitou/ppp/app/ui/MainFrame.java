/*
 * Created by JFormDesigner on Thu Feb 05 10:56:52 CST 2015
 */

package kaitou.ppp.app.ui;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.app.ui.dialog.InputHint;
import kaitou.ppp.app.ui.dialog.OnlineConfig;
import kaitou.ppp.app.ui.dialog.OperationHint;
import kaitou.ppp.app.ui.dialog.ReportErrorHint;
import kaitou.ppp.app.ui.table.OnlyQueryFrame;
import kaitou.ppp.app.ui.table.PageQueryFrame;
import kaitou.ppp.app.ui.table.QueryFrame;
import kaitou.ppp.app.ui.table.WarrantyPartsQueryFrame;
import kaitou.ppp.app.ui.table.queryobject.basic.*;
import kaitou.ppp.app.ui.table.queryobject.system.IPRegistryQueryObject;
import kaitou.ppp.app.ui.table.queryobject.system.OperationLogQueryObject;
import kaitou.ppp.app.ui.table.queryobject.tech.*;
import kaitou.ppp.app.ui.table.queryobject.ts.*;
import kaitou.ppp.app.ui.table.queryobject.warranty.*;
import kaitou.ppp.common.utils.NetworkUtil;
import kaitou.ppp.domain.basic.Models;
import kaitou.ppp.domain.card.CardApplicationRecord;
import kaitou.ppp.domain.engineer.Engineer;
import kaitou.ppp.domain.engineer.EngineerTraining;
import kaitou.ppp.domain.shop.PartsLibrary;
import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.domain.shop.ShopContract;
import kaitou.ppp.domain.shop.ShopDetail;
import kaitou.ppp.domain.system.DBVersion;
import kaitou.ppp.domain.system.IPRegistry;
import kaitou.ppp.domain.system.OperationLog;
import kaitou.ppp.domain.tech.*;
import kaitou.ppp.domain.ts.*;
import kaitou.ppp.domain.warranty.IpfEquipment;
import kaitou.ppp.domain.warranty.WarrantyConsumables;
import kaitou.ppp.domain.warranty.WarrantyFee;
import kaitou.ppp.domain.warranty.WarrantyPrint;
import kaitou.ppp.rmi.ServiceClient;
import kaitou.ppp.rmi.service.RemoteDBVersionService;
import kaitou.ppp.rmi.service.RemoteRegistryService;
import kaitou.ppp.service.LocalDBVersionService;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import static com.womai.bsp.tool.utils.PropertyUtil.getValue;
import static kaitou.ppp.app.SpringContextManager.getCardService;
import static kaitou.ppp.app.SpringContextManager.getDbService;
import static kaitou.ppp.app.SpringContextManager.getEngineerService;
import static kaitou.ppp.app.SpringContextManager.getExportService;
import static kaitou.ppp.app.SpringContextManager.getLocalDBVersionService;
import static kaitou.ppp.app.SpringContextManager.getLocalRegistryService;
import static kaitou.ppp.app.SpringContextManager.getServiceProvider;
import static kaitou.ppp.app.SpringContextManager.getShopService;
import static kaitou.ppp.app.SpringContextManager.getSystemSettingsService;
import static kaitou.ppp.app.SpringContextManager.getTechService;
import static kaitou.ppp.app.SpringContextManager.getTsService;
import static kaitou.ppp.app.SpringContextManager.getWarrantyService;
import static kaitou.ppp.app.ui.UIUtil.*;
import static kaitou.ppp.app.ui.table.OPManager.*;
import static kaitou.ppp.app.ui.table.OPManager.getBasicService;
import static kaitou.ppp.service.ServiceInvokeManager.InvokeRunnable;
import static kaitou.ppp.service.ServiceInvokeManager.asynchronousRun;

/**
 * 主界面
 *
 * @author 赵立伟
 */
public class MainFrame extends JFrame {
    private JFrame self = this;
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
        getUpgradeService().upgradeTo3Dot7();

        asynchronousInit();

        new MainFrame();
    }

    /**
     * 异步初始化
     * <ul>
     * <li>更新系统设置</li>
     * <li>备份DB</li>
     * <li>缓存认定店</li>
     * <li>缓存机型分类基础数据</li>
     * </ul>
     */
    private static void asynchronousInit() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getSystemSettingsService().updateSystemSettings();
                getDbService().backupDB();
                getShopService().cacheAllShops();
                getBasicService().cacheModels();
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
     * 异步获取技术管理SDS权限到期提醒
     */
    private void asynchronousGetTechSDSReminder() {
        asynchronousRun(new InvokeRunnable() {
            @Override
            public void run() throws RemoteException {
                List<TechSDSPermission> reminderList = getTechService().getSDSEndDateReminder();
                Object[] column = {"技术管理工程师姓名", "到期时间"};
                Object[][] data = new Object[(reminderList.size())][2];
                if (CollectionUtil.isNotEmpty(reminderList)) {
                    for (int i = 0; i < reminderList.size(); i++) {
                        TechSDSPermission permission = reminderList.get(i);
                        data[i][0] = permission.getApplicant();
                        data[i][1] = permission.getEndDate();
                    }
                }
                techSDSReminderTable.setModel(new DefaultTableModel(data, column));
                ((TitledBorder) techSDSReminderPanel.getBorder()).setTitle("技术管理SDS需要更新提醒");
                techSDSReminderPanel.setVisible(true);
            }
        });
    }

    /**
     * 异步获取TS SDS到期提醒
     */
    private void asynchronousGetTSSDSReminder() {
        asynchronousRun(new InvokeRunnable() {
            @Override
            public void run() throws RemoteException {
                List<TSSDSPermission> reminderList = getTsService().getTSSDSEndDateReminder();
                Object[] column = {"TS工程师姓名", "到期时间"};
                Object[][] data = new Object[(reminderList.size())][2];
                if (CollectionUtil.isNotEmpty(reminderList)) {
                    for (int i = 0; i < reminderList.size(); i++) {
                        TSSDSPermission permission = reminderList.get(i);
                        data[i][0] = permission.getEngineerName();
                        data[i][1] = permission.getEndDate();
                    }
                }
                tsSDSReminderTable.setModel(new DefaultTableModel(data, column));
                ((TitledBorder) tsSDSReminderPanel.getBorder()).setTitle("TS管理SDS需要更新提醒");
                tsSDSReminderPanel.setVisible(true);
            }
        });
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

        asynchronousGetTSSDSReminder();
        asynchronousGetTechSDSReminder();
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
        doRunWithExHandler(this, new OpRunnable() {
            @Override
            public void run() {
                File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
                if (srcFile == null) return;
                getShopService().importShops(srcFile);
                new OperationHint(self, "导入成功");
            }
        });
    }

    private void exportShopBasicActionPerformed(ActionEvent e) {
        doRunWithExHandler(this, new OpRunnable() {
            @Override
            public void run() {
                File targetFile = chooseExportFile("excel文件", "xlsx");
                if (targetFile == null) return;
                getShopService().exportShops(targetFile);
                new OperationHint(self, "导出成功");
            }
        });
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
        doRunWithExHandler(this, new OpRunnable() {
            @Override
            public void run() {
                final File targetFile = chooseExportFile("压缩文件", "zip");
                if (targetFile == null) return;
                doRunWithWaiting(self, new OpRunnable() {
                    @Override
                    public void run() {
                        getDbService().backupDB(targetFile.getPath());
                    }
                }, "备份成功");
            }
        });
    }

    private void recoveryDBActionPerformed(ActionEvent e) {
        doRunWithExHandler(this, new OpRunnable() {
            @Override
            public void run() {
                final File srcFile = chooseImportFile("压缩文件", "zip");
                if (srcFile == null) return;
                doRunWithWaiting(self, new OpRunnable() {
                    @Override
                    public void run() {
                        getDbService().recovery(srcFile.getPath());
                    }
                }, "恢复成功");
            }
        });
    }

    private void importEngineerBasicActionPerformed(ActionEvent e) {
        doRunWithExHandler(this, new OpRunnable() {
            @Override
            public void run() {
                File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
                if (srcFile == null) return;
                getEngineerService().importEngineers(srcFile);
                new OperationHint(self, "导入成功");
            }
        });
    }

    private void exportEngineerBasicActionPerformed(ActionEvent e) {
        doRunWithExHandler(this, new OpRunnable() {
            @Override
            public void run() {
                File targetFile = chooseExportFile("excel文件", "xlsx");
                if (targetFile == null) return;
                getEngineerService().exportEngineers(targetFile);
                new OperationHint(self, "导出成功");
            }
        });
    }

    private void importEngineerTrainingActionPerformed(ActionEvent e) {
        doRunWithExHandler(this, new OpRunnable() {
            @Override
            public void run() {
                File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
                if (srcFile == null) return;
                getEngineerService().importEngineerTrainings(srcFile);
                new OperationHint(self, "导入成功");
            }
        });
    }

    private void queryEngineerTrainingActionPerformed(ActionEvent e) {
        new QueryFrame<EngineerTraining>(getEngineerService().queryAllTrainings(), new EngineerTrainingQueryObject());
    }

    private void exportEngineerTrainingActionPerformed(ActionEvent e) {
        doRunWithExHandler(this, new OpRunnable() {
            @Override
            public void run() {
                File targetFile = chooseExportFile("excel文件", "xlsx");
                if (targetFile == null) return;
                getEngineerService().exportTrainings(targetFile);
                new OperationHint(self, "导出成功");
            }
        });
    }

    private void countEngineerByProductLineActionPerformed(ActionEvent e) {
        doRunWithExHandler(this, new OpRunnable() {
            @Override
            public void run() {
                File targetFile = chooseExportFile("excel文件", "xlsx");
                if (targetFile == null) return;
                getEngineerService().countEngineersByProductLine(targetFile);
                new OperationHint(self, "导出成功");
            }
        });
    }

    private void countEngineerByShopActionPerformed(ActionEvent e) {
        doRunWithExHandler(this, new OpRunnable() {
            @Override
            public void run() {
                final File targetFile = chooseExportFile("excel文件", "xlsx");
                if (targetFile == null) return;
                doRunWithWaiting(self, new OpRunnable() {
                    @Override
                    public void run() {
                        getEngineerService().countEngineersByShop(targetFile);
                    }
                }, "导出成功");
            }
        });
    }

    private void importShopDetailActionPerformed(ActionEvent e) {
        doRunWithExHandler(this, new OpRunnable() {
            @Override
            public void run() {
                File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
                if (srcFile == null) return;
                getShopService().importShopDetails(srcFile);
                new OperationHint(self, "导入成功");
            }
        });
    }

    private void queryShopDetailActionPerformed(ActionEvent e) {
        new QueryFrame<ShopDetail>(getShopService().queryAllDetails(), new ShopDetailQueryObject());
    }

    private void exportShopDetailActionPerformed(ActionEvent e) {
        doRunWithExHandler(self, new OpRunnable() {
            @Override
            public void run() {
                InputHint inputHint = new InputHint(self, new String[]{"导出认定年份"});
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
                new OperationHint(self, "导出成功");
            }
        });
    }

    private void exportAllActionPerformed(ActionEvent e) {
        doRunWithExHandler(this, new OpRunnable() {
            @Override
            public void run() {
                final File targetFile = chooseExportFile("excel文件", "xls");
                if (targetFile == null) return;
                doRunWithWaiting(self, new OpRunnable() {
                    @Override
                    public void run() {
                        getExportService().exportShopAll(targetFile);
                    }
                }, "导出成功");
            }
        });
    }

    private void genCardMenuActionPerformed(ActionEvent e) {
        doRunWithExHandler(this, new OpRunnable() {
            @Override
            public void run() {
                doRunWithWaiting(self, new OpRunnable() {
                    @Override
                    public void run() {
                        getCardService().generateCards();
                    }
                }, "生成成功");
            }
        });
    }

    private void importCardApplicationRecordActionPerformed(ActionEvent e) {
        doRunWithExHandler(this, new OpRunnable() {
            @Override
            public void run() {
                final File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
                if (srcFile == null) return;
                doRunWithWaiting(self, new OpRunnable() {
                    @Override
                    public void run() {
                        getCardService().importCardApplicationRecords(srcFile);
                    }
                }, "导入成功");
            }
        });
    }

    private void queryCardApplicationRecordActionPerformed(ActionEvent e) {
        new PageQueryFrame<CardApplicationRecord>(new CardApplicationRecordQueryObject());
    }

    private void exportCardApplicationRecordActionPerformed(ActionEvent e) {
        doRunWithExHandler(this, new OpRunnable() {
            @Override
            public void run() {
                final File targetFile = chooseExportFile("excel文件", "xlsx");
                if (targetFile == null) return;
                doRunWithWaiting(self, new OpRunnable() {
                    @Override
                    public void run() {
                        getCardService().exportCardApplicationRecords(targetFile);
                    }
                }, "导出成功");
            }
        });
    }

    private void countShopEquipmentActionPerformed(ActionEvent e) {
        doRunWithExHandler(self, new OpRunnable() {
            @Override
            public void run() {
                final File targetFile = chooseExportFile("excel文件", "xlsx");
                if (targetFile == null) return;
                doRunWithWaiting(self, new OpRunnable() {
                    @Override
                    public void run() {
                        getShopService().countShopEquipment(targetFile);
                    }
                }, "导出成功");
            }
        });
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
        doRunWithExHandler(self, new OpRunnable() {
            @Override
            public void run() {
                final File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
                if (srcFile == null) return;
                doRunWithWaiting(self, new OpRunnable() {
                    @Override
                    public void run() {
                        getWarrantyService().importWarrantyFee(srcFile);
                    }
                }, "导入成功");
            }
        });
    }

    private void exportWarrantyFeeActionPerformed(ActionEvent e) {
        doRunWithExHandler(self, new OpRunnable() {
            @Override
            public void run() {
                InputHint inputHint = new InputHint(self, new String[]{"导出年份"});
                if (!inputHint.isOk()) {
                    return;
                }
                final String numberOfYear = inputHint.getInputResult()[0];
                final File targetFile = chooseExportFile("excel文件", "xls");
                if (targetFile == null) return;
                doRunWithWaiting(self, new OpRunnable() {
                    @Override
                    public void run() {
                        if (StringUtils.isEmpty(numberOfYear)) {
                            getWarrantyService().exportWarrantyFee(targetFile);
                        } else {
                            getWarrantyService().exportWarrantyFee(targetFile, numberOfYear);
                        }
                    }
                }, "导出成功");
            }
        });
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
        new WarrantyPartsQueryFrame(new WarrantyPartsQueryObject());
    }

    private void exportWarrantyPartsActionPerformed(ActionEvent e) {
        doRunWithExHandler(self, new OpRunnable() {
            @Override
            public void run() {
                InputHint inputHint = new InputHint(self, new String[]{"导出年份"});
                if (!inputHint.isOk()) {
                    return;
                }
                final String numberOfYear = inputHint.getInputResult()[0];
                final File targetFile = chooseExportFile("excel文件", "xls");
                if (targetFile == null) return;
                doRunWithWaiting(self, new OpRunnable() {
                    @Override
                    public void run() {
                        if (StringUtils.isEmpty(numberOfYear)) {
                            getWarrantyService().exportWarrantyParts(targetFile);
                        } else {
                            getWarrantyService().exportWarrantyParts(targetFile, numberOfYear);
                        }
                    }
                }, "导出成功");
            }
        });
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
        doRunWithExHandler(self, new OpRunnable() {
            @Override
            public void run() {
                final File targetFile = chooseExportFile("excel文件", "xls");
                if (targetFile == null) return;
                doRunWithWaiting(self, new OpRunnable() {
                    @Override
                    public void run() {
                        getExportService().exportSOIDCode(targetFile);
                    }
                }, "导出成功");
            }
        });
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
        new PageQueryFrame<TSSDSPermission>(new TSSDSPermissionQueryObject());
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

    private void queryTechDongleActionPerformed(ActionEvent e) {
        new PageQueryFrame<TechDongle>(new TechDongleQueryObject());
    }

    private void importTechDongleActionPerformed(ActionEvent e) {
        doRunWithExHandler(self, new OpRunnable() {
            @Override
            public void run() {
                final File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
                if (srcFile == null) return;
                doRunWithWaiting(self, new OpRunnable() {
                    @Override
                    public void run() {
                        getTechService().importTechDongles(srcFile);
                    }
                }, "导入成功");
            }
        });
    }

    private void exportTechDongleActionPerformed(ActionEvent e) {
        doRunWithExHandler(self, new OpRunnable() {
            @Override
            public void run() {
                final File targetFile = chooseExportFile("excel文件", "xls");
                if (targetFile == null) return;
                doRunWithWaiting(self, new OpRunnable() {
                    @Override
                    public void run() {
                        getTechService().exportTechDongles(targetFile);
                    }
                }, "导出成功");
            }
        });
    }

    private void queryTSDongleActionPerformed(ActionEvent e) {
        new PageQueryFrame<TSDongle>(new TSDongleQueryObject());
    }

    private void importTSDongleActionPerformed(ActionEvent e) {
        doRunWithExHandler(self, new OpRunnable() {
            @Override
            public void run() {
                final File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
                if (srcFile == null) return;
                doRunWithWaiting(self, new OpRunnable() {
                    @Override
                    public void run() {
                        getTsService().importTSDongles(srcFile);
                    }
                }, "导入成功");
            }
        });
    }

    private void exportTSDongleActionPerformed(ActionEvent e) {
        doRunWithExHandler(self, new OpRunnable() {
            @Override
            public void run() {
                final File targetFile = chooseExportFile("excel文件", "xls");
                if (targetFile == null) return;
                doRunWithWaiting(self, new OpRunnable() {
                    @Override
                    public void run() {
                        getTsService().exportTSDongles(targetFile);
                    }
                }, "导出成功");
            }
        });
    }

    private void updateShopIdActionPerformed(ActionEvent e) {
        doRunWithExHandler(self, new OpRunnable() {
            @Override
            public void run() {
                final File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
                if (srcFile == null) return;
                doRunWithWaiting(self, new OpRunnable() {
                    @Override
                    public void run() {
                        getShopService().updateShopId(srcFile);
                    }
                }, "更新成功");
            }
        });
    }

    private void queryModelsActionPerformed(ActionEvent e) {
        new PageQueryFrame<Models>(new ModelsQueryObject());
    }

    private void importModelsActionPerformed(ActionEvent e) {
        doRunWithExHandler(self, new OpRunnable() {
            @Override
            public void run() {
                final File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
                if (srcFile == null) return;
                doRunWithWaiting(self, new OpRunnable() {
                    @Override
                    public void run() {
                        getBasicService().importBasicModels(srcFile);
                    }
                }, "导入成功");
            }
        });
    }

    private void exportModelsActionPerformed(ActionEvent e) {
        doRunWithExHandler(self, new OpRunnable() {
            @Override
            public void run() {
                final File targetFile = chooseExportFile("excel文件", "xls");
                if (targetFile == null) return;
                doRunWithWaiting(self, new OpRunnable() {
                    @Override
                    public void run() {
                        getBasicService().exportBasicModels(targetFile);
                    }
                }, "导出成功");
            }
        });
    }

    private void queryTSEngineerActionPerformed(ActionEvent e) {
        new PageQueryFrame<EngineerTS>(new TSEngineerQueryObject());
    }

    private void importTSEngineerActionPerformed(ActionEvent e) {
        doRunWithExHandler(self, new OpRunnable() {
            @Override
            public void run() {
                final File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
                if (srcFile == null) return;
                doRunWithWaiting(self, new OpRunnable() {
                    @Override
                    public void run() {
                        getTsService().importTSEngineer(srcFile);
                    }
                }, "导入成功");
            }
        });
    }

    private void exportTSEngineerActionPerformed(ActionEvent e) {
        doRunWithExHandler(self, new OpRunnable() {
            @Override
            public void run() {
                final File targetFile = chooseExportFile("excel文件", "xls");
                if (targetFile == null) return;
                doRunWithWaiting(self, new OpRunnable() {
                    @Override
                    public void run() {
                        getTsService().exportTSEngineer(targetFile);
                    }
                }, "导出成功");
            }
        });
    }

    private void queryIpfEquipmentActionPerformed(ActionEvent e) {
        new PageQueryFrame<IpfEquipment>(new IpfEquipmentQueryObject());
    }

    private void importIpfEquipmentActionPerformed(ActionEvent e) {
        doRunWithExHandler(self, new OpRunnable() {
            @Override
            public void run() {
                final File srcFile = chooseImportFile("excel文件", "xls", "xlsx");
                if (srcFile == null) return;
                doRunWithWaiting(self, new OpRunnable() {
                    @Override
                    public void run() {
                        getWarrantyService().importIpfEquipment(srcFile);
                    }
                }, "导入成功");
            }
        });
    }

    private void exportIpfEquipmentActionPerformed(ActionEvent e) {
        doRunWithExHandler(self, new OpRunnable() {
            @Override
            public void run() {
                final File targetFile = chooseExportFile("excel文件", "xls");
                if (targetFile == null) return;
                doRunWithWaiting(self, new OpRunnable() {
                    @Override
                    public void run() {
                        getWarrantyService().exportIpfEquipment(targetFile);
                    }
                }, "导出成功");
            }
        });
    }

    private void ipManagementActionPerformed(ActionEvent e) {
        new QueryFrame<IPRegistry>(getLocalRegistryService().queryIPRegistry(), new IPRegistryQueryObject());
    }

    private void queryOperationLogActionPerformed(ActionEvent e) {
        new OnlyQueryFrame<OperationLog>(new OperationLogQueryObject());
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
        ipfEquipment = new JMenu();
        queryIpfEquipment = new JMenuItem();
        importIpfEquipment = new JMenuItem();
        exportIpfEquipment = new JMenuItem();
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
        techDongle = new JMenu();
        queryTechDongle = new JMenuItem();
        importTechDongle = new JMenuItem();
        exportTechDongle = new JMenuItem();
        tsManagement = new JMenu();
        tsEngineer = new JMenu();
        queryTSEngineer = new JMenuItem();
        importTSEngineer = new JMenuItem();
        exportTSEngineer = new JMenuItem();
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
        tsDongle = new JMenu();
        queryTSDongle = new JMenuItem();
        importTSDongle = new JMenuItem();
        exportTSDongle = new JMenuItem();
        basicDataManagement = new JMenu();
        models = new JMenu();
        queryModels = new JMenuItem();
        importModels = new JMenuItem();
        exportModels = new JMenuItem();
        onlineMenu = new JMenu();
        onlineSetting = new JMenuItem();
        startOnline = new JMenuItem();
        onlineConfig = new JMenuItem();
        ipManagement = new JMenuItem();
        helpMenu = new JMenu();
        aboutItem = new JMenuItem();
        queryOperationLog = new JMenuItem();
        backupDB = new JMenuItem();
        recoveryDB = new JMenuItem();
        reportError = new JMenuItem();
        updateShopId = new JMenuItem();
        tsSDSReminderPanel = new JPanel();
        tsSDSReminderScrollPane = new JScrollPane();
        tsSDSReminderTable = new JTable();
        techSDSReminderPanel = new JPanel();
        techSDSReminderScrollPane = new JScrollPane();
        techSDSReminderTable = new JTable();

        //======== this ========
        setTitle("PPP-ERP\u4e3b\u754c\u9762");
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
                    countEngineerByProductLine.setText("\u7edf\u8ba1\u6bcf\u4e2a\u4ea7\u54c1\u7ebf\u7684\u5728\u804c\u5de5\u7a0b\u5e08\u6570");
                    countEngineerByProductLine.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            countEngineerByProductLineActionPerformed(e);
                        }
                    });
                    shopManagement.add(countEngineerByProductLine);

                    //---- countEngineerByShop ----
                    countEngineerByShop.setText("\u7edf\u8ba1\u6bcf\u4e2a\u8ba4\u5b9a\u5e97\u7684\u5728\u804c\u5de5\u7a0b\u5e08\u6570");
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

                //======== ipfEquipment ========
                {
                    ipfEquipment.setText("iPF\u8bbe\u5907\u7ba1\u7406");

                    //---- queryIpfEquipment ----
                    queryIpfEquipment.setText("\u67e5\u8be2");
                    queryIpfEquipment.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queryIpfEquipmentActionPerformed(e);
                        }
                    });
                    ipfEquipment.add(queryIpfEquipment);

                    //---- importIpfEquipment ----
                    importIpfEquipment.setText("\u5bfc\u5165");
                    importIpfEquipment.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            importIpfEquipmentActionPerformed(e);
                        }
                    });
                    ipfEquipment.add(importIpfEquipment);

                    //---- exportIpfEquipment ----
                    exportIpfEquipment.setText("\u5bfc\u51fa");
                    exportIpfEquipment.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportIpfEquipmentActionPerformed(e);
                        }
                    });
                    ipfEquipment.add(exportIpfEquipment);
                }
                warrantyManagement.add(ipfEquipment);

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

                //======== techDongle ========
                {
                    techDongle.setText("dongle\u8bb0\u5f55");

                    //---- queryTechDongle ----
                    queryTechDongle.setText("\u67e5\u8be2");
                    queryTechDongle.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queryTechDongleActionPerformed(e);
                        }
                    });
                    techDongle.add(queryTechDongle);

                    //---- importTechDongle ----
                    importTechDongle.setText("\u5bfc\u5165");
                    importTechDongle.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            importTechDongleActionPerformed(e);
                        }
                    });
                    techDongle.add(importTechDongle);

                    //---- exportTechDongle ----
                    exportTechDongle.setText("\u5bfc\u51fa");
                    exportTechDongle.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportTechDongleActionPerformed(e);
                        }
                    });
                    techDongle.add(exportTechDongle);
                }
                technicalManagement.add(techDongle);
            }
            managerMenuBar.add(technicalManagement);

            //======== tsManagement ========
            {
                tsManagement.setText("TS\u7ba1\u7406");

                //======== tsEngineer ========
                {
                    tsEngineer.setText("\u5de5\u7a0b\u5e08\u57fa\u7840\u4fe1\u606f");

                    //---- queryTSEngineer ----
                    queryTSEngineer.setText("\u67e5\u8be2");
                    queryTSEngineer.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queryTSEngineerActionPerformed(e);
                        }
                    });
                    tsEngineer.add(queryTSEngineer);

                    //---- importTSEngineer ----
                    importTSEngineer.setText("\u5bfc\u5165");
                    importTSEngineer.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            importTSEngineerActionPerformed(e);
                        }
                    });
                    tsEngineer.add(importTSEngineer);

                    //---- exportTSEngineer ----
                    exportTSEngineer.setText("\u5bfc\u51fa");
                    exportTSEngineer.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportTSEngineerActionPerformed(e);
                        }
                    });
                    tsEngineer.add(exportTSEngineer);
                }
                tsManagement.add(tsEngineer);

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

                //======== tsDongle ========
                {
                    tsDongle.setText("dongle\u8bb0\u5f55");

                    //---- queryTSDongle ----
                    queryTSDongle.setText("\u67e5\u8be2");
                    queryTSDongle.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queryTSDongleActionPerformed(e);
                        }
                    });
                    tsDongle.add(queryTSDongle);

                    //---- importTSDongle ----
                    importTSDongle.setText("\u5bfc\u5165");
                    importTSDongle.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            importTSDongleActionPerformed(e);
                        }
                    });
                    tsDongle.add(importTSDongle);

                    //---- exportTSDongle ----
                    exportTSDongle.setText("\u5bfc\u51fa");
                    exportTSDongle.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportTSDongleActionPerformed(e);
                        }
                    });
                    tsDongle.add(exportTSDongle);
                }
                tsManagement.add(tsDongle);
            }
            managerMenuBar.add(tsManagement);

            //======== basicDataManagement ========
            {
                basicDataManagement.setText("\u57fa\u7840\u6570\u636e");

                //======== models ========
                {
                    models.setText("\u673a\u578b\u5206\u7c7b");

                    //---- queryModels ----
                    queryModels.setText("\u67e5\u8be2");
                    queryModels.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            queryModelsActionPerformed(e);
                        }
                    });
                    models.add(queryModels);

                    //---- importModels ----
                    importModels.setText("\u5bfc\u5165");
                    importModels.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            importModelsActionPerformed(e);
                        }
                    });
                    models.add(importModels);

                    //---- exportModels ----
                    exportModels.setText("\u5bfc\u51fa");
                    exportModels.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportModelsActionPerformed(e);
                        }
                    });
                    models.add(exportModels);
                }
                basicDataManagement.add(models);
            }
            managerMenuBar.add(basicDataManagement);

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

                //---- ipManagement ----
                ipManagement.setText("\u7ba1\u7406\u6ce8\u518cIP");
                ipManagement.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ipManagementActionPerformed(e);
                    }
                });
                onlineMenu.add(ipManagement);
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

                //---- queryOperationLog ----
                queryOperationLog.setText("\u64cd\u4f5c\u65e5\u5fd7\u67e5\u8be2");
                queryOperationLog.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        queryOperationLogActionPerformed(e);
                    }
                });
                helpMenu.add(queryOperationLog);

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

                //---- updateShopId ----
                updateShopId.setText("\u66f4\u65b0\u8ba4\u5b9a\u5e97\u7f16\u53f7");
                updateShopId.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        updateShopIdActionPerformed(e);
                    }
                });
                helpMenu.add(updateShopId);
            }
            managerMenuBar.add(helpMenu);
        }
        setJMenuBar(managerMenuBar);

        //======== tsSDSReminderPanel ========
        {
            tsSDSReminderPanel.setBorder(new TitledBorder("\u6b63\u5728\u83b7\u53d6SDS\u9700\u8981\u66f4\u65b0\u63d0\u9192......"));
            tsSDSReminderPanel.setLayout(null);

            //======== tsSDSReminderScrollPane ========
            {
                tsSDSReminderScrollPane.setViewportView(tsSDSReminderTable);
            }
            tsSDSReminderPanel.add(tsSDSReminderScrollPane);
            tsSDSReminderScrollPane.setBounds(10, 25, 325, 215);
        }
        contentPane.add(tsSDSReminderPanel);
        tsSDSReminderPanel.setBounds(15, 10, 345, 255);

        //======== techSDSReminderPanel ========
        {
            techSDSReminderPanel.setBorder(new TitledBorder("\u6b63\u5728\u83b7\u53d6SDS\u9700\u8981\u66f4\u65b0\u63d0\u9192......"));
            techSDSReminderPanel.setLayout(null);

            //======== techSDSReminderScrollPane ========
            {
                techSDSReminderScrollPane.setViewportView(techSDSReminderTable);
            }
            techSDSReminderPanel.add(techSDSReminderScrollPane);
            techSDSReminderScrollPane.setBounds(10, 25, 325, 215);
        }
        contentPane.add(techSDSReminderPanel);
        techSDSReminderPanel.setBounds(365, 10, 345, 255);

        contentPane.setPreferredSize(new Dimension(960, 530));
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
    private JMenu ipfEquipment;
    private JMenuItem queryIpfEquipment;
    private JMenuItem importIpfEquipment;
    private JMenuItem exportIpfEquipment;
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
    private JMenu techDongle;
    private JMenuItem queryTechDongle;
    private JMenuItem importTechDongle;
    private JMenuItem exportTechDongle;
    private JMenu tsManagement;
    private JMenu tsEngineer;
    private JMenuItem queryTSEngineer;
    private JMenuItem importTSEngineer;
    private JMenuItem exportTSEngineer;
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
    private JMenu tsDongle;
    private JMenuItem queryTSDongle;
    private JMenuItem importTSDongle;
    private JMenuItem exportTSDongle;
    private JMenu basicDataManagement;
    private JMenu models;
    private JMenuItem queryModels;
    private JMenuItem importModels;
    private JMenuItem exportModels;
    private JMenu onlineMenu;
    private JMenuItem onlineSetting;
    private JMenuItem startOnline;
    private JMenuItem onlineConfig;
    private JMenuItem ipManagement;
    private JMenu helpMenu;
    private JMenuItem aboutItem;
    private JMenuItem queryOperationLog;
    private JMenuItem backupDB;
    private JMenuItem recoveryDB;
    private JMenuItem reportError;
    private JMenuItem updateShopId;
    private JPanel tsSDSReminderPanel;
    private JScrollPane tsSDSReminderScrollPane;
    private JTable tsSDSReminderTable;
    private JPanel techSDSReminderPanel;
    private JScrollPane techSDSReminderScrollPane;
    private JTable techSDSReminderTable;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
