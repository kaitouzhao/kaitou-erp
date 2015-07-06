package kaitou.ppp.rmi.service;

import kaitou.ppp.domain.basic.Models;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * 远程基础数据服务.
 * User: 赵立伟
 * Date: 2015/6/22
 * Time: 19:19
 */
public interface RemoteBasicService extends Remote {

    /**
     * 保存保修卡生成记录
     *
     * @param modelsList 记录列表
     */
    public void saveModels(List<Models> modelsList) throws RemoteException;

    /**
     * 删除保修卡生成记录
     *
     * @param models 记录
     */
    public void deleteModels(Object... models) throws RemoteException;
}
