package kaitou.ppp.service.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.domain.tech.*;
import kaitou.ppp.manager.shop.ShopManager;
import kaitou.ppp.manager.system.RemoteRegistryManager;
import kaitou.ppp.manager.system.SystemSettingsManager;
import kaitou.ppp.manager.tech.*;
import kaitou.ppp.rmi.ServiceClient;
import kaitou.ppp.rmi.service.RemoteTechService;
import kaitou.ppp.service.BaseExcelService;
import kaitou.ppp.service.TechService;

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * 技术管理服务实现.
 * User: 赵立伟
 * Date: 2015/5/19
 * Time: 10:39
 */
public class TechServiceImpl extends BaseExcelService implements TechService {

    private ShopManager shopManager;
    private SystemSettingsManager systemSettingsManager;
    private RemoteRegistryManager remoteRegistryManager;

    private SOIDCodeManager soidCodeManager;
    private TechSupportManager techSupportManager;
    private SDSPermissionManager sdsPermissionManager;
    private InstallPermissionManager installPermissionManager;
    private ManualPermissionsManager manualPermissionsManager;

    public void setShopManager(ShopManager shopManager) {
        this.shopManager = shopManager;
    }

    public void setSystemSettingsManager(SystemSettingsManager systemSettingsManager) {
        this.systemSettingsManager = systemSettingsManager;
    }

    public void setRemoteRegistryManager(RemoteRegistryManager remoteRegistryManager) {
        this.remoteRegistryManager = remoteRegistryManager;
    }

    public void setSoidCodeManager(SOIDCodeManager soidCodeManager) {
        this.soidCodeManager = soidCodeManager;
    }

    public void setTechSupportManager(TechSupportManager techSupportManager) {
        this.techSupportManager = techSupportManager;
    }

    public void setInstallPermissionManager(InstallPermissionManager installPermissionManager) {
        this.installPermissionManager = installPermissionManager;
    }

    public void setSdsPermissionManager(SDSPermissionManager sdsPermissionManager) {
        this.sdsPermissionManager = sdsPermissionManager;
    }

    public void setManualPermissionsManager(ManualPermissionsManager manualPermissionsManager) {
        this.manualPermissionsManager = manualPermissionsManager;
    }

    @Override
    public void importManualPermissions(File srcFile) {
        saveOrUpdateManualPermissions(CollectionUtil.toArray(readFromExcel(srcFile, TechManualPermissions.class), TechManualPermissions.class));
    }

    @Override
    public void exportManualPermissions(File targetFile) {
        export2Excel(manualPermissionsManager.query(), targetFile, TechManualPermissions.class);
    }

    @Override
    public List<TechManualPermissions> queryManualPermissions() {
        return manualPermissionsManager.query();
    }

