package kaitou.ppp.dao.warranty.impl;

import kaitou.ppp.dao.BaseDao;
import kaitou.ppp.dao.warranty.WarrantyPrintDao;
import kaitou.ppp.domain.warranty.WarrantyPrint;

/**
 * 打印头保修DAO实现.
 * User: 赵立伟
 * Date: 2015/5/18
 * Time: 11:44
 */
public class WarrantyPrintDaoImpl extends BaseDao<WarrantyPrint> implements WarrantyPrintDao {
    @Override
    public Class<WarrantyPrint> getDomainClass() {
        return WarrantyPrint.class;
    }
}
