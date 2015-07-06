package kaitou.ppp.rmi.service;

import kaitou.ppp.domain.warranty.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * 远程保修管理服务.
 * User: 赵立伟
 * Date: 2015/5/6
 * Time: 15:08
 */
public interface RemoteWarrantyService extends Remote {
    /**
     * 保存iPF设备记录
     *
     * @param equipments iPF设备记录列表
     */
    public void saveIpfEquipment(List<IpfEquipment> equipments) throws RemoteException;

    /**
     * 删除iPF设备记录
     *
     * @param equipments iPF设备
     */
    public void deleteIpfEquipment(Object... equipments) throws RemoteException;

    /**
     * 保存保修费记录
     *
     * @param warrantyFees 保修费记录列表
     */
    public void saveWarrantyFee(List<WarrantyFee> warrantyFees) throws RemoteException;

    /**
     * 删除保修费记录
     *
     * @param warrantyFee 保修费
     */
    public void deleteWarrantyFee(Object... warrantyFee) throws RemoteException;

    /**
     * 保存保修及索赔零件
     *
     * @param warrantyParts 零件列表
     */
    public void saveWarrantyParts(List<WarrantyParts> warrantyParts) throws RemoteException;

    /**
     * 删除保修及索赔零件
     *
     * @param warrantyParts 零件
     */
    public void deleteWarrantyParts(Object... warrantyParts) throws RemoteException;

    /**
     * 保存打印头保修
     *
     * @param warrantyPrint 打印头保修列表
     */
    public void saveWarrantyPrint(List<WarrantyPrint> warrantyPrint) throws RemoteException;

    /**
     * 删除打印头保修
     *
     * @param warrantyPrint 打印头保修
     */
    public void deleteWarrantyPrint(Object... warrantyPrint) throws RemoteException;

    /**
     * 保存耗材保修
     *
     * @param warrantyConsumables 耗材保修列表
     */
    public void saveWarrantyConsumables(List<WarrantyConsumables> warrantyConsumables) throws RemoteException;

    /**
     * 删除打印头保修
     *
     * @param warrantyConsumables 耗材保修
     */
    public void deleteWarrantyConsumables(Object... warrantyConsumables) throws RemoteException;
}
