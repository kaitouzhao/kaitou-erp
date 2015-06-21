package kaitou.ppp.common.utils;

import org.apache.commons.lang.StringUtils;

import java.text.DecimalFormat;

/**
 * 数字工具类.
 * User: 赵立伟
 * Date: 2015/6/16
 * Time: 11:25
 */
public abstract class NumberUtil {
    /**
     * 转换成整型
     *
     * @param str 字符串
     * @return 整型
     */
    public static Integer toInt(String str) {
        if (StringUtils.isEmpty(str)) {
            return 0;
        }
        try {
            return Integer.valueOf(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * 金额输出成字符串
     *
     * @param money 金额
     * @return 字符串
     */
    public static String money2Str(double money) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        return decimalFormat.format(money);
    }
}
