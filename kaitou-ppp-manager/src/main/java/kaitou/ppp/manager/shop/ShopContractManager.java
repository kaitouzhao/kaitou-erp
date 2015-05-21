package kaitou.ppp.manager.shop;

import kaitou.ppp.domain.shop.ShopContract;

import java.util.List;

/**
 * 认定店合同信息DAO事务控制层.
 * User: 赵立伟
 * Date: 2015/5/17
 * Time: 21:25
 */
public interface ShopContractManager {

    /**
     * 保存合同信息
     *
     * @param shopContracts 合同信息列表
     * @return 成功执行个数
     */
    public int save(List<ShopContract> shopContracts);

    /**
     * 获取合同信息
     *
     * @return 合同信息列表
     */
    public List<ShopContract> query();

    /**
     * 删除
     *
     * @param shopContracts 待删除集合。支持一个或多个
     * @return 成功执行条数
     */
    public int delete(Object... shopContracts);
}
