package kaitou.ppp.dao.warranty.impl;

import kaitou.ppp.dao.BaseDao4InDoubt;
import kaitou.ppp.dao.warranty.WarrantyFeeDao;
import kaitou.ppp.domain.warranty.WarrantyFee;

import java.util.List;

/**
 * 保修费DAO实现.
 * User: 赵立伟
 * Date: 2015/5/6
 * Time: 14:49
 */
public class WarrantyFeeDaoImpl extends BaseDao4InDoubt<WarrantyFee> implements WarrantyFeeDao {
    @Override
    public Class getDomainClass() {
        return WarrantyFee.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<WarrantyFee> query() {
        return super.query();
    }
}
