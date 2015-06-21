package kaitou.ppp.service.rmi;

import kaitou.ppp.domain.ts.*;
import kaitou.ppp.manager.ts.*;
import kaitou.ppp.rmi.service.RemoteTSService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * 远程TS管理服务实现.
 * User: 赵立伟
 * Date: 2015/5/20
 * Time: 11:18
 */
public class RemoteTSServiceImpl extends UnicastRemoteObject implements RemoteTSService {

    private TsDongleManager tsDongleManager;
    private TSTrainingManager tsTrainingManager;
    private ToolRecipientsManager toolRecipientsManager;
    private TSSDSPermissionManager tssdsPermissionManager;
    private OldMachineRenewManager oldMachineRenewManager;
    private NewMachineClaimManager newMachineClaimManager;
    private ComponentBorrowingManager componentBorrowingManager;
    private TSInstallPermissionManager tsInstallPermissionManager;
    private TSManualPermissionsManager tsManualPermissionsManager;

    public void setTsDongleManager(TsDongleManager tsDongleManager) {
        this.tsDongleManager = tsDongleManager;
    }

    public void setToolRecipientsManager(ToolRecipientsManager toolRecipientsManager) {
        this.toolRecipientsManager = toolRecipientsManager;
    }

    public void setComponentBorrowingManager(ComponentBorrowingManager componentBorrowingManager) {
        this.componentBorrowingManager = componentBorrowingManager;
    }

    public void setOldMachineRenewManager(OldMachineRenewManager oldMachineRenewManager) {
        this.oldMachineRenewManager = oldMachineRenewManager;
    }

    public void setNewMachineClaimManager(NewMachineClaimManager newMachineClaimManager) {
        this.newMachineClaimManager = newMachineClaimManager;
    }

    public void setTsSDSPermissionManager(TSSDSPermissionManager tsSDSPermissionManager) {
        this.tssdsPermissionManager = tsSDSPermissionManager;
    }

    public void setTsInstallPermissionManager(TSInstallPermissionManager tsInstallPermissionManager) {
        this.tsInstallPermissionManager = tsInstallPermissionManager;
    }

    public void setTsManualPermissionsManager(TSManualPermissionsManager tsManualPermissionsManager) {
        this.tsManualPermissionsManager = tsManualPermissionsManager;
    }

    public void setTsTrainingManager(TSTrainingManager tsTrainingManager) {
        this.tsTrainingManager = tsTrainingManager;
    }

    public RemoteTSServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public void saveTSTraining(List<TSTraining> tsTrainings) throws RemoteException {
        tsTrainingManager.save(tsTrainings);
    }

    @Override
    public void deleteTSTraining(Object... tsTraining) throws RemoteException {
        tsTrainingManager.delete(tsTraining);
    }

    @Override
    public void saveTSManualPermission(List<TSManualPermissions> tsManualPermissions) throws RemoteException {
        tsManualPermissionsManager.save(tsManualPermissions);
    }

    @Override
    public void deleteTSManualPermission(Object... tsManualPermission) throws RemoteException {
        tsManualPermissionsManager.delete(tsManualPermission);
    }

    @Override
    public void saveTSSDSPermission(List<TSSDSPermission> tsSDSPermissions) throws RemoteException {
        tssdsPermissionManager.save(tsSDSPermissions);
    }

    @Override
    public void deleteTSSDSPermission(Object... tsSDSPermission) throws RemoteException {
        tssdsPermissionManager.delete(tsSDSPermission);
    }

    @Override
    public void saveTSInstallPermission(List<TSInstallPermission> tsInstallPermissions) throws RemoteException {
        tsInstallPermissionManager.save(tsInstallPermissions);
    }

    @Override
    public void deleteTSInstallPermission(Object... tsInstallPermissions) throws RemoteException {
        tsInstallPermissionManager.delete(tsInstallPermissions);
    }

    @Override
    public void saveOldMachineRenew(List<OldMachineRenew> oldMachineRenewList) throws RemoteException {
        oldMachineRenewManager.save(oldMachineRenewList);
    }

    @Override
    public void deleteOldMachineRenew(Object... oldMachineRenew) throws RemoteException {
        oldMachineRenewManager.delete(oldMachineRenew);
    }

    @Override
    public void saveNewMachineClaim(List<NewMachineClaim> newMachineClaims) throws RemoteException {
        newMachineClaimManager.save(newMachineClaims);
    }

    @Override
    public void deleteNewMachineClaim(Object... newMachineClaim) throws RemoteException {
        newMachineClaimManager.delete(newMachineClaim);
    }

    @Override
    public void saveToolRecipients(List<ToolRecipients> toolRecipients) throws RemoteException {
        toolRecipientsManager.save(toolRecipients);
    }

    @Override
    public void deleteToolRecipients(Object... toolRecipients) throws RemoteException {
        toolRecipientsManager.delete(toolRecipients);
    }

    @Override
    public void saveComponentBorrowing(List<ComponentBorrowing> componentBorrowing) throws RemoteException {
        componentBorrowingManager.save(componentBorrowing);
    }

    @Override
    public void deleteComponentBorrowing(Object... componentBorrowing) throws RemoteException {
        componentBorrowingManager.delete(componentBorrowing);
    }

    @Override
    public void saveTSDongle(List<TSDongle> tsDongles) throws RemoteException {
        tsDongleManager.save(tsDongles);
    }

    @Override
    public void deleteTSDongle(Object... tsDongles) throws RemoteException {
        tsDongleManager.delete(tsDongles);
    }
}
