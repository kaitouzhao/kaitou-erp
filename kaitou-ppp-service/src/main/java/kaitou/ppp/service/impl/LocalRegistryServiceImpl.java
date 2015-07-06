package kaitou.ppp.service.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.domain.system.IPRegistry;
import kaitou.ppp.manager.system.RemoteRegistryManager;
import kaitou.ppp.service.LocalRegistryService;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 本地注册表业务层实现.
 * User: 赵立伟
 * Date: 2015/3/31
 * Time: 15:52
 */
public class LocalRegistryServiceImpl implements LocalRegistryService {

    private RemoteRegistryManager remoteRegistryManager;

    public void setRemoteRegistryManager(RemoteRegistryManager remoteRegistryManager) {
        this.remoteRegistryManager = remoteRegistryManager;
    }

    @Override
    public void updateRegistryIps(List<String> ipList) {
        if (CollectionUtil.isEmpty(ipList)) {
            return;
        }
        for (String ip : ipList) {
            remoteRegistryManager.register(ip);
        }
    }

    @Override
    public List<String> queryRegistryIps() {
        return remoteRegistryManager.queryRegistryIps();
    }

    @Override
    public List<IPRegistry> queryIPRegistry() {
        List<IPRegistry> ipRegistryList = new ArrayList<IPRegistry>();
        List<String> ipList = queryRegistryIps();
        if (CollectionUtil.isNotEmpty(ipList)) {
            for (String ip : ipList) {
                IPRegistry ipRegistry = new IPRegistry();
                ipRegistry.setIp(ip);
                ipRegistryList.add(ipRegistry);
            }
        }
        return ipRegistryList;
    }

    @Override
    public void saveOrUpdateIPRegistry(IPRegistry... ipRegistry) {
        if (CollectionUtil.isEmpty(ipRegistry)) {
            return;
        }
        for (IPRegistry ip : ipRegistry) {
            if (StringUtils.isEmpty(ip.getIp())) {
                continue;
            }
            remoteRegistryManager.register(ip.getIp());
        }
    }

    @Override
    public void deleteIPRegistry(Object... ipRegistries) {
        if (CollectionUtil.isEmpty(ipRegistries)) {
            return;
        }
        List<String> ipList = queryRegistryIps();
        List<String> newIPList = new ArrayList<String>();
        for (String ip : ipList) {
            boolean isDeleted = false;
            for (Object item : ipRegistries) {
                IPRegistry deletedIpRegistry = (IPRegistry) item;
                if (ip.equals(deletedIpRegistry.getIp())) {
                    isDeleted = true;
                    break;
                }
            }
            if (!isDeleted) {
                newIPList.add(ip);
            }
        }
        remoteRegistryManager.clear();
        updateRegistryIps(newIPList);
    }
}
