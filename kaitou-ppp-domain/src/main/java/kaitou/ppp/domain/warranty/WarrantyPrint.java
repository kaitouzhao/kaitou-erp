package kaitou.ppp.domain.warranty;

import kaitou.ppp.domain.BaseDomain;
import kaitou.ppp.domain.system.SysCode;
import org.apache.commons.lang.StringUtils;

/**
 * 打印头保修.
 * User: 赵立伟
 * Date: 2015/5/18
 * Time: 11:13
 */
public class WarrantyPrint extends BaseDomain {
    /**
     * 年份
     */
    private String numberOfYear;
    /**
     * 区域
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
     * 机型
     */
    private String models;
    /**
     * 机身号
     */
    private String fuselage;
    /**
     * 保修耗材型号
     */
    private String warrantyConsumables;
    /**
     * 用户名称
     */
    private String userCompanyName;
    /**
     * 用户城市
     */
    private String userCity;
    /**
     * 用户电话
     */
    private String userContact;
    /**
     * 联系人
     */
    private String userLinkMan;
    /**
     * 故障现象
     */
    private String faultPhenomenon;
    /**
     * PRINT INF
     */
    private String printInf;
    /**
     * Nozzle Check
     */
    private String nozzleCheck;
    /**
     * 销售凭证
     */
    private String salesReceipts;
    /**
     * 维修站申请日期
     */
    private String applyDate;
    /**
     * 打印头安装日期
     */
    private String installedDate;
    /**
     * 打印头故障日期
     */
    private String faultDate;
    /**
     * 保修担当接收日期
     */
    private String warrantyReceivingDate;
    /**
     * 月份
     */
    private String numberOfMonth;
    /**
     * 确认结果
     */
    private String confirmResult;
    /**
     * 技术担当接收日期
     */
    private String techReceivingDate;
    /**
     * 再次确认结果
     */
    private String confirmResultAgain;
    /**
     * 故障类型
     */
    private String faultType;
    /**
     * 检测编号
     */
    private String checkNo;
    /**
     * DOUNT COUNT
     */
    private String dountCount;
    /**
     * HQ担当接收日期
     */
    private String hqReceivingDate;
    /**
     * 三次确认结果
     */
    private String confirmResultThird;

    @Override
    public void check() {
        if (StringUtils.isEmpty(numberOfYear)) {
            throw new RuntimeException("年份为空");
        }
    }

    @Override
    public String dbFileName() {
        return numberOfYear + dbFileSuffix();
    }

    @Override
    public String dbFileSuffix() {
        return '_' + getClass().getSimpleName() + DB_SUFFIX;
    }

