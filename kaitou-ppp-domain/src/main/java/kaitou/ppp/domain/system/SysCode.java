package kaitou.ppp.domain.system;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 * 系统变量.
 * User: 赵立伟
 * Date: 2015/1/17
 * Time: 10:45
 */
public abstract class SysCode {
    /**
     * 保修状态
     */
    public static enum WarrantyStatus {
        IN_WARRANTY("保修中"), OUT_WARRANTY("过保"), JUST_OUT_WARRANTY("刚刚过保，需要申请保养费");
        /**
         * 保修有效期（天）
         */
        private static final int IN_WARRANTY_DAYS = 364;
        /**
         * 刚刚过保期（月）
         */
        private static final int JUST_OUT_WARRANTY_MONTHS = 3;
        /**
         * 标准日期格式
         */
        public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy/MM/dd";
        /**
         * 可以支持的日期格式
         */
        private static final String DATE_FORMAT_YYYY_MM_DD1 = "yyyy年MM月dd日";
        private String value;

        WarrantyStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        /**
         * 根据装机日期获取保修状态
         * <ul>
         * <li>装机日期未超过364天，则为保修中</li>
         * <li>装机日期过保三个月内，则为刚刚过保，需要申请保养费</li>
         * <li>以上都不满足则为过保</li>
         * </ul>
         *
         * @param installedDateStr 装机日期。格式支持：yyyy/MM/dd
         * @return 保修状态
         */
        public static String getStatus(String installedDateStr) {
            if (StringUtils.isEmpty(installedDateStr)) {
                return "";
            }
            try {
                DateTime endDate = getEndDate(installedDateStr);
                if (endDate.isAfterNow()) {
                    return IN_WARRANTY.getValue();
                }
                endDate = endDate.plusMonths(JUST_OUT_WARRANTY_MONTHS);
                if (endDate.isAfterNow()) {
                    return JUST_OUT_WARRANTY.getValue();
                }
                return OUT_WARRANTY.getValue();
            } catch (Exception e) {
                return "";
            }
        }

        /**
         * 获取到期日期
         *
         * @param installedDateStr 装机日期
         * @return 到期日期
         */
        private static DateTime getEndDate(String installedDateStr) {
            if (StringUtils.isEmpty(installedDateStr)) {
                return null;
            }
            try {
                DateTime installedDate = DateTime.parse(installedDateStr, DateTimeFormat.forPattern(DATE_FORMAT_YYYY_MM_DD));
                return installedDate.plusDays(IN_WARRANTY_DAYS);
            } catch (Exception e) {
                return null;
            }
        }

        /**
         * 获取到期日期字符串
         *
         * @param installedDateStr 装机日期
         * @return 到期日期字符串
         */
        public static String getEndDateStr(String installedDateStr) {
            DateTime endDate = getEndDate(installedDateStr);
            return endDate != null ? endDate.toString(DATE_FORMAT_YYYY_MM_DD) : "";
        }

        /**
         * 校验装机日期
         *
         * @param installedDateStr 待校验字符串
         * @return 校验通过的字符串
         */
        public static String convert2Standard(String installedDateStr) {
            try {
                DateTime.parse(installedDateStr, DateTimeFormat.forPattern(DATE_FORMAT_YYYY_MM_DD));
                return installedDateStr;
            } catch (Exception e) {
                try {
                    DateTime.parse(installedDateStr, DateTimeFormat.forPattern(DATE_FORMAT_YYYY_MM_DD1));
                    return installedDateStr;
                } catch (Exception e1) {
                    return "";
                }
            }
        }
    }

    /**
     * 保修订单类型
     */
    public static enum WarrantyOrderType {
        PARTS("零件保修"), CLAIM("索赔");
        private String value;

        WarrantyOrderType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 认定店状态
     */
    public static enum ShopStatus {
        IN_THE_USE("认定中"), CANCEL("取消");
        private String value;

        ShopStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 认定店等级
     */
    public static enum ShopLevel {
        GOLD("金牌"), SILVER("银牌"), NORMAL("普通"), BACKUP("候补");
        private String value;

        ShopLevel(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 工程师状态
     */
    public static enum EngineerStatus {
        ON("在职"), OFF("离职");
        private String value;

        EngineerStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 销售区域
     */
    public static enum SaleRegion {
        NORTH_CHINA("华北", "HB"), WEST_CHINA("华西", "HX"), SOUTH_CHINA("华南", "HN"), EAST_CHINA("华东", "HD");
        private String value;
        private String code;

        SaleRegion(String value, String code) {
            this.value = value;
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        /**
         * 转换成名称
         *
         * @param currentCode 编码
         * @return 名称
         */
        public static String convert2Value(String currentCode) {
            for (SaleRegion region : SaleRegion.values()) {
                if (region.code.equals(currentCode)) {
                    return region.value;
                }
            }
            return currentCode;
        }

        /**
         * 转换成编码
         *
         * @param currentName 名称
         * @return 编码
         */
        public static String convert2Code(String currentName) {
            for (SaleRegion region : SaleRegion.values()) {
                if (region.value.equals(currentName)) {
                    return region.code;
                }
            }
            return currentName;
        }
    }

    /**
     * ACE等级
     */
    public static enum ACELevel {
        FIRST("一级"), SECOND("二级"), THIRD("三级");
        private String value;

        ACELevel(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 单元格数据类型
     */
    public static enum CellType {
        STRING(1), DATE(2), NUMERIC(3);

        private int value;

        CellType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public static final int CELL_TYPE_STRING = 1;
    public static final int CELL_TYPE_DATE = 2;
    public static final int CELL_TYPE_NUMERIC = 3;

    /**
     * 机型代码
     */
    public enum ModelCode {
        P("WFP", "TDS", "M"), T("WFP", "TDS", "M"), F("WFP", "TDS", "M"), A("WFP", "DGS", "M"), V("CPP", "DP", ""), C("WFP", "TDS", ""), I("CPP", "PGA", "");
        private String cardNoPref;
        private String models;
        private String readUnit;

        ModelCode(String cardNoPref, String models, String readUnit) {
            this.cardNoPref = cardNoPref;
            this.models = models;
            this.readUnit = readUnit;
        }

        public String getCardNoPref() {
            return cardNoPref;
        }

        public String getModels() {
            return models;
        }

        public String getReadUnit() {
            return readUnit;
        }

        public static ModelCode getCode(String value) {
            for (ModelCode code : ModelCode.values()) {
                if (code.name().equals(value)) {
                    return code;
                }
            }
            return P;
        }
    }

    /**
     * 主键约束违反处理类型
     */
    public enum PKViolationType {
        COVERED(1, "覆盖"), IN_DOUBT(2, "存疑");
        private int type;
        private String name;

        PKViolationType(int type, String name) {
            this.type = type;
            this.name = name;
        }

        public int getType() {
            return type;
        }
    }

    /**
     * 最新版本号键值
     */
    public static final String LATEST_VERSION_KEY = "latestVersion";
}
