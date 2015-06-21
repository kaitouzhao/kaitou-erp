package kaitou.ppp.domain.warranty;

import kaitou.ppp.domain.BaseDomain;
import org.apache.commons.lang.StringUtils;

import static kaitou.ppp.domain.system.SysCode.DB_FILE_NAME_SPLIT;
import static kaitou.ppp.domain.system.SysCode.SaleRegion;

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
     * @see SaleRegion
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
        return DB_FILE_NAME_SPLIT + getClass().getSimpleName() + DB_SUFFIX;
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

    public String getNumberOfYear() {
        return numberOfYear;
    }

    public void setNumberOfYear(String numberOfYear) {
        this.numberOfYear = numberOfYear;
    }

    public String getSaleRegion() {
        return SaleRegion.convert2Value(saleRegion);
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
