package kaitou.ppp.domain.ts;

import kaitou.ppp.domain.BaseDomain;
import kaitou.ppp.domain.system.SysCode;

/**
 * TS SDS权限.
 * User: 赵立伟
 * Date: 2015/5/20
 * Time: 16:53
 */
public class TSSDSPermission extends BaseDomain {
    /**
     * 区域
     *
     * @see kaitou.ppp.domain.system.SysCode.SaleRegion
     */
    private String saleRegion;
    /**
     * 首次/更新
     */
    private String addOrUpdate;
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
                ", addOrUpdate='" + addOrUpdate + '\'' +
                ", engineerName='" + engineerName + '\'' +
                ", email='" + email + '\'' +
                ", mac='" + mac + '\'' +
                ", applyDate='" + applyDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TSSDSPermission that = (TSSDSPermission) o;

        if (addOrUpdate != null ? !addOrUpdate.equals(that.addOrUpdate) : that.addOrUpdate != null) return false;
        if (applyDate != null ? !applyDate.equals(that.applyDate) : that.applyDate != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (engineerName != null ? !engineerName.equals(that.engineerName) : that.engineerName != null) return false;
        if (mac != null ? !mac.equals(that.mac) : that.mac != null) return false;
        if (note != null ? !note.equals(that.note) : that.note != null) return false;
        if (saleRegion != null ? !saleRegion.equals(that.saleRegion) : that.saleRegion != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = saleRegion != null ? saleRegion.hashCode() : 0;
        result = 31 * result + (addOrUpdate != null ? addOrUpdate.hashCode() : 0);
        result = 31 * result + (engineerName != null ? engineerName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (mac != null ? mac.hashCode() : 0);
        result = 31 * result + (applyDate != null ? applyDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        return result;
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
