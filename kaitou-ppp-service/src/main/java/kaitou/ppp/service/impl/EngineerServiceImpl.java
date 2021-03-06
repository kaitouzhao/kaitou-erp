package kaitou.ppp.service.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.domain.count.CountByProductLine;
import kaitou.ppp.domain.count.CountByShop;
import kaitou.ppp.domain.engineer.Engineer;
import kaitou.ppp.domain.engineer.EngineerTraining;
import kaitou.ppp.domain.shop.CachedShop;
import kaitou.ppp.domain.shop.CachedShopDetail;
import kaitou.ppp.domain.system.SysCode;
import kaitou.ppp.manager.engineer.EngineerManager;
import kaitou.ppp.manager.engineer.EngineerTrainingManager;
import kaitou.ppp.manager.listener.EngineerUpdateListener;
import kaitou.ppp.manager.shop.ShopManager;
import kaitou.ppp.rmi.service.RemoteEngineerService;
import kaitou.ppp.service.BaseExcelService;
import kaitou.ppp.service.EngineerService;

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static com.womai.bsp.tool.utils.BeanCopyUtil.copyBean;
import static kaitou.ppp.service.ServiceInvokeManager.*;

/**
 * 工程师业务处理层实现.
 * User: 赵立伟
 * Date: 2015/1/17
 * Time: 23:16
 */
public class EngineerServiceImpl extends BaseExcelService implements EngineerService {

    private ShopManager shopManager;
    private EngineerManager engineerManager;
    private EngineerTrainingManager engineerTrainingManager;
    private List<EngineerUpdateListener> engineerUpdateListeners;

    public void setEngineerUpdateListeners(List<EngineerUpdateListener> engineerUpdateListeners) {
        this.engineerUpdateListeners = engineerUpdateListeners;
    }

    public void setShopManager(ShopManager shopManager) {
        this.shopManager = shopManager;
    }

    public void setEngineerTrainingManager(EngineerTrainingManager engineerTrainingManager) {
        this.engineerTrainingManager = engineerTrainingManager;
    }

    public void setEngineerManager(EngineerManager engineerManager) {
        this.engineerManager = engineerManager;
    }

    @Override
    public void importEngineers(File srcFile) {
        saveOrUpdateEngineer(CollectionUtil.toArray(readFromExcel(srcFile, Engineer.class), Engineer.class));
    }

    @Override
    public void exportEngineers(File targetFile) {
        export2Excel(engineerManager.query(), targetFile, Engineer.class);
    }

    @Override
    public void importEngineerTrainings(File srcFile) {
        saveOrUpdateEngineerTraining(CollectionUtil.toArray(readFromExcel(srcFile, EngineerTraining.class), EngineerTraining.class));
    }

    @Override
    public void exportTrainings(File targetFile) {
        export2Excel(engineerTrainingManager.query(), targetFile, EngineerTraining.class);
    }

    @Override
    public void deleteEngineers(final Object... engineers) {
        logOperation("已删除工程师个数：" + engineerManager.delete(engineers));
        asynchronousRun(new InvokeRunnable() {
            @Override
            public void run() throws RemoteException {
                List<RemoteEngineerService> remoteEngineerServices = queryRemoteService(RemoteEngineerService.class);
                logOperation("通知已注册的远程服务删除工程师基本信息");
                for (RemoteEngineerService remoteEngineerService : remoteEngineerServices) {
                    remoteEngineerService.deleteEngineer(engineers);
                }
            }
        });
    }

    @Override
    public void deleteEngineerTrainings(final Object... trainings) {
        logOperation("已删除工程师发展信息个数：" + engineerTrainingManager.delete(trainings));
        asynchronousRun(new InvokeRunnable() {
            @Override
            public void run() throws RemoteException {
                List<RemoteEngineerService> remoteEngineerServices = queryRemoteService(RemoteEngineerService.class);
                logOperation("通知已注册的远程服务删除工程师发展信息");
                for (RemoteEngineerService remoteEngineerService : remoteEngineerServices) {
                    remoteEngineerService.deleteEngineerTrainings(trainings);
                }
            }
        });
    }

    @Override
    public void countEngineersByProductLine(File targetFile) {
        List<Engineer> allEngineers = engineerManager.query();
        List<CountByProductLine> result = new ArrayList<CountByProductLine>();
        CountByProductLine countByProductLine;
        String productLine;
        int index;
        for (Engineer engineer : allEngineers) {
            if (!SysCode.EngineerStatus.ON.getValue().equals(engineer.getStatus())) {
                continue;
            }
            productLine = engineer.getProductLine();
            index = result.indexOf(new CountByProductLine().setProductLine(productLine));
            if (index < 0) {
                countByProductLine = new CountByProductLine().setProductLine(productLine);
                result.add(countByProductLine);
            } else {
                countByProductLine = result.get(index);
            }
            countByProductLine.setCount(countByProductLine.getCount() + 1);
        }
        export2Excel(result, "产品线在职工程师统计", new String[]{"产品线", "人数"}, new String[]{"productLine", "count"}, targetFile);
    }

