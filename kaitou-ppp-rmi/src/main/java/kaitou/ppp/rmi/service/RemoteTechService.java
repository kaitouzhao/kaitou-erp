package kaitou.ppp.rmi.service;

import kaitou.ppp.domain.tech.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * 远程技术管理服务.
 * User: 赵立伟
 * Date: 2015/5/19
 * Time: 10:29
 */
public interface RemoteTechService extends Remote {
    /**
     * 保存手册权限
     *
     * @param techManualPermissions 手册权限列表
     */
    public void saveManualPermissions(List<TechManualPermissions> techManualPermissions) throws RemoteException;

    /**
     * 删除手册权限
     *
     * @param manualPermissions 手册权限
     */
    public void deleteManualPermissions(Object... manualPermissions) throws RemoteException;

    /**
     * 保存SOID识别码
     *
     * @param soidCodes SOID识别码列表
     */
    public void saveSOIDCode(List<SOIDCode> soidCodes) throws RemoteException;

    /**
     * 删除SOID识别码
     *
     * @param soidCodes SOID识别码
     */
    public void deleteSOIDCode(Object... soidCodes) throws RemoteException;

    /**
     * 保存SDS权限管理
     *
     * @param techSdsPermissions SDS权限管理列表
     */
    public void saveSDSPermission(List<TechSDSPermission> techSdsPermissions) throws RemoteException;

    /**
     * 删除SDS权限管理
     *
     * @param sdsPermissions SDS权限管理
     */
    public void deleteSDSPermission(Object... sdsPermissions) throws RemoteException;

    /**
     * 保存技术支援管理
     *
     * @param techSupportList 技术支援列表
     */
    public void saveTechSupport(List<TechSupport> techSupportList) throws RemoteException;

    /**
     * 删除技术支援
     *
     * @param techSupport 技术支援
     */
    public void deleteTechSupport(Object... techSupport) throws RemoteException;

    /**
     * 保存装机权限
     *
     * @param techInstallPermissionList 装机权限列表
     */
    public void saveInstallPermission(List<TechInstallPermission> techInstallPermissionList) throws RemoteException;

    /**
     * 删除装机权限
     *
     * @param installPermission 装机权限
     */
    public void deleteInstallPermission(Object... installPermission) throws RemoteException;
}