    @Override
    public String toString() {
        return "WarrantyPrint{" +
                "numberOfYear='" + numberOfYear + '\'' +
                ", saleRegion='" + saleRegion + '\'' +
                ", shopId='" + shopId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", models='" + models + '\'' +
                ", fuselage='" + fuselage + '\'' +
                ", warrantyConsumables='" + warrantyConsumables + '\'' +
                ", userCompanyName='" + userCompanyName + '\'' +
                ", userCity='" + userCity + '\'' +
                ", userContact='" + userContact + '\'' +
                ", userLinkMan='" + userLinkMan + '\'' +
                ", faultPhenomenon='" + faultPhenomenon + '\'' +
                ", printInf='" + printInf + '\'' +
                ", nozzleCheck='" + nozzleCheck + '\'' +
                ", salesReceipts='" + salesReceipts + '\'' +
                ", applyDate='" + applyDate + '\'' +
                ", installedDate='" + installedDate + '\'' +
                ", faultDate='" + faultDate + '\'' +
                ", warrantyReceivingDate='" + warrantyReceivingDate + '\'' +
                ", numberOfMonth='" + numberOfMonth + '\'' +
                ", confirmResult='" + confirmResult + '\'' +
                ", techReceivingDate='" + techReceivingDate + '\'' +
                ", confirmResultAgain='" + confirmResultAgain + '\'' +
                ", faultType='" + faultType + '\'' +
                ", checkNo='" + checkNo + '\'' +
                ", dountCount='" + dountCount + '\'' +
                ", hqReceivingDate='" + hqReceivingDate + '\'' +
                ", confirmResultThird='" + confirmResultThird + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WarrantyPrint that = (WarrantyPrint) o;

        if (applyDate != null ? !applyDate.equals(that.applyDate) : that.applyDate != null) return false;
        if (checkNo != null ? !checkNo.equals(that.checkNo) : that.checkNo != null) return false;
        if (confirmResult != null ? !confirmResult.equals(that.confirmResult) : that.confirmResult != null)
            return false;
        if (confirmResultAgain != null ? !confirmResultAgain.equals(that.confirmResultAgain) : that.confirmResultAgain != null)
            return false;
        if (confirmResultThird != null ? !confirmResultThird.equals(that.confirmResultThird) : that.confirmResultThird != null)
            return false;
        if (dountCount != null ? !dountCount.equals(that.dountCount) : that.dountCount != null) return false;
        if (faultDate != null ? !faultDate.equals(that.faultDate) : that.faultDate != null) return false;
        if (faultPhenomenon != null ? !faultPhenomenon.equals(that.faultPhenomenon) : that.faultPhenomenon != null)
            return false;
        if (faultType != null ? !faultType.equals(that.faultType) : that.faultType != null) return false;
        if (fuselage != null ? !fuselage.equals(that.fuselage) : that.fuselage != null) return false;
        if (hqReceivingDate != null ? !hqReceivingDate.equals(that.hqReceivingDate) : that.hqReceivingDate != null)
            return false;
        if (installedDate != null ? !installedDate.equals(that.installedDate) : that.installedDate != null)
            return false;
        if (models != null ? !models.equals(that.models) : that.models != null) return false;
        if (nozzleCheck != null ? !nozzleCheck.equals(that.nozzleCheck) : that.nozzleCheck != null) return false;
        if (numberOfMonth != null ? !numberOfMonth.equals(that.numberOfMonth) : that.numberOfMonth != null)
            return false;
        if (numberOfYear != null ? !numberOfYear.equals(that.numberOfYear) : that.numberOfYear != null) return false;
        if (printInf != null ? !printInf.equals(that.printInf) : that.printInf != null) return false;
        if (saleRegion != null ? !saleRegion.equals(that.saleRegion) : that.saleRegion != null) return false;
        if (salesReceipts != null ? !salesReceipts.equals(that.salesReceipts) : that.salesReceipts != null)
            return false;
        if (shopId != null ? !shopId.equals(that.shopId) : that.shopId != null) return false;
        if (shopName != null ? !shopName.equals(that.shopName) : that.shopName != null) return false;
        if (techReceivingDate != null ? !techReceivingDate.equals(that.techReceivingDate) : that.techReceivingDate != null)
            return false;
        if (userCity != null ? !userCity.equals(that.userCity) : that.userCity != null) return false;
        if (userCompanyName != null ? !userCompanyName.equals(that.userCompanyName) : that.userCompanyName != null)
            return false;
        if (userContact != null ? !userContact.equals(that.userContact) : that.userContact != null) return false;
        if (userLinkMan != null ? !userLinkMan.equals(that.userLinkMan) : that.userLinkMan != null) return false;
        if (warrantyConsumables != null ? !warrantyConsumables.equals(that.warrantyConsumables) : that.warrantyConsumables != null)
            return false;
        if (warrantyReceivingDate != null ? !warrantyReceivingDate.equals(that.warrantyReceivingDate) : that.warrantyReceivingDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = numberOfYear != null ? numberOfYear.hashCode() : 0;
        result = 31 * result + (saleRegion != null ? saleRegion.hashCode() : 0);
        result = 31 * result + (shopId != null ? shopId.hashCode() : 0);
        result = 31 * result + (shopName != null ? shopName.hashCode() : 0);
        result = 31 * result + (models != null ? models.hashCode() : 0);
        result = 31 * result + (fuselage != null ? fuselage.hashCode() : 0);
        result = 31 * result + (warrantyConsumables != null ? warrantyConsumables.hashCode() : 0);
        result = 31 * result + (userCompanyName != null ? userCompanyName.hashCode() : 0);
        result = 31 * result + (userCity != null ? userCity.hashCode() : 0);
        result = 31 * result + (userContact != null ? userContact.hashCode() : 0);
        result = 31 * result + (userLinkMan != null ? userLinkMan.hashCode() : 0);
        result = 31 * result + (faultPhenomenon != null ? faultPhenomenon.hashCode() : 0);
        result = 31 * result + (printInf != null ? printInf.hashCode() : 0);
        result = 31 * result + (nozzleCheck != null ? nozzleCheck.hashCode() : 0);
        result = 31 * result + (salesReceipts != null ? salesReceipts.hashCode() : 0);
        result = 31 * result + (applyDate != null ? applyDate.hashCode() : 0);
        result = 31 * result + (installedDate != null ? installedDate.hashCode() : 0);
        result = 31 * result + (faultDate != null ? faultDate.hashCode() : 0);
        result = 31 * result + (warrantyReceivingDate != null ? warrantyReceivingDate.hashCode() : 0);
        result = 31 * result + (numberOfMonth != null ? numberOfMonth.hashCode() : 0);
        result = 31 * result + (confirmResult != null ? confirmResult.hashCode() : 0);
        result = 31 * result + (techReceivingDate != null ? techReceivingDate.hashCode() : 0);
        result = 31 * result + (confirmResultAgain != null ? confirmResultAgain.hashCode() : 0);
        result = 31 * result + (faultType != null ? faultType.hashCode() : 0);
        result = 31 * result + (checkNo != null ? checkNo.hashCode() : 0);
        result = 31 * result + (dountCount != null ? dountCount.hashCode() : 0);
        result = 31 * result + (hqReceivingDate != null ? hqReceivingDate.hashCode() : 0);
        result = 31 * result + (confirmResultThird != null ? confirmResultThird.hashCode() : 0);
        return result;
    }

    public String getNumberOfYear() {
        return numberOfYear;
    }

    public void setNumberOfYear(String numberOfYear) {
        this.numberOfYear = numberOfYear;
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

    public String getModels() {
        return models;
    }

    public void setModels(String models) {
        this.models = models;
    }

    public String getFuselage() {
        return fuselage;
    }

    public void setFuselage(String fuselage) {
        this.fuselage = fuselage;
    }

    public String getWarrantyConsumables() {
        return warrantyConsumables;
    }

    public void setWarrantyConsumables(String warrantyConsumables) {
        this.warrantyConsumables = warrantyConsumables;
    }

    public String getUserCompanyName() {
        return userCompanyName;
    }

    public void setUserCompanyName(String userCompanyName) {
        this.userCompanyName = userCompanyName;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public String getUserLinkMan() {
        return userLinkMan;
    }

    public void setUserLinkMan(String userLinkMan) {
        this.userLinkMan = userLinkMan;
    }

    public String getFaultPhenomenon() {
        return faultPhenomenon;
    }

    public void setFaultPhenomenon(String faultPhenomenon) {
        this.faultPhenomenon = faultPhenomenon;
    }

    public String getPrintInf() {
        return printInf;
    }

    public void setPrintInf(String printInf) {
        this.printInf = printInf;
    }

    public String getNozzleCheck() {
        return nozzleCheck;
    }

    public void setNozzleCheck(String nozzleCheck) {
        this.nozzleCheck = nozzleCheck;
    }

    public String getSalesReceipts() {
        return salesReceipts;
    }

    public void setSalesReceipts(String salesReceipts) {
        this.salesReceipts = salesReceipts;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getInstalledDate() {
        return installedDate;
    }

    public void setInstalledDate(String installedDate) {
        this.installedDate = installedDate;
    }

    public String getFaultDate() {
        return faultDate;
    }

    public void setFaultDate(String faultDate) {
        this.faultDate = faultDate;
    }

    public String getWarrantyReceivingDate() {
        return warrantyReceivingDate;
    }

    public void setWarrantyReceivingDate(String warrantyReceivingDate) {
        this.warrantyReceivingDate = warrantyReceivingDate;
    }

    public String getNumberOfMonth() {
        return numberOfMonth;
    }

    public void setNumberOfMonth(String numberOfMonth) {
        this.numberOfMonth = numberOfMonth;
    }

    public String getConfirmResult() {
        return confirmResult;
    }

    public void setConfirmResult(String confirmResult) {
        this.confirmResult = confirmResult;
    }

    public String getTechReceivingDate() {
        return techReceivingDate;
    }

    public void setTechReceivingDate(String techReceivingDate) {
        this.techReceivingDate = techReceivingDate;
    }

    public String getConfirmResultAgain() {
        return confirmResultAgain;
    }

    public void setConfirmResultAgain(String confirmResultAgain) {
        this.confirmResultAgain = confirmResultAgain;
    }

    public String getFaultType() {
        return faultType;
    }

    public void setFaultType(String faultType) {
        this.faultType = faultType;
    }

    public String getCheckNo() {
        return checkNo;
    }

    public void setCheckNo(String checkNo) {
        this.checkNo = checkNo;
    }

    public String getDountCount() {
        return dountCount;
    }

    public void setDountCount(String dountCount) {
        this.dountCount = dountCount;
    }

    public String getHqReceivingDate() {
        return hqReceivingDate;
    }

    public void setHqReceivingDate(String hqReceivingDate) {
        this.hqReceivingDate = hqReceivingDate;
    }

    public String getConfirmResultThird() {
        return confirmResultThird;
    }

    public void setConfirmResultThird(String confirmResultThird) {
        this.confirmResultThird = confirmResultThird;
    }
}
