package kaitou.ppp.service.rmi;

import kaitou.ppp.domain.tech.*;
import kaitou.ppp.manager.tech.*;
import kaitou.ppp.rmi.service.RemoteTechService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * 远程技术管理服务实现.
 * User: 赵立伟
 * Date: 2015/5/19
 * Time: 10:32
 */
public class RemoteTechServiceImpl extends UnicastRemoteObject implements RemoteTechService {

    private SOIDCodeManager soidCodeManager;
    private TechSupportManager techSupportManager;
    private SDSPermissionManager sdsPermissionManager;
    private InstallPermissionManager installPermissionManager;
    private ManualPermissionsManager manualPermissionsManager;

    public void setInstallPermissionManager(InstallPermissionManager installPermissionManager) {
        this.installPermissionManager = installPermissionManager;
    }

    public void setTechSupportManager(TechSupportManager techSupportManager) {
        this.techSupportManager = techSupportManager;
    }

    public void setSdsPermissionManager(SDSPermissionManager sdsPermissionManager) {
        this.sdsPermissionManager = sdsPermissionManager;
    }

    public void setSoidCodeManager(SOIDCodeManager soidCodeManager) {
        this.soidCodeManager = soidCodeManager;
    }

    public void setManualPermissionsManager(ManualPermissionsManager manualPermissionsManager) {
        this.manualPermissionsManager = manualPermissionsManager;
    }

    public RemoteTechServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public void saveManualPermissions(List<TechManualPermissions> techManualPermissions) throws RemoteException {
        manualPermissionsManager.save(techManualPermissions);
    }

    @Override
    public void deleteManualPermissions(Object... manualPermissions) throws RemoteException {
        manualPermissionsManager.delete(manualPermissions);
    }

    @Override
    public void saveSOIDCode(List<SOIDCode> soidCodes) throws RemoteException {
        soidCodeManager.save(soidCodes);
    }

    @Override
    public void deleteSOIDCode(Object... soidCodes) throws RemoteException {
        soidCodeManager.delete(soidCodes);
    }

    @Override
    public void saveSDSPermission(List<TechSDSPermission> techSdsPermissions) throws RemoteException {
        sdsPermissionManager.save(techSdsPermissions);
    }

    @Override
    public void deleteSDSPermission(Object... sdsPermissions) throws RemoteException {
        sdsPermissionManager.delete(sdsPermissions);
    }

    @Override
    public void saveTechSupport(List<TechSupport> techSupportList) throws RemoteException {
        techSupportManager.save(techSupportList);
    }

    @Override
    public void deleteTechSupport(Object... techSupport) throws RemoteException {
        techSupportManager.delete(techSupport);
    }

    @Override
    public void saveInstallPermission(List<TechInstallPermission> techInstallPermissionList) throws RemoteException {
        installPermissionManager.save(techInstallPermissionList);
    }

    @Override
    public void deleteInstallPermission(Object... installPermission) throws RemoteException {
        installPermissionManager.delete(installPermission);
    }
}
