package kaitou.ppp.domain.warranty;

import kaitou.ppp.domain.BaseDomain4InDoubt;
import kaitou.ppp.domain.annotation.PKField;
import kaitou.ppp.domain.system.SysCode;
import org.apache.commons.lang.StringUtils;

/**
 * 保修费.
 * User: 赵立伟
 * Date: 2015/5/6
 * Time: 14:06
 */
public class WarrantyFee extends BaseDomain4InDoubt {
    /**
     * 年份
     */
    private String numberOfYear;
    /**
     * 季度
     */
    private String quarter;
    /**
     * 保修卡号
     */
    private String warrantyCardNo;
    /**
     * 销售区域
     *
     * @see kaitou.ppp.domain.system.SysCode.SaleRegion
     */
    private String saleRegion;
    /**
     * 认定店编号
     */
    private String shopId;
    /**
     * 认定店名称
     */
    private String shopName;
    /**
     * 产品线
     */
    private String productLine;
    /**
     * 机型
     */
    private String model;
    /**
     * 机身号
     */
    @PKField(PKViolationType = SysCode.PKViolationType.IN_DOUBT)
    private String fuselage;
    /**
     * 安装日期
     */
    private String installedDate;
    /**
     * 终止日期
     */
    private String endDate;
    /**
     * 最终用户名称
     */
    private String userCompanyName;
    /**
     * 安装费
     */
    private String installedFee;
    /**
     * 保养费
     */
    private String maintenanceFee;
    /**
     * TTL
     */
    private String ttl;

    @Override
    public void check() {
        if (StringUtils.isEmpty(fuselage)) {
            doInDoubt();
        }
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
        return "WarrantyFee{" +
                "numberOfYear='" + numberOfYear + '\'' +
                ", quarter='" + quarter + '\'' +
                ", warrantyCardNo='" + warrantyCardNo + '\'' +
                ", saleRegion='" + saleRegion + '\'' +
                ", shopId='" + shopId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", productLine='" + productLine + '\'' +
                ", model='" + model + '\'' +
                ", fuselage='" + fuselage + '\'' +
                ", installedDate='" + installedDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", userCompanyName='" + userCompanyName + '\'' +
                ", installedFee='" + installedFee + '\'' +
                ", maintenanceFee='" + maintenanceFee + '\'' +
                ", ttl='" + ttl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        WarrantyFee that = (WarrantyFee) o;

        if (serialNo > 0) {
            return super.equals(o);
        }

        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (fuselage != null ? !fuselage.equals(that.fuselage) : that.fuselage != null) return false;
        if (installedDate != null ? !installedDate.equals(that.installedDate) : that.installedDate != null)
            return false;
        if (installedFee != null ? !installedFee.equals(that.installedFee) : that.installedFee != null) return false;
        if (maintenanceFee != null ? !maintenanceFee.equals(that.maintenanceFee) : that.maintenanceFee != null)
            return false;
        if (model != null ? !model.equals(that.model) : that.model != null) return false;
        if (numberOfYear != null ? !numberOfYear.equals(that.numberOfYear) : that.numberOfYear != null) return false;
        if (productLine != null ? !productLine.equals(that.productLine) : that.productLine != null) return false;
        if (quarter != null ? !quarter.equals(that.quarter) : that.quarter != null) return false;
        if (saleRegion != null ? !saleRegion.equals(that.saleRegion) : that.saleRegion != null) return false;
        if (shopId != null ? !shopId.equals(that.shopId) : that.shopId != null) return false;
        if (shopName != null ? !shopName.equals(that.shopName) : that.shopName != null) return false;
        if (ttl != null ? !ttl.equals(that.ttl) : that.ttl != null) return false;
        if (userCompanyName != null ? !userCompanyName.equals(that.userCompanyName) : that.userCompanyName != null)
            return false;
        if (warrantyCardNo != null ? !warrantyCardNo.equals(that.warrantyCardNo) : that.warrantyCardNo != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        if (serialNo > 0) {
            return super.hashCode();
        }

        int result = super.hashCode();
        result = 31 * result + (numberOfYear != null ? numberOfYear.hashCode() : 0);
        result = 31 * result + (quarter != null ? quarter.hashCode() : 0);
        result = 31 * result + (warrantyCardNo != null ? warrantyCardNo.hashCode() : 0);
        result = 31 * result + (saleRegion != null ? saleRegion.hashCode() : 0);
        result = 31 * result + (shopId != null ? shopId.hashCode() : 0);
        result = 31 * result + (shopName != null ? shopName.hashCode() : 0);
        result = 31 * result + (productLine != null ? productLine.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (fuselage != null ? fuselage.hashCode() : 0);
        result = 31 * result + (installedDate != null ? installedDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (userCompanyName != null ? userCompanyName.hashCode() : 0);
        result = 31 * result + (installedFee != null ? installedFee.hashCode() : 0);
        result = 31 * result + (maintenanceFee != null ? maintenanceFee.hashCode() : 0);
        result = 31 * result + (ttl != null ? ttl.hashCode() : 0);
        return result;
    }

    public String getNumberOfYear() {
        return numberOfYear;
    }

    public void setNumberOfYear(String numberOfYear) {
        this.numberOfYear = numberOfYear;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public String getWarrantyCardNo() {
        return warrantyCardNo;
    }

    public void setWarrantyCardNo(String warrantyCardNo) {
        this.warrantyCardNo = warrantyCardNo;
    }

    public String getSaleRegion() {
        return SysCode.SaleRegion.convert2Value(saleRegion);
    }

    public void setSaleRegion(String saleRegion) {
        this.saleRegion = saleRegion;
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

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFuselage() {
        return fuselage;
    }

    public void setFuselage(String fuselage) {
        this.fuselage = fuselage;
    }

    public String getInstalledDate() {
        return installedDate;
    }

    public void setInstalledDate(String installedDate) {
        this.installedDate = installedDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getUserCompanyName() {
        return userCompanyName;
    }

    public void setUserCompanyName(String userCompanyName) {
        this.userCompanyName = userCompanyName;
    }

    public String getInstalledFee() {
        return installedFee;
    }

    public void setInstalledFee(String installedFee) {
        this.installedFee = installedFee;
    }

    public String getMaintenanceFee() {
        return maintenanceFee;
    }

    public void setMaintenanceFee(String maintenanceFee) {
        this.maintenanceFee = maintenanceFee;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }
}
