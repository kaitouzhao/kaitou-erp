package kaitou.ppp.dao.shop.impl;

import kaitou.ppp.dao.BaseDao;
import kaitou.ppp.dao.shop.PartsLibraryDao;
import kaitou.ppp.domain.shop.PartsLibrary;

/**
 * 零件备库管理DAO实现.
 * User: 赵立伟
 * Date: 2015/5/17
 * Time: 22:24
 */
public class PartsLibraryDaoImpl extends BaseDao<PartsLibrary> implements PartsLibraryDao {
    @Override
    public Class<PartsLibrary> getDomainClass() {
        return PartsLibrary.class;
    }
}
