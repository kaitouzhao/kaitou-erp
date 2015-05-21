package kaitou.ppp.domain.ts;

import kaitou.ppp.domain.BaseDomain;
import kaitou.ppp.domain.system.SysCode;

/**
 * TS手册权限.
 * User: 赵立伟
 * Date: 2015/5/20
 * Time: 13:42
 */
public class TSManualPermissions extends BaseDomain {
    /**
     * 区域
     *
     * @see kaitou.ppp.domain.system.SysCode.SaleRegion
     */
    private String saleRegion;
    /**
     * 申请时间
     */
    private String applyDate;
    /**
     * 工程师名称
     */
    private String engineerName;
    /**
     * Robin帐号
     */
    private String robinAccount;
    /**
     * 邮箱地址
     */
    private String email;
    /**
     * 添加机型
     */
    private String addModel;
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
        return "TSManualPermissions{" +
                "saleRegion='" + saleRegion + '\'' +
                ", applyDate='" + applyDate + '\'' +
                ", engineerName='" + engineerName + '\'' +
                ", robinAccount='" + robinAccount + '\'' +
                ", email='" + email + '\'' +
                ", addModel='" + addModel + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TSManualPermissions that = (TSManualPermissions) o;

        if (addModel != null ? !addModel.equals(that.addModel) : that.addModel != null) return false;
        if (applyDate != null ? !applyDate.equals(that.applyDate) : that.applyDate != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (engineerName != null ? !engineerName.equals(that.engineerName) : that.engineerName != null) return false;
        if (note != null ? !note.equals(that.note) : that.note != null) return false;
        if (robinAccount != null ? !robinAccount.equals(that.robinAccount) : that.robinAccount != null) return false;
        if (saleRegion != null ? !saleRegion.equals(that.saleRegion) : that.saleRegion != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = saleRegion != null ? saleRegion.hashCode() : 0;
        result = 31 * result + (applyDate != null ? applyDate.hashCode() : 0);
        result = 31 * result + (engineerName != null ? engineerName.hashCode() : 0);
        result = 31 * result + (robinAccount != null ? robinAccount.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (addModel != null ? addModel.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        return result;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getEngineerName() {
        return engineerName;
    }

    public void setEngineerName(String engineerName) {
        this.engineerName = engineerName;
    }

    public String getRobinAccount() {
        return robinAccount;
    }

    public void setRobinAccount(String robinAccount) {
        this.robinAccount = robinAccount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddModel() {
        return addModel;
    }

    public void setAddModel(String addModel) {
        this.addModel = addModel;
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
