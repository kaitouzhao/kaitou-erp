package kaitou.ppp.domain.tech;

import kaitou.ppp.domain.BaseDomain;
import kaitou.ppp.domain.system.SysCode;

/**
 * SDS权限管理.
 * User: 赵立伟
 * Date: 2015/5/19
 * Time: 16:33
 */
public class TechSDSPermission extends BaseDomain {
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

    @Override
    public void check() {

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
        return "SDSPermission{" +
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
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TechSDSPermission that = (TechSDSPermission) o;

        if (applicant != null ? !applicant.equals(that.applicant) : that.applicant != null) return false;
        if (applyDate != null ? !applyDate.equals(that.applyDate) : that.applyDate != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (firstApplyOrDelay != null ? !firstApplyOrDelay.equals(that.firstApplyOrDelay) : that.firstApplyOrDelay != null)
            return false;
        if (mac != null ? !mac.equals(that.mac) : that.mac != null) return false;
        if (note != null ? !note.equals(that.note) : that.note != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (productLine != null ? !productLine.equals(that.productLine) : that.productLine != null) return false;
        if (saleRegion != null ? !saleRegion.equals(that.saleRegion) : that.saleRegion != null) return false;
        if (shopId != null ? !shopId.equals(that.shopId) : that.shopId != null) return false;
        if (shopName != null ? !shopName.equals(that.shopName) : that.shopName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = saleRegion != null ? saleRegion.hashCode() : 0;
        result = 31 * result + (productLine != null ? productLine.hashCode() : 0);
        result = 31 * result + (shopId != null ? shopId.hashCode() : 0);
        result = 31 * result + (shopName != null ? shopName.hashCode() : 0);
        result = 31 * result + (applicant != null ? applicant.hashCode() : 0);
        result = 31 * result + (applyDate != null ? applyDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (mac != null ? mac.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (firstApplyOrDelay != null ? firstApplyOrDelay.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        return result;
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