    @Override
    public void saveOrUpdateManualPermissions(TechManualPermissions... techManualPermissions) {
        final List<TechManualPermissions> techManualPermissionsList = new ArrayList<TechManualPermissions>();
        for (TechManualPermissions permissions : techManualPermissions) {
            permissions.setShopId(shopManager.getCachedIdByName(permissions.getShopName()));
            techManualPermissionsList.add(permissions);
        }
        logOperation("成功导入/更新手册权限数：" + manualPermissionsManager.save(techManualPermissionsList));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteTechService> remoteTechServices = ServiceClient.queryServicesOfListener(RemoteTechService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteTechServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新手册权限");
                for (RemoteTechService remoteTechService : remoteTechServices) {
                    try {
                        remoteTechService.saveManualPermissions(techManualPermissionsList);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void deleteManualPermissions(final Object... manualPermissions) {
        logOperation("成功删除手册权限数：" + manualPermissionsManager.delete(manualPermissions));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteTechService> remoteTechServices = ServiceClient.queryServicesOfListener(RemoteTechService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteTechServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新删除手册权限");
                for (RemoteTechService remoteTechService : remoteTechServices) {
                    try {
                        remoteTechService.deleteManualPermissions(manualPermissions);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void importSOIDCode(File srcFile) {
        saveOrUpdateSOIDCode(CollectionUtil.toArray(readFromExcel(srcFile, SOIDCode.class), SOIDCode.class));
    }

    @Override
    public void exportSOIDCode(File targetFile) {
        export2Excel(soidCodeManager.query(), targetFile, SOIDCode.class);
    }

    @Override
    public List<SOIDCode> querySOIDCode() {
        return soidCodeManager.query();
    }

    @Override
    public void saveOrUpdateSOIDCode(SOIDCode... soidCodes) {
        final List<SOIDCode> soidCodeList = new ArrayList<SOIDCode>();
        for (SOIDCode soidCode : soidCodes) {
            soidCode.setShopId(shopManager.getCachedIdByName(soidCode.getShopName()));
            soidCodeList.add(soidCode);
        }
        logOperation("成功导入/更新SOID识别码数：" + soidCodeManager.save(soidCodeList));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteTechService> remoteTechServices = ServiceClient.queryServicesOfListener(RemoteTechService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteTechServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新SOID识别码");
                for (RemoteTechService remoteTechService : remoteTechServices) {
                    try {
                        remoteTechService.saveSOIDCode(soidCodeList);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void deleteSOIDCode(final Object... soidCodes) {
        logOperation("成功删除SOID识别码数：" + soidCodeManager.delete(soidCodes));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteTechService> remoteTechServices = ServiceClient.queryServicesOfListener(RemoteTechService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteTechServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新删除SOID识别码");
                for (RemoteTechService remoteTechService : remoteTechServices) {
                    try {
                        remoteTechService.deleteSOIDCode(soidCodes);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void importSDSPermissions(File srcFile) {
        saveOrUpdateSDSPermissions(CollectionUtil.toArray(readFromExcel(srcFile, TechSDSPermission.class), TechSDSPermission.class));
    }

    @Override
    public void exportSDSPermissions(File targetFile) {
        export2Excel(sdsPermissionManager.query(), targetFile, TechSDSPermission.class);
    }

    @Override
    public List<TechSDSPermission> querySDSPermissions() {
        return sdsPermissionManager.query();
    }

    @Override
    public void saveOrUpdateSDSPermissions(TechSDSPermission... techSdsPermissions) {
        final List<TechSDSPermission> techSdsPermissionList = new ArrayList<TechSDSPermission>();
        for (TechSDSPermission techSdsPermission : techSdsPermissions) {
            techSdsPermission.setShopId(shopManager.getCachedIdByName(techSdsPermission.getShopName()));
            techSdsPermissionList.add(techSdsPermission);
        }
        logOperation("成功导入/更新SDS权限数：" + sdsPermissionManager.save(techSdsPermissionList));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteTechService> remoteTechServices = ServiceClient.queryServicesOfListener(RemoteTechService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteTechServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新SDS权限");
                for (RemoteTechService remoteTechService : remoteTechServices) {
                    try {
                        remoteTechService.saveSDSPermission(techSdsPermissionList);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void deleteSDSPermissions(final Object... sdsPermissions) {
        logOperation("成功删除SDS权限数：" + sdsPermissionManager.delete(sdsPermissions));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteTechService> remoteTechServices = ServiceClient.queryServicesOfListener(RemoteTechService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteTechServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新删除SDS权限");
                for (RemoteTechService remoteTechService : remoteTechServices) {
                    try {
                        remoteTechService.deleteSDSPermission(sdsPermissions);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void importTechSupport(File srcFile) {
        saveOrUpdateTechSupport(CollectionUtil.toArray(readFromExcel(srcFile, TechSupport.class), TechSupport.class));
    }

    @Override
    public void exportTechSupport(File targetFile, String... numberOfYear) {
        export2Excel(techSupportManager.query(numberOfYear), targetFile, TechSupport.class);
    }

    @Override
    public List<TechSupport> queryTechSupport() {
        return techSupportManager.query();
    }

    @Override
    public void saveOrUpdateTechSupport(TechSupport... techSupport) {
        final List<TechSupport> techSupportList = new ArrayList<TechSupport>();
        for (TechSupport support : techSupport) {
            support.setShopId(shopManager.getCachedIdByName(support.getShopName()));
            techSupportList.add(support);
        }
        logOperation("成功导入/更新技术支援数：" + techSupportManager.save(techSupportList));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteTechService> remoteTechServices = ServiceClient.queryServicesOfListener(RemoteTechService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteTechServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新技术支援");
                for (RemoteTechService remoteTechService : remoteTechServices) {
                    try {
                        remoteTechService.saveTechSupport(techSupportList);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void deleteTechSupport(final Object... techSupport) {
        logOperation("成功删除技术支援数：" + techSupportManager.delete(techSupport));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteTechService> remoteTechServices = ServiceClient.queryServicesOfListener(RemoteTechService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteTechServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新删除技术支援");
                for (RemoteTechService remoteTechService : remoteTechServices) {
                    try {
                        remoteTechService.deleteTechSupport(techSupport);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void importInstallPermission(File srcFile) {
        saveOrUpdateInstallPermission(CollectionUtil.toArray(readFromExcel(srcFile, TechInstallPermission.class), TechInstallPermission.class));
    }

    @Override
    public void exportInstallPermission(File targetFile) {
        export2Excel(installPermissionManager.queryAll(), targetFile, TechInstallPermission.class);
    }

    @Override
    public List<TechInstallPermission> queryInstallPermission() {
        return installPermissionManager.queryAll();
    }

    @Override
    public void saveOrUpdateInstallPermission(TechInstallPermission... techInstallPermission) {
        final List<TechInstallPermission> techInstallPermissionList = new ArrayList<TechInstallPermission>();
        for (TechInstallPermission permission : techInstallPermission) {
            permission.setShopId(shopManager.getCachedIdByName(permission.getShopName()));
            techInstallPermissionList.add(permission);
        }
        logOperation("成功导入/更新装机权限数：" + installPermissionManager.save(techInstallPermissionList));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteTechService> remoteTechServices = ServiceClient.queryServicesOfListener(RemoteTechService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteTechServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新装机权限");
                for (RemoteTechService remoteTechService : remoteTechServices) {
                    try {
                        remoteTechService.saveInstallPermission(techInstallPermissionList);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void deleteInstallPermission(final Object... installPermission) {
        logOperation("成功删除装机权限数：" + installPermissionManager.delete(installPermission));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteTechService> remoteTechServices = ServiceClient.queryServicesOfListener(RemoteTechService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteTechServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新删除装机权限");
                for (RemoteTechService remoteTechService : remoteTechServices) {
                    try {
                        remoteTechService.deleteInstallPermission(installPermission);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }
}
