package kaitou.ppp.common.utils;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 * 日期时间工具类.
 * User: 赵立伟
 * Date: 2015/6/15
 * Time: 17:53
 */
public abstract class DateTimeUtil {
    /**
     * 支持的日期格式
     */
    private static enum DatePattern {
        DATE_PATTERN1("yyyy/MM/dd"), DATE_PATTERN2("yyyy-MM-dd"), DATE_PATTERN3("yyyy年MM月dd日");
        private String pattern;

        DatePattern(String pattern) {
            this.pattern = pattern;
        }
    }

    /**
     * 转换成日期
     *
     * @param str 字符串
     * @return 日期
     */
    public static DateTime toDate(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        for (DatePattern datePattern : DatePattern.values()) {
            try {
                return DateTime.parse(str, DateTimeFormat.forPattern(datePattern.pattern));
            } catch (IllegalArgumentException e) {
                continue;
            }
        }
        return null;
    }

    /**
     * 判断日期是否同一天
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 年月日相同即为同一天
     */
    public static boolean isSameDate(DateTime date1, DateTime date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        String date1ToStr = date1.toString(DatePattern.DATE_PATTERN1.pattern);
        String date2ToStr = date2.toString(DatePattern.DATE_PATTERN1.pattern);
        return date1ToStr.equals(date2ToStr);
    }
}
