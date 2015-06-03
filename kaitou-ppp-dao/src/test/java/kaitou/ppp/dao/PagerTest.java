package kaitou.ppp.dao;

import kaitou.ppp.dao.support.Pager;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * 单元测试.
 * User: 赵立伟
 * Date: 2015/6/1
 * Time: 17:11
 */
public class PagerTest {

    @Test
    public void test() {
        Pager pager = new Pager(1, null);
        pager.setTotalSize(10);
        assertEquals(0, pager.beginIndex());
        assertEquals(10, pager.endIndex());
        assertEquals(1, pager.getTotalPage());
        pager.setTotalSize(25);
        assertEquals(0, pager.beginIndex());
        assertEquals(25, pager.endIndex());
        assertEquals(1, pager.getTotalPage());
        pager = new Pager(2, null);
        pager.setTotalSize(10);
        assertEquals(0, pager.beginIndex());
        assertEquals(10, pager.endIndex());
        assertEquals(1, pager.getTotalPage());
        pager.setTotalSize(100);
        assertEquals(25, pager.beginIndex());
        assertEquals(50, pager.endIndex());
        assertEquals(4, pager.getTotalPage());
        pager = new Pager(4, null);
        pager.setTotalSize(100);
        assertEquals(75, pager.beginIndex());
        assertEquals(100, pager.endIndex());
        assertEquals(4, pager.getTotalPage());
        pager = new Pager(4, null);
        pager.setTotalSize(0);
        assertEquals(0, pager.beginIndex());
        assertEquals(0, pager.endIndex());
        assertEquals(0, pager.getTotalPage());
    }
}
