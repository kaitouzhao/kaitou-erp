package kaitou.ppp.dao.shop;

import kaitou.ppp.domain.shop.ShopContract;

import java.util.List;

/**
 * 认定店合同信息DAO.
 * User: 赵立伟
 * Date: 2015/5/17
 * Time: 21:20
 */
public interface ShopContractDao {

    /**
     * 添加/更新
     *
     * @param shopContracts 合同集合，可以是单个，也可是多个
     * @return 成功记录数
     */
    public int save(Object... shopContracts);

    /**
     * 查询认定店合同信息
     *
     * @return 认定店合同信息
     */
    public List<ShopContract> queryAll();

    /**
     * 删除
     *
     * @param shopContracts 待删除集合。支持一个或多个
     * @return 成功执行记录数
     */
    public int delete(Object... shopContracts);
}
