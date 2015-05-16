package kaitou.ppp.service.rmi;

import kaitou.ppp.domain.warranty.WarrantyFee;
import kaitou.ppp.domain.warranty.WarrantyParts;
import kaitou.ppp.manager.warranty.WarrantyFeeManager;
import kaitou.ppp.manager.warranty.WarrantyPartsManager;
import kaitou.ppp.rmi.service.RemoteWarrantyService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * 远程保修管理服务实现.
 * User: 赵立伟
 * Date: 2015/5/6
 * Time: 15:22
 */
public class RemoteWarrantyServiceImpl extends UnicastRemoteObject implements RemoteWarrantyService {

    private WarrantyFeeManager warrantyFeeManager;
    private WarrantyPartsManager warrantyPartsManager;

    public void setWarrantyPartsManager(WarrantyPartsManager warrantyPartsManager) {
        this.warrantyPartsManager = warrantyPartsManager;
    }

    public void setWarrantyFeeManager(WarrantyFeeManager warrantyFeeManager) {
        this.warrantyFeeManager = warrantyFeeManager;
    }

    public RemoteWarrantyServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public void saveWarrantyFee(List<WarrantyFee> warrantyFees) throws RemoteException {
        warrantyFeeManager.save(warrantyFees);
    }

    @Override
    public void deleteWarrantyFee(Object... warrantyFee) throws RemoteException {
        warrantyFeeManager.delete(warrantyFee);
    }

    @Override
    public void saveWarrantyParts(List<WarrantyParts> warrantyParts) throws RemoteException {
        warrantyPartsManager.save(warrantyParts);
    }

    @Override
    public void deleteWarrantyParts(Object... warrantyParts) throws RemoteException {
        warrantyPartsManager.delete(warrantyParts);
    }
}
