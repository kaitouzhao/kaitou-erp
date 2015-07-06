package kaitou.ppp.service.rmi;

import kaitou.ppp.domain.basic.Models;
import kaitou.ppp.manager.basic.ModelsManager;
import kaitou.ppp.rmi.service.RemoteBasicService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * 远程基础数据服务实现.
 * User: 赵立伟
 * Date: 2015/6/22
 * Time: 19:21
 */
public class RemoteBasicServiceImpl extends UnicastRemoteObject implements RemoteBasicService {

    private ModelsManager modelsManager;

    public void setModelsManager(ModelsManager modelsManager) {
        this.modelsManager = modelsManager;
    }

    public RemoteBasicServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public void saveModels(List<Models> modelsList) throws RemoteException {
        modelsManager.save(modelsList);
    }

    @Override
    public void deleteModels(Object... models) throws RemoteException {
        modelsManager.delete(models);
    }
}
