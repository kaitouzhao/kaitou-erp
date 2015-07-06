package kaitou.ppp.service;

import kaitou.ppp.domain.system.IPRegistry;

import java.util.List;

/**
 * 本地注册表业务层.
 * User: 赵立伟
 * Date: 2015/3/31
 * Time: 15:50
 */
public interface LocalRegistryService {

    /**
     * 更新注册表
     *
     * @param ipList IP列表
     */
    public void updateRegistryIps(List<String> ipList);

    /**
     * 获取已注册的IP列表
     *
     * @return IP列表
     */
    public List<String> queryRegistryIps();

    /**
     * 查询已注册的IP地址列表
     *
     * @return IP地址列表
     */
    public List<IPRegistry> queryIPRegistry();

    /**
     * 保存新的IP地址
     *
     * @param ipRegistry 注册IP
     */
    public void saveOrUpdateIPRegistry(IPRegistry... ipRegistry);

    /**
     * 删除IP地址
     *
     * @param ipRegistries 已注册的IP地址
     */
    public void deleteIPRegistry(Object... ipRegistries);
}
