package kaitou.ppp.common.utils;

import org.junit.Test;

import static kaitou.ppp.common.utils.NumberUtil.toInt;
import static org.junit.Assert.assertEquals;

/**
 * 单元测试.
 * User: 赵立伟
 * Date: 2015/6/16
 * Time: 11:30
 */
public class NumberUtilTest {
    @Test
    public void testToInt() {
        assertEquals(Integer.valueOf(1), toInt("1"));
        assertEquals(Integer.valueOf(0), toInt("0"));
        assertEquals(Integer.valueOf(0), toInt(""));
        assertEquals(Integer.valueOf(0), toInt("a1"));
    }
}
