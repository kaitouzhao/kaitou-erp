package kaitou.ppp.domain.tech;

import kaitou.ppp.domain.BaseDomain;
import kaitou.ppp.domain.system.SysCode;
import org.joda.time.DateTime;

import static kaitou.ppp.common.utils.DateTimeUtil.isSameDate;
import static kaitou.ppp.common.utils.DateTimeUtil.toDate;

/**
 * SDS权限管理.
 * User: 赵立伟
 * Date: 2015/5/19
 * Time: 16:33
 */
public class TechSDSPermission extends BaseDomain {
    /**
     * 提前提醒天数
     */
    private static final int REMINDER_DAYS = 3;
    /**
     * 区域
     *
     * @see kaitou.ppp.domain.system.SysCode.SaleRegion
     */
    private String saleRegion;
    /**
     * 产品线
     */
    private String productLine;
    /**
     * 认定店编号
     */
    private String shopId;
    /**
     * 认定店名称
     */
    private String shopName;
    /**
     * 申请人
     */
    private String applicant;
    /**
     * 申请时间
     */
    private String applyDate;
    /**
     * 到期时间
     */
    private String endDate;
    /**
     * MAC地址
     */
    private String mac;
    /**
     * Email地址
     */
    private String email;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 首次申请/延期
     */
    private String firstApplyOrDelay;
    /**
     * 备注
     */
    private String note;
    /**
     * 是否被更新
     */
    private String updated = "未更新";

    /**
     * 是否需要提醒
     * <p>到期时间三天前需要提醒</p>
     *
     * @return 是为真
     */
    public boolean shouldReminder() {
        DateTime endDateTime = toDate(endDate);
        if (endDateTime == null || "更新".equals(updated)) {
            return false;
        }
        return isSameDate(endDateTime.minusDays(REMINDER_DAYS), new DateTime());
    }

    @Override
    public String dbFileName() {
        return dbFileSuffix();
    }

    @Override
    public String dbFileSuffix() {
        return getClass().getSimpleName() + DB_SUFFIX;
    }

    @Override
    public String toString() {
        return "TechSDSPermission{" +
                "saleRegion='" + saleRegion + '\'' +
                ", productLine='" + productLine + '\'' +
                ", shopId='" + shopId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", applicant='" + applicant + '\'' +
                ", applyDate='" + applyDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", mac='" + mac + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", firstApplyOrDelay='" + firstApplyOrDelay + '\'' +
                ", note='" + note + '\'' +
                ", updated='" + updated + '\'' +
                '}';
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getSaleRegion() {
        return SysCode.SaleRegion.convert2Value(saleRegion);
    }

    public void setSaleRegion(String saleRegion) {
        this.saleRegion = saleRegion;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstApplyOrDelay() {
        return firstApplyOrDelay;
    }

    public void setFirstApplyOrDelay(String firstApplyOrDelay) {
        this.firstApplyOrDelay = firstApplyOrDelay;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
