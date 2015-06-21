package kaitou.ppp.rmi.service;

import kaitou.ppp.domain.ts.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * 远程TS管理服务.
 * User: 赵立伟
 * Date: 2015/5/20
 * Time: 11:16
 */
public interface RemoteTSService extends Remote {
    /**
     * 保存TS培训记录
     *
     * @param tsTrainings TS培训记录列表
     */
    public void saveTSTraining(List<TSTraining> tsTrainings) throws RemoteException;

    /**
     * 删除TS培训记录
     *
     * @param tsTraining TS培训记录
     */
    public void deleteTSTraining(Object... tsTraining) throws RemoteException;

    /**
     * 保存TS手册权限
     *
     * @param tsManualPermissions TS手册权限列表
     */
    public void saveTSManualPermission(List<TSManualPermissions> tsManualPermissions) throws RemoteException;

    /**
     * 删除TS手册权限
     *
     * @param tsManualPermission TS手册权限
     */
    public void deleteTSManualPermission(Object... tsManualPermission) throws RemoteException;

    /**
     * 保存TS SDS权限
     *
     * @param tsSDSPermissions TS SDS权限列表
     */
    public void saveTSSDSPermission(List<TSSDSPermission> tsSDSPermissions) throws RemoteException;

    /**
     * 删除TS SDS权限
     *
     * @param tsSDSPermission TS SDS权限
     */
    public void deleteTSSDSPermission(Object... tsSDSPermission) throws RemoteException;

    /**
     * 保存TS装机权限
     *
     * @param tsInstallPermissions TS装机权限列表
     */
    public void saveTSInstallPermission(List<TSInstallPermission> tsInstallPermissions) throws RemoteException;

    /**
     * 删除TS装机权限
     *
     * @param tsInstallPermissions TS装机权限
     */
    public void deleteTSInstallPermission(Object... tsInstallPermissions) throws RemoteException;

    /**
     * 保存旧机翻新记录
     *
     * @param oldMachineRenewList 旧机翻新记录列表
     */
    public void saveOldMachineRenew(List<OldMachineRenew> oldMachineRenewList) throws RemoteException;

    /**
     * 删除旧机翻新记录
     *
     * @param oldMachineRenew 旧机翻新记录
     */
    public void deleteOldMachineRenew(Object... oldMachineRenew) throws RemoteException;

    /**
     * 保存新装机索赔记录
     *
     * @param newMachineClaims 新装机索赔记录
     */
    public void saveNewMachineClaim(List<NewMachineClaim> newMachineClaims) throws RemoteException;

    /**
     * 删除新装机索赔记录
     *
     * @param newMachineClaim 新装机索赔记录
     */
    public void deleteNewMachineClaim(Object... newMachineClaim) throws RemoteException;

    /**
     * 保存工具领用记录
     *
     * @param toolRecipients 工具领用记录
     */
    public void saveToolRecipients(List<ToolRecipients> toolRecipients) throws RemoteException;

    /**
     * 删除工具领用记录
     *
     * @param toolRecipients 工具领用记录
     */
    public void deleteToolRecipients(Object... toolRecipients) throws RemoteException;

    /**
     * 保存零件借用记录
     *
     * @param componentBorrowing 零件借用记录
     */
    public void saveComponentBorrowing(List<ComponentBorrowing> componentBorrowing) throws RemoteException;

    /**
     * 删除零件借用记录
     *
     * @param componentBorrowing 零件借用记录
     */
    public void deleteComponentBorrowing(Object... componentBorrowing) throws RemoteException;

    /**
     * 保存dongle记录
     *
     * @param tsDongles dongle记录列表
     */
    public void saveTSDongle(List<TSDongle> tsDongles) throws RemoteException;

    /**
     * 删除dongle记录
     *
     * @param tsDongles dongle记录
     */
    public void deleteTSDongle(Object... tsDongles) throws RemoteException;
}
