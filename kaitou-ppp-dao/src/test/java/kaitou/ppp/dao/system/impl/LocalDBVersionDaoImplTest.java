package kaitou.ppp.dao.system.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.dao.AbstractDaoTest;
import kaitou.ppp.dao.BaseDao;
import kaitou.ppp.dao.system.LocalDBVersionDao;
import kaitou.ppp.domain.system.DBVersion;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * 本地DB版本库DAO实现单元测试.
 * User: 赵立伟
 * Date: 2015/4/10
 * Time: 15:10
 */
public class LocalDBVersionDaoImplTest extends AbstractDaoTest {

    private LocalDBVersionDao localDBVersionDao;

    @Override
    public String getDbDir() {
        return "D:\\temp\\ppp\\test\\local_db_version";
    }

    @Override
    public void initManager() {
        localDBVersionDao = ctx.getBean(LocalDBVersionDao.class);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testUpgrade() throws Exception {
        String dbFileName1 = "dbFile1.kdb";
        String dbFileName2 = "dbFile2.kdb";
        List<String> toUpgradeDBList = CollectionUtil.newList(dbFileName1, dbFileName2);
        localDBVersionDao.upgrade(toUpgradeDBList);
        long dbLatestVersion11 = localDBVersionDao.getDBLatestVersion(dbFileName1);
        long dbLatestVersion21 = localDBVersionDao.getDBLatestVersion(dbFileName2);
        toUpgradeDBList = CollectionUtil.newList(dbFileName2);
        localDBVersionDao.upgrade(toUpgradeDBList);
        long dbLatestVersion12 = localDBVersionDao.getDBLatestVersion(dbFileName1);
        assertTrue(dbLatestVersion11 == dbLatestVersion12);
        long dbLatestVersion22 = localDBVersionDao.getDBLatestVersion(dbFileName2);
        assertTrue(dbLatestVersion22 > dbLatestVersion21);
        Map<String, Long> toUpgradeDBMap = new HashMap<String, Long>();
        toUpgradeDBMap.put(dbFileName1, 7l);
        localDBVersionDao.upgrade(toUpgradeDBMap);
        assertEquals(7l, localDBVersionDao.getDBLatestVersion(dbFileName1));
        assertTrue(dbLatestVersion22 == localDBVersionDao.getDBLatestVersion(dbFileName2));
    }

    @Test
    public void testGetDomainClass() {
        assertEquals(DBVersion.class, ((BaseDao<DBVersion>) localDBVersionDao).getDomainClass());
        assertEquals(DBVersion.class.getSimpleName(), ((BaseDao<DBVersion>) localDBVersionDao).getDomainClass().getSimpleName());
    }
}
