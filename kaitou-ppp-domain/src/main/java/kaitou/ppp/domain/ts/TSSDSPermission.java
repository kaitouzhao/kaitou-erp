package kaitou.ppp.domain.ts;

import kaitou.ppp.domain.BaseDomain;
import kaitou.ppp.domain.system.SysCode;
import org.joda.time.DateTime;

import static kaitou.ppp.common.utils.DateTimeUtil.isSameDate;
import static kaitou.ppp.common.utils.DateTimeUtil.toDate;

/**
 * TS SDS权限.
 * User: 赵立伟
 * Date: 2015/5/20
 * Time: 16:53
 */
public class TSSDSPermission extends BaseDomain {
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
     * 城市
     */
    private String city;
    /**
     * 首次/更新
     */
    private String addOrUpdate;
    /**
     * 员工号
     */
    private String employeeNo;
    /**
     * 工程师姓名
     */
    private String engineerName;
    /**
     * 邮箱地址
     */
    private String email;
    /**
     * MAC 地址
     */
    private String mac;
    /**
     * 申请时间
     */
    private String applyDate;
    /**
     * 到期时间
     */
    private String endDate;
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
        return "TSSDSPermission{" +
                "saleRegion='" + saleRegion + '\'' +
                ", city='" + city + '\'' +
                ", addOrUpdate='" + addOrUpdate + '\'' +
                ", employeeNo='" + employeeNo + '\'' +
                ", engineerName='" + engineerName + '\'' +
                ", email='" + email + '\'' +
                ", mac='" + mac + '\'' +
                ", applyDate='" + applyDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", note='" + note + '\'' +
                ", updated='" + updated + '\'' +
                '}';
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddOrUpdate() {
        return addOrUpdate;
    }

    public void setAddOrUpdate(String addOrUpdate) {
        this.addOrUpdate = addOrUpdate;
    }

    public String getEngineerName() {
        return engineerName;
    }

    public void setEngineerName(String engineerName) {
        this.engineerName = engineerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSaleRegion() {
        return SysCode.SaleRegion.convert2Value(saleRegion);
    }

    public void setSaleRegion(String saleRegion) {
        this.saleRegion = saleRegion;
    }

}
