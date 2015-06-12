package kaitou.ppp.service;

import kaitou.ppp.common.log.BaseLogManager;
import kaitou.ppp.manager.system.RemoteRegistryManager;
import kaitou.ppp.manager.system.SystemSettingsManager;
import kaitou.ppp.rmi.ServiceClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * 服务调用管理.
 * User: 赵立伟
 * Date: 2015/6/4
 * Time: 16:21
 */
public abstract class ServiceInvokeManager extends BaseLogManager {
    private static RemoteRegistryManager remoteRegistryManager;
    private static SystemSettingsManager systemSettingsManager;

    static {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                new String[]{
                        "applicationContext-service.xml"
                }
        );
        remoteRegistryManager = ctx.getBean(RemoteRegistryManager.class);
        systemSettingsManager = ctx.getBean(SystemSettingsManager.class);
    }

    /**
     * 获取指定远程服务列表
     *
     * @param remoteType 远程服务类型
     * @param <T>        远程服务类型
     * @return 服务列表
     */
    public static <T extends Remote> List<T> queryRemoteService(Class<T> remoteType) {
        return ServiceClient.queryServicesOfListener(remoteType, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
    }

    /**
     * 异步执行
     *
     * @param runnable 执行体
     */
    public static void asynchronousRun(final InvokeRunnable runnable) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    runnable.run();
                } catch (RemoteException e) {
                    logSystemEx(e);
                }
            }
        }).start();
    }

    /**
     * 执行体
     */
    public interface InvokeRunnable {
        /**
         * 执行逻辑
         */
        public abstract void run() throws RemoteException;
    }
}
