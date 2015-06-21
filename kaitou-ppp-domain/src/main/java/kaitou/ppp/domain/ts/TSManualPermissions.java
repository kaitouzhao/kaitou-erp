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
