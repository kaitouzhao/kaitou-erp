package kaitou.ppp.service.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.dao.support.Condition;
import kaitou.ppp.dao.support.Pager;
import kaitou.ppp.domain.ts.*;
import kaitou.ppp.manager.system.RemoteRegistryManager;
import kaitou.ppp.manager.system.SystemSettingsManager;
import kaitou.ppp.manager.ts.*;
import kaitou.ppp.rmi.ServiceClient;
import kaitou.ppp.rmi.service.RemoteTSService;
import kaitou.ppp.service.BaseExcelService;
import kaitou.ppp.service.ServiceInvokeManager;
import kaitou.ppp.service.TSService;

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static kaitou.ppp.service.ServiceInvokeManager.asynchronousRun;
import static kaitou.ppp.service.ServiceInvokeManager.queryRemoteService;

/**
 * TS管理服务层实现.
 * User: 赵立伟
 * Date: 2015/5/20
 * Time: 11:23
 */
public class TSServiceImpl extends BaseExcelService implements TSService {

    private TsDongleManager tsDongleManager;
    private TSTrainingManager tsTrainingManager;
    private ToolRecipientsManager toolRecipientsManager;
    private TSSDSPermissionManager tssdsPermissionManager;
    private OldMachineRenewManager oldMachineRenewManager;
    private NewMachineClaimManager newMachineClaimManager;
    private ComponentBorrowingManager componentBorrowingManager;
    private TSInstallPermissionManager tsInstallPermissionManager;
    private TSManualPermissionsManager tsManualPermissionsManager;

    private SystemSettingsManager systemSettingsManager;
    private RemoteRegistryManager remoteRegistryManager;

    public void setTsDongleManager(TsDongleManager tsDongleManager) {
        this.tsDongleManager = tsDongleManager;
    }

    public void setToolRecipientsManager(ToolRecipientsManager toolRecipientsManager) {
        this.toolRecipientsManager = toolRecipientsManager;
    }

    public void setComponentBorrowingManager(ComponentBorrowingManager componentBorrowingManager) {
        this.componentBorrowingManager = componentBorrowingManager;
    }

    public void setTsTrainingManager(TSTrainingManager tsTrainingManager) {
        this.tsTrainingManager = tsTrainingManager;
    }

    public void setNewMachineClaimManager(NewMachineClaimManager newMachineClaimManager) {
        this.newMachineClaimManager = newMachineClaimManager;
    }

    public void setOldMachineRenewManager(OldMachineRenewManager oldMachineRenewManager) {
        this.oldMachineRenewManager = oldMachineRenewManager;
    }

    public void setTsInstallPermissionManager(TSInstallPermissionManager tsInstallPermissionManager) {
        this.tsInstallPermissionManager = tsInstallPermissionManager;
    }

    public void setTsSDSPermissionManager(TSSDSPermissionManager tsSDSPermissionManager) {
        this.tssdsPermissionManager = tsSDSPermissionManager;
    }

    public void setTsManualPermissionsManager(TSManualPermissionsManager tsManualPermissionsManager) {
        this.tsManualPermissionsManager = tsManualPermissionsManager;
    }

    public void setSystemSettingsManager(SystemSettingsManager systemSettingsManager) {
        this.systemSettingsManager = systemSettingsManager;
    }

    public void setRemoteRegistryManager(RemoteRegistryManager remoteRegistryManager) {
        this.remoteRegistryManager = remoteRegistryManager;
    }

    @Override
    public void importTSTraining(File srcFile) {
        saveOrUpdateTSTraining(CollectionUtil.toArray(readFromExcel(srcFile, TSTraining.class), TSTraining.class));
    }

    @Override
    public void exportTSTraining(File targetFile, String... numberOfYear) {
        export2Excel(tsTrainingManager.query(numberOfYear), targetFile, TSTraining.class);
    }

