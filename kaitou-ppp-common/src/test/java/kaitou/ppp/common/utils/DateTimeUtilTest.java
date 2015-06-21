package kaitou.ppp.common.utils;

import org.joda.time.DateTime;
import org.junit.Test;

import static kaitou.ppp.common.utils.DateTimeUtil.isSameDate;
import static kaitou.ppp.common.utils.DateTimeUtil.toDate;
import static org.junit.Assert.*;

/**
 * 单元测试.
 * User: 赵立伟
 * Date: 2015/6/15
 * Time: 18:01
 */
public class DateTimeUtilTest {
    @Test
    public void testToDate() {
        assertNotNull(toDate("2015/1/1"));
        assertNotNull(toDate("2015-1-1"));
        assertNotNull(toDate("2015年1月1日"));
        assertNull(toDate(""));
        assertNull(toDate("dasdas"));
    }

    @Test
    public void testIsSameDate() {
        DateTime date1 = toDate("2015/01/01");
        DateTime date2 = toDate("2015/1/1");
        assertTrue(isSameDate(date1, date2));
    }
}
