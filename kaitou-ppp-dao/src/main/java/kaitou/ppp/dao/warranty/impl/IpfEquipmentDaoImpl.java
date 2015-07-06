package kaitou.ppp.dao.warranty.impl;

import kaitou.ppp.dao.BaseDao4InDoubt;
import kaitou.ppp.dao.warranty.IpfEquipmentDao;
import kaitou.ppp.domain.warranty.IpfEquipment;

/**
 * iPF设备DAO实现.
 * User: 赵立伟
 * Date: 2015/6/28
 * Time: 11:45
 */
public class IpfEquipmentDaoImpl extends BaseDao4InDoubt<IpfEquipment> implements IpfEquipmentDao {
    @Override
    public Class getDomainClass() {
        return IpfEquipment.class;
    }
}
