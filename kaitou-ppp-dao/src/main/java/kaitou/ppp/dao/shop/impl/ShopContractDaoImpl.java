package kaitou.ppp.dao.shop.impl;

import kaitou.ppp.dao.BaseDao;
import kaitou.ppp.dao.shop.ShopContractDao;
import kaitou.ppp.domain.shop.ShopContract;

/**
 * 认定店合同信息DAO实现.
 * User: 赵立伟
 * Date: 2015/5/17
 * Time: 21:22
 */
public class ShopContractDaoImpl extends BaseDao<ShopContract> implements ShopContractDao {
    @Override
    public Class<ShopContract> getDomainClass() {
        return ShopContract.class;
    }
}