    @Override
    public List<TSTraining> queryTSTraining() {
        return tsTrainingManager.query();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void saveOrUpdateTSTraining(TSTraining... tsTraining) {
        if (CollectionUtil.isEmpty(tsTraining)) {
            return;
        }
        final List<TSTraining> tsTrainingList = CollectionUtil.newList(tsTraining);
        logOperation("成功导入/更新TS培训记录数：" + tsTrainingManager.save(tsTrainingList));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteTSService> remoteTSServices = ServiceClient.queryServicesOfListener(RemoteTSService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteTSServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新TS培训记录");
                for (RemoteTSService remoteTSService : remoteTSServices) {
                    try {
                        remoteTSService.saveTSTraining(tsTrainingList);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void deleteTSTraining(final Object... tsTraining) {
        if (CollectionUtil.isEmpty(tsTraining)) {
            return;
        }
        logOperation("成功删除TS培训记录数：" + tsTrainingManager.delete(tsTraining));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteTSService> remoteTSServices = ServiceClient.queryServicesOfListener(RemoteTSService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteTSServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新删除TS培训记录");
                for (RemoteTSService remoteTSService : remoteTSServices) {
                    try {
                        remoteTSService.deleteTSTraining(tsTraining);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void importTSManualPermission(File srcFile) {
        saveOrUpdateTSManualPermission(CollectionUtil.toArray(readFromExcel(srcFile, TSManualPermissions.class), TSManualPermissions.class));
    }

    @Override
    public void exportTSManualPermission(File targetFile, String... numberOfYear) {
        export2Excel(tsManualPermissionsManager.queryAll(), targetFile, TSManualPermissions.class);
    }

    @Override
    public List<TSManualPermissions> queryTSManualPermission() {
        return tsManualPermissionsManager.queryAll();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void saveOrUpdateTSManualPermission(TSManualPermissions... tsManualPermissions) {
        if (CollectionUtil.isEmpty(tsManualPermissions)) {
            return;
        }
        final List<TSManualPermissions> tsManualPermissionsList = CollectionUtil.newList(tsManualPermissions);
        logOperation("成功导入/更新TS手册权限数：" + tsManualPermissionsManager.save(tsManualPermissionsList));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteTSService> remoteTSServices = ServiceClient.queryServicesOfListener(RemoteTSService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteTSServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新TS手册权限");
                for (RemoteTSService remoteTSService : remoteTSServices) {
                    try {
                        remoteTSService.saveTSManualPermission(tsManualPermissionsList);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void deleteTSManualPermission(final Object... tsManualPermission) {
        if (CollectionUtil.isEmpty(tsManualPermission)) {
            return;
        }
        logOperation("成功删除TS手册权限数：" + tsManualPermissionsManager.delete(tsManualPermission));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteTSService> remoteTSServices = ServiceClient.queryServicesOfListener(RemoteTSService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteTSServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新删除TS手册权限");
                for (RemoteTSService remoteTSService : remoteTSServices) {
                    try {
                        remoteTSService.deleteTSManualPermission(tsManualPermission);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void importTSSDSPermission(File srcFile) {
        saveOrUpdateTSSDSPermission(CollectionUtil.toArray(readFromExcel(srcFile, TSSDSPermission.class), TSSDSPermission.class));
    }

    @Override
    public void exportTSSDSPermission(File targetFile) {
        export2Excel(tssdsPermissionManager.queryAll(), targetFile, TSSDSPermission.class);
    }

    @Override
    public List<TSSDSPermission> queryTSSDSPermission() {
        return tssdsPermissionManager.queryAll();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void saveOrUpdateTSSDSPermission(TSSDSPermission... tsSDSPermissions) {
        if (CollectionUtil.isEmpty(tsSDSPermissions)) {
            return;
        }
        final List<TSSDSPermission> tsSDSPermissionList = CollectionUtil.newList(tsSDSPermissions);
        logOperation("成功导入/更新TS SDS权限数：" + tssdsPermissionManager.save(tsSDSPermissionList));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteTSService> remoteTSServices = ServiceClient.queryServicesOfListener(RemoteTSService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteTSServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新TS SDS权限");
                for (RemoteTSService remoteTSService : remoteTSServices) {
                    try {
                        remoteTSService.saveTSSDSPermission(tsSDSPermissionList);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void deleteTSSDSPermission(final Object... tsSDSPermission) {
        if (CollectionUtil.isEmpty(tsSDSPermission)) {
            return;
        }
        logOperation("成功删除TS SDS权限数：" + tssdsPermissionManager.delete(tsSDSPermission));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteTSService> remoteTSServices = ServiceClient.queryServicesOfListener(RemoteTSService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteTSServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新删除TS SDS权限");
                for (RemoteTSService remoteTSService : remoteTSServices) {
                    try {
                        remoteTSService.deleteTSSDSPermission(tsSDSPermission);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public List<TSSDSPermission> getTSSDSEndDateReminder() {
        List<TSSDSPermission> permissionList = tssdsPermissionManager.queryAll();
        List<TSSDSPermission> reminderList = new ArrayList<TSSDSPermission>();
        if (CollectionUtil.isEmpty(permissionList)) {
            return reminderList;
        }
        for (TSSDSPermission permission : permissionList) {
            if (permission.shouldReminder()) {
                reminderList.add(permission);
            }
        }
        return reminderList;
    }

    @Override
    public Pager<TSSDSPermission> queryTSSDSPermissionPager(int currentPage, List<Condition> conditions) {
        return tssdsPermissionManager.queryPager(currentPage, conditions);
    }

    @Override
    public List<TSSDSPermission> queryTSSDSPermission(List<Condition> conditions) {
        return tssdsPermissionManager.queryAll(conditions);
    }

    @Override
    public void importTSInstallPermission(File srcFile) {
        saveOrUpdateTSInstallPermission(CollectionUtil.toArray(readFromExcel(srcFile, TSInstallPermission.class), TSInstallPermission.class));
    }

    @Override
    public void exportTSInstallPermission(File targetFile) {
        export2Excel(tsInstallPermissionManager.queryAll(), targetFile, TSInstallPermission.class);
    }

    @Override
    public List<TSInstallPermission> queryTSInstallPermission() {
        return tsInstallPermissionManager.queryAll();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void saveOrUpdateTSInstallPermission(TSInstallPermission... tsInstallPermissions) {
        if (CollectionUtil.isEmpty(tsInstallPermissions)) {
            return;
        }
        final List<TSInstallPermission> tsInstallPermissionList = CollectionUtil.newList(tsInstallPermissions);
        logOperation("成功导入/更新TS装机权限数：" + tsInstallPermissionManager.save(tsInstallPermissionList));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteTSService> remoteTSServices = ServiceClient.queryServicesOfListener(RemoteTSService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteTSServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新TS装机权限");
                for (RemoteTSService remoteTSService : remoteTSServices) {
                    try {
                        remoteTSService.saveTSInstallPermission(tsInstallPermissionList);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void deleteTSInstallPermission(final Object... tsInstallPermission) {
        if (CollectionUtil.isEmpty(tsInstallPermission)) {
            return;
        }
        logOperation("成功删除TS装机权限数：" + tsInstallPermissionManager.delete(tsInstallPermission));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteTSService> remoteTSServices = ServiceClient.queryServicesOfListener(RemoteTSService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteTSServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新删除TS装机权限");
                for (RemoteTSService remoteTSService : remoteTSServices) {
                    try {
                        remoteTSService.deleteTSInstallPermission(tsInstallPermission);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void importOldMachineRenew(File srcFile) {
        saveOrUpdateOldMachineRenew(CollectionUtil.toArray(readFromExcel(srcFile, OldMachineRenew.class), OldMachineRenew.class));
    }

    @Override
    public void exportOldMachineRenew(File targetFile, String... numberOfYear) {
        export2Excel(oldMachineRenewManager.query(numberOfYear), targetFile, OldMachineRenew.class);
    }

    @Override
    public List<OldMachineRenew> queryOldMachineRenew() {
        return oldMachineRenewManager.query();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void saveOrUpdateOldMachineRenew(OldMachineRenew... oldMachineRenew) {
        if (CollectionUtil.isEmpty(oldMachineRenew)) {
            return;
        }
        final List<OldMachineRenew> oldMachineRenewList = CollectionUtil.newList(oldMachineRenew);
        logOperation("成功导入/更新旧机翻新记录数：" + oldMachineRenewManager.save(oldMachineRenewList));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteTSService> remoteTSServices = ServiceClient.queryServicesOfListener(RemoteTSService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteTSServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新旧机翻新记录");
                for (RemoteTSService remoteTSService : remoteTSServices) {
                    try {
                        remoteTSService.saveOldMachineRenew(oldMachineRenewList);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void deleteOldMachineRenew(final Object... oldMachineRenew) {
        if (CollectionUtil.isEmpty(oldMachineRenew)) {
            return;
        }
        logOperation("成功删除旧机翻新记录数：" + oldMachineRenewManager.delete(oldMachineRenew));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteTSService> remoteTSServices = ServiceClient.queryServicesOfListener(RemoteTSService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteTSServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新删除旧机翻新记录");
                for (RemoteTSService remoteTSService : remoteTSServices) {
                    try {
                        remoteTSService.deleteOldMachineRenew(oldMachineRenew);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void importNewMachineClaim(File srcFile) {
        saveOrUpdateNewMachineClaim(CollectionUtil.toArray(readFromExcel(srcFile, NewMachineClaim.class), NewMachineClaim.class));
    }

    @Override
    public void exportNewMachineClaim(File targetFile, String... numberOfYear) {
        export2Excel(newMachineClaimManager.query(numberOfYear), targetFile, NewMachineClaim.class);
    }

    @Override
    public List<NewMachineClaim> queryNewMachineClaim() {
        return newMachineClaimManager.query();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void saveOrUpdateNewMachineClaim(NewMachineClaim... newMachineClaim) {
        if (CollectionUtil.isEmpty(newMachineClaim)) {
            return;
        }
        final List<NewMachineClaim> newMachineClaimList = CollectionUtil.newList(newMachineClaim);
        logOperation("成功导入/更新新装机索赔记录数：" + newMachineClaimManager.save(newMachineClaimList));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteTSService> remoteTSServices = ServiceClient.queryServicesOfListener(RemoteTSService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteTSServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新新装机索赔记录");
                for (RemoteTSService remoteTSService : remoteTSServices) {
                    try {
                        remoteTSService.saveNewMachineClaim(newMachineClaimList);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void deleteNewMachineClaim(final Object... newMachineClaim) {
        if (CollectionUtil.isEmpty(newMachineClaim)) {
            return;
        }
        logOperation("成功删除新装机索赔记录数：" + newMachineClaimManager.delete(newMachineClaim));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteTSService> remoteTSServices = ServiceClient.queryServicesOfListener(RemoteTSService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteTSServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新删除新装机索赔记录");
                for (RemoteTSService remoteTSService : remoteTSServices) {
                    try {
                        remoteTSService.deleteNewMachineClaim(newMachineClaim);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void importToolRecipients(File srcFile) {
        saveOrUpdateToolRecipients(CollectionUtil.toArray(readFromExcel(srcFile, ToolRecipients.class), ToolRecipients.class));
    }

    @Override
    public void exportToolRecipients(File targetFile, String... numberOfYear) {
        export2Excel(toolRecipientsManager.query(numberOfYear), targetFile, ToolRecipients.class);
    }

    @Override
    public List<ToolRecipients> queryToolRecipients() {
        return toolRecipientsManager.query();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void saveOrUpdateToolRecipients(ToolRecipients... toolRecipients) {
        if (CollectionUtil.isEmpty(toolRecipients)) {
            return;
        }
        final List<ToolRecipients> toolRecipientsList = CollectionUtil.newList(toolRecipients);
        logOperation("成功导入/更新工具领用记录数：" + toolRecipientsManager.save(toolRecipientsList));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteTSService> remoteTSServices = ServiceClient.queryServicesOfListener(RemoteTSService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteTSServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新工具领用记录");
                for (RemoteTSService remoteTSService : remoteTSServices) {
                    try {
                        remoteTSService.saveToolRecipients(toolRecipientsList);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void deleteToolRecipients(final Object... toolRecipients) {
        if (CollectionUtil.isEmpty(toolRecipients)) {
            return;
        }
        logOperation("成功删除工具领用记录数：" + toolRecipientsManager.delete(toolRecipients));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteTSService> remoteTSServices = ServiceClient.queryServicesOfListener(RemoteTSService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteTSServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新删除工具领用记录");
                for (RemoteTSService remoteTSService : remoteTSServices) {
                    try {
                        remoteTSService.deleteToolRecipients(toolRecipients);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void importComponentBorrowing(File srcFile) {
        saveOrUpdateComponentBorrowing(CollectionUtil.toArray(readFromExcel(srcFile, ComponentBorrowing.class), ComponentBorrowing.class));
    }

    @Override
    public void exportComponentBorrowing(File targetFile, String... numberOfYear) {
        export2Excel(componentBorrowingManager.query(numberOfYear), targetFile, ComponentBorrowing.class);
    }

    @Override
    public List<ComponentBorrowing> queryComponentBorrowing() {
        List<ComponentBorrowing> componentBorrowingList = componentBorrowingManager.query();
        Collections.sort(componentBorrowingList, new Comparator<ComponentBorrowing>() {
            @Override
            public int compare(ComponentBorrowing o1, ComponentBorrowing o2) {
                return o1.comparatorBySerialNumber(o2);
            }
        });
        return componentBorrowingList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void saveOrUpdateComponentBorrowing(ComponentBorrowing... componentBorrowing) {
        if (CollectionUtil.isEmpty(componentBorrowing)) {
            return;
        }
        final List<ComponentBorrowing> componentBorrowingList = CollectionUtil.newList(componentBorrowing);
        logOperation("成功导入/更新零件借用记录数：" + componentBorrowingManager.save(componentBorrowingList));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteTSService> remoteTSServices = ServiceClient.queryServicesOfListener(RemoteTSService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteTSServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新零件借用记录");
                for (RemoteTSService remoteTSService : remoteTSServices) {
                    try {
                        remoteTSService.saveComponentBorrowing(componentBorrowingList);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void deleteComponentBorrowing(final Object... componentBorrowing) {
        if (CollectionUtil.isEmpty(componentBorrowing)) {
            return;
        }
        logOperation("成功删除零件借用记录数：" + componentBorrowingManager.delete(componentBorrowing));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteTSService> remoteTSServices = ServiceClient.queryServicesOfListener(RemoteTSService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteTSServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新删除零件借用记录");
                for (RemoteTSService remoteTSService : remoteTSServices) {
                    try {
                        remoteTSService.deleteComponentBorrowing(componentBorrowing);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void importTSDongles(File srcFile) {
        saveOrUpdateTSDongles(CollectionUtil.toArray(readFromExcel(srcFile, TSDongle.class), TSDongle.class));
    }

    @Override
    public void exportTSDongles(File targetFile) {
        export2Excel(tsDongleManager.queryAll(), targetFile, TSDongle.class);
    }

    @Override
    public List<TSDongle> queryTSDongles() {
        return tsDongleManager.queryAll();
    }

    @Override
    public void saveOrUpdateTSDongles(TSDongle... tsDongles) {
        final List<TSDongle> tsDongleList = CollectionUtil.newList(tsDongles);
        logOperation("成功导入/更新TS dongle记录数：" + tsDongleManager.save(tsDongleList));
        asynchronousRun(new ServiceInvokeManager.InvokeRunnable() {
            @Override
            public void run() throws RemoteException {
                List<RemoteTSService> remoteTSServices = queryRemoteService(RemoteTSService.class);
                logOperation("通知已注册的远程服务更新TS dongle记录");
                for (RemoteTSService remoteTsService : remoteTSServices) {
                    remoteTsService.saveTSDongle(tsDongleList);
                }
            }
        });
    }

    @Override
    public void deleteTSDongles(final Object... tsDongles) {
        logOperation("成功删除TS dongle记录数：" + tsDongleManager.delete(tsDongles));
        asynchronousRun(new ServiceInvokeManager.InvokeRunnable() {
            @Override
            public void run() throws RemoteException {
                List<RemoteTSService> remoteTSServices = queryRemoteService(RemoteTSService.class);
                logOperation("通知已注册的远程服务更新删除TS dongle记录");
                for (RemoteTSService remoteTsService : remoteTSServices) {
                    remoteTsService.deleteTSDongle(tsDongles);
                }
            }
        });
    }

    @Override
    public Pager<TSDongle> queryTSDonglePager(int currentPage, List<Condition> conditions) {
        return tsDongleManager.queryPager(currentPage, conditions);
    }

    @Override
    public List<TSDongle> queryTSDongle(List<Condition> conditions) {
        return tsDongleManager.queryAll(conditions);
    }
}
