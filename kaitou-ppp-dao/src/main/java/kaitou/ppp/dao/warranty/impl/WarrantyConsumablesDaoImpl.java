package kaitou.ppp.dao.warranty.impl;

import kaitou.ppp.dao.BaseDao;
import kaitou.ppp.dao.warranty.WarrantyConsumablesDao;
import kaitou.ppp.domain.warranty.WarrantyConsumables;

/**
 * 耗材保修DAO实现.
 * User: 赵立伟
 * Date: 2015/5/18
 * Time: 18:00
 */
public class WarrantyConsumablesDaoImpl extends BaseDao<WarrantyConsumables> implements WarrantyConsumablesDao {
    @Override
    public Class<WarrantyConsumables> getDomainClass() {
        return WarrantyConsumables.class;
    }
}
