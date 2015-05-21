package kaitou.ppp.dao.tech.impl;

import kaitou.ppp.dao.BaseDao;
import kaitou.ppp.dao.tech.SOIDCodeDao;
import kaitou.ppp.domain.tech.SOIDCode;

import java.util.List;

/**
 * SOID识别码DAO实现.
 * User: 赵立伟
 * Date: 2015/5/19
 * Time: 11:23
 */
public class SOIDCodeDaoImpl extends BaseDao<SOIDCode> implements SOIDCodeDao {
    @Override
    public Class<SOIDCode> getDomainClass() {
        return SOIDCode.class;
    }

    @Override
    public List<SOIDCode> query() {
        return super.query();
    }
}
