package kaitou.ppp.dao.warranty.impl;

import kaitou.ppp.dao.BaseDao;
import kaitou.ppp.dao.warranty.WarrantyPartsDao;
import kaitou.ppp.domain.warranty.WarrantyParts;

/**
 * 保修零件及索赔零件DAO实现.
 * User: 赵立伟
 * Date: 2015/5/10
 * Time: 16:05
 */
public class WarrantyPartsDaoImpl extends BaseDao<WarrantyParts> implements WarrantyPartsDao {
    @Override
    public Class<WarrantyParts> getDomainClass() {
        return WarrantyParts.class;
    }
}
