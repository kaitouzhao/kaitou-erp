package kaitou.ppp.service.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.dao.support.Condition;
import kaitou.ppp.dao.support.Pager;
import kaitou.ppp.domain.basic.Models;
import kaitou.ppp.manager.basic.ModelsManager;
import kaitou.ppp.rmi.service.RemoteBasicService;
import kaitou.ppp.service.BaseExcelService;
import kaitou.ppp.service.BasicService;
import kaitou.ppp.service.ServiceInvokeManager;

import java.io.File;
import java.rmi.RemoteException;
import java.util.List;

import static kaitou.ppp.service.ServiceInvokeManager.asynchronousRun;
import static kaitou.ppp.service.ServiceInvokeManager.queryRemoteService;

/**
 * 基础数据服务实现.
 * User: 赵立伟
 * Date: 2015/6/22
 * Time: 19:26
 */
public class BasicServiceImpl extends BaseExcelService implements BasicService {

    private ModelsManager modelsManager;

    public void setModelsManager(ModelsManager modelsManager) {
        this.modelsManager = modelsManager;
    }

    @Override
    public void importBasicModels(File srcFile) {
        saveOrUpdateBasicModels(CollectionUtil.toArray(readFromExcel(srcFile, Models.class), Models.class));
    }

    @Override
    public void exportBasicModels(File targetFile) {
        export2Excel(modelsManager.queryAll(), targetFile, Models.class);
    }

    @Override
    public List<Models> queryBasicModels() {
        return modelsManager.queryAll();
    }

    @Override
    public void saveOrUpdateBasicModels(Models... models) {
        final List<Models> modelsList = CollectionUtil.newList(models);
        logOperation("成功导入/更新机型基础数：" + modelsManager.save(modelsList));
        asynchronousRun(new ServiceInvokeManager.InvokeRunnable() {
            @Override
            public void run() throws RemoteException {
                List<RemoteBasicService> remoteBasicServices = queryRemoteService(RemoteBasicService.class);
                logOperation("通知已注册的远程服务更新机型基础数据");
                for (RemoteBasicService remoteBasicService : remoteBasicServices) {
                    remoteBasicService.saveModels(modelsList);
                }
            }
        });
    }

    @Override
    public void deleteBasicModels(final Object... models) {
        logOperation("成功删除机型基础数据个数：" + modelsManager.delete(models));
        asynchronousRun(new ServiceInvokeManager.InvokeRunnable() {
            @Override
            public void run() throws RemoteException {
                List<RemoteBasicService> remoteBasicServices = queryRemoteService(RemoteBasicService.class);
                logOperation("通知已注册的远程服务更新删除机型基础数据");
                for (RemoteBasicService remoteBasicService : remoteBasicServices) {
                    remoteBasicService.deleteModels(models);
                }
            }
        });
    }

    @Override
    public Pager<Models> queryBasicModels(int currentPage, List<Condition> conditions) {
        return modelsManager.queryPager(currentPage, conditions);
    }

    @Override
    public List<Models> queryBasicModels(List<Condition> conditions) {
        return modelsManager.queryAll(conditions);
    }

    @Override
    public void cacheModels() {
        modelsManager.cacheModels();
        logOperation("已缓存机型分类基础数据");
    }
}
