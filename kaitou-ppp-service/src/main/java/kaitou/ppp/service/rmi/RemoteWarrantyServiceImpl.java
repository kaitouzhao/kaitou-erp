package kaitou.ppp.service.rmi;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.domain.warranty.*;
import kaitou.ppp.manager.listener.WarrantyUpdateListener;
import kaitou.ppp.manager.warranty.*;
import kaitou.ppp.rmi.service.RemoteWarrantyService;
import kaitou.ppp.service.ServiceInvokeManager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import static kaitou.ppp.service.ServiceInvokeManager.asynchronousRun;

/**
 * 远程保修管理服务实现.
 * User: 赵立伟
 * Date: 2015/5/6
 * Time: 15:22
 */
public class RemoteWarrantyServiceImpl extends UnicastRemoteObject implements RemoteWarrantyService {

    private WarrantyFeeManager warrantyFeeManager;
    private IpfEquipmentManager ipfEquipmentManager;
    private WarrantyPartsManager warrantyPartsManager;
    private WarrantyPrintManager warrantyPrintManager;
    private WarrantyConsumablesManager warrantyConsumablesManager;
    private List<WarrantyUpdateListener> warrantyUpdateListeners;

    public void setWarrantyUpdateListeners(List<WarrantyUpdateListener> warrantyUpdateListeners) {
        this.warrantyUpdateListeners = warrantyUpdateListeners;
    }

    public void setIpfEquipmentManager(IpfEquipmentManager ipfEquipmentManager) {
        this.ipfEquipmentManager = ipfEquipmentManager;
    }

    public void setWarrantyConsumablesManager(WarrantyConsumablesManager warrantyConsumablesManager) {
        this.warrantyConsumablesManager = warrantyConsumablesManager;
    }

    public void setWarrantyPrintManager(WarrantyPrintManager warrantyPrintManager) {
        this.warrantyPrintManager = warrantyPrintManager;
    }

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
    public void saveIpfEquipment(List<IpfEquipment> equipments) throws RemoteException {
        ipfEquipmentManager.save(equipments);
    }

    @Override
    public void deleteIpfEquipment(Object... equipments) throws RemoteException {
        ipfEquipmentManager.delete(equipments);
    }

    @Override
    public void saveWarrantyFee(final List<WarrantyFee> warrantyFees) throws RemoteException {
        warrantyFeeManager.save(warrantyFees);
        asynchronousRun(new ServiceInvokeManager.InvokeRunnable() {
            @Override
            public void run() throws RemoteException {
                for (WarrantyUpdateListener warrantyUpdateListener : warrantyUpdateListeners) {
                    warrantyUpdateListener.updateWarrantyFeeEvent(CollectionUtil.toArray(warrantyFees, WarrantyFee.class));
                }
            }
        });
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

    @Override
    public void saveWarrantyPrint(List<WarrantyPrint> warrantyPrint) throws RemoteException {
        warrantyPrintManager.save(warrantyPrint);
    }

    @Override
    public void deleteWarrantyPrint(Object... warrantyPrint) throws RemoteException {
        warrantyPrintManager.delete(warrantyPrint);
    }

    @Override
    public void saveWarrantyConsumables(List<WarrantyConsumables> warrantyConsumables) throws RemoteException {
        warrantyConsumablesManager.save(warrantyConsumables);
    }

    @Override
    public void deleteWarrantyConsumables(Object... warrantyConsumables) throws RemoteException {
        warrantyConsumablesManager.delete(warrantyConsumables);
    }
}