    @Override
    public void countEngineersByShop(File targetFile) {
        List<Engineer> allEngineers = engineerManager.query();
        List<CountByShop> result = new ArrayList<CountByShop>();
        CountByShop countByShop;
        String shopId;
        int index;
        for (Engineer engineer : allEngineers) {
            if (!SysCode.EngineerStatus.ON.getValue().equals(engineer.getStatus())) {
                continue;
            }
            shopId = engineer.getShopId();
            index = result.indexOf(new CountByShop().setShopId(shopId));
            if (index < 0) {
                countByShop = new CountByShop().setShopId(shopId).setShopName(engineer.getShopName());
                result.add(countByShop);
            } else {
                countByShop = result.get(index);
            }
            countByShop.setCount(countByShop.getCount() + 1);
        }
        export2Excel(result, "认定店在职工程师统计", new String[]{"认定店编码", "认定店名", "人数"}, new String[]{"shopId", "shopName", "count"}, targetFile);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Engineer> queryAllEngineers() {
        return engineerManager.query();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<EngineerTraining> queryAllTrainings() {
        return engineerTrainingManager.query();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void saveOrUpdateEngineer(final Engineer... engineers) {
        int successCount = 0;
        if (CollectionUtil.isEmpty(engineers)) {
            logOperation("成功导入/更新工程师数：" + successCount);
            return;
        }
        final List<Engineer> engineerList = new ArrayList<Engineer>();
        for (Engineer engineer : engineers) {
            CachedShop cachedShop = shopManager.getCachedShop(engineer.getShopId());
            engineer.setSaleRegion(cachedShop.getSaleRegion());
            CachedShopDetail shopDetail = shopManager.getCachedShopDetail(engineer.getShopId(), engineer.getProductLine());
            engineer.setNumberOfYear(shopDetail.getNumberOfYear());
            engineer.setShopLevel(shopDetail.getLevel());
            engineerList.add(engineer);
        }
        successCount = engineerManager.save(engineerList);
        logOperation("成功导入/更新工程师数：" + successCount);
        if (successCount <= 0) {
            return;
        }
        asynchronousRun(new InvokeRunnable() {
            @Override
            public void run() throws RemoteException {
                for (EngineerUpdateListener listener : engineerUpdateListeners) {
                    listener.updateEngineerEvent(CollectionUtil.toArray(engineerList, Engineer.class));
                }
            }
        });
        asynchronousRun(new InvokeRunnable() {
            @Override
            public void run() throws RemoteException {
                List<RemoteEngineerService> remoteEngineerServices = queryRemoteService(RemoteEngineerService.class);
                logOperation("通知已注册的远程服务更新工程师基本信息");
                for (RemoteEngineerService remoteEngineerService : remoteEngineerServices) {
                    remoteEngineerService.saveEngineers(engineerList);
                }
            }
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public void saveOrUpdateEngineerTraining(final EngineerTraining... trainings) {
        int successCount = 0;
        if (CollectionUtil.isEmpty(trainings)) {
            logOperation("成功导入/更新培训信息数：" + successCount);
            return;
        }
        List<Engineer> allEngineers = engineerManager.query();
        final List<EngineerTraining> trainingList = new ArrayList<EngineerTraining>();
        for (EngineerTraining training : trainings) {
            for (Engineer engineer : allEngineers) {
                if (!training.getId().equals(engineer.getId())) {
                    if (!training.getName().equals(engineer.getName()) || !training.getShopName().equals(engineer.getShopName())) {
                        continue;
                    }
                }
                copyBean(engineer, training);
            }
            trainingList.add(training);
        }
        successCount = engineerTrainingManager.save(trainingList);
        logOperation("成功导入/更新工程师培训信息数：" + successCount);
        asynchronousRun(new InvokeRunnable() {
            @Override
            public void run() throws RemoteException {
                List<RemoteEngineerService> remoteEngineerServices = queryRemoteService(RemoteEngineerService.class);
                logOperation("通知已注册的远程服务更新工程师发展信息");
                for (RemoteEngineerService remoteEngineerService : remoteEngineerServices) {
                    remoteEngineerService.saveEngineerTrainings(trainingList);
                }
            }
        });
    }
}
