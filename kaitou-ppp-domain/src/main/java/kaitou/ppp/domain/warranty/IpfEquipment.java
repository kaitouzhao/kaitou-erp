package kaitou.ppp.domain.warranty;

import kaitou.ppp.domain.BaseDomain4InDoubt;
import kaitou.ppp.domain.annotation.PKField;
import kaitou.ppp.domain.system.SysCode;
import org.apache.commons.lang.StringUtils;

/**
 * iPF设备.
 * User: 赵立伟
 * Date: 2015/6/28
 * Time: 11:22
 */
public class IpfEquipment extends BaseDomain4InDoubt {
    /**
     * 年
     */
    private String numberOfYear;
    /**
     * 月
     */
    private String numberOfMonth;
    /**
     * 序号
     */
    private String serialNumber;
    /**
     * 区域
     *
     * @see kaitou.ppp.domain.system.SysCode.SaleRegion
     */
    private String saleRegion;
    /**
     * 认定店地区
     */
    private String shopArea;
    /**
     * 机型
     */
    private String model;
    /**
     * 机身号
     * <p>主键</p>
     */
    @PKField(PKViolationType = SysCode.PKViolationType.IN_DOUBT)
    private String fuselage;
    /**
     * 联系人
     */
    private String userLinkMan;
    /**
     * 最终用户名称
     */
    private String userCompanyName;
    /**
     * 用户地区
     */
    private String userArea;
    /**
     * 用户所属城市
     */
    private String userCity;
    /**
     * 联系地址
     */
    private String installedAddress;
    /**
     * 用户电话
     */
    private String userContact;
    /**
     * 用户手机
     */
    private String userPhone;
    /**
     * 认定店编号
     */
    private String shopId;
    /**
     * 认定店名称
     */
    private String shopName;
    /**
     * 销售日期
     */
    private String saleDate;

    @Override
    public void check() {
        if (StringUtils.isEmpty(fuselage) || StringUtils.isEmpty(shopId) || StringUtils.isEmpty(shopName)) {
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
        return "IpfEquipment{" +
                "numberOfYear='" + numberOfYear + '\'' +
                ", numberOfMonth='" + numberOfMonth + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", saleRegion='" + saleRegion + '\'' +
                ", shopArea='" + shopArea + '\'' +
                ", model='" + model + '\'' +
                ", fuselage='" + fuselage + '\'' +
                ", userLinkMan='" + userLinkMan + '\'' +
                ", userCompanyName='" + userCompanyName + '\'' +
                ", userArea='" + userArea + '\'' +
                ", userCity='" + userCity + '\'' +
                ", installedAddress='" + installedAddress + '\'' +
                ", userContact='" + userContact + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", shopId='" + shopId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", saleDate='" + saleDate + '\'' +
                '}';
    }

    public String getNumberOfYear() {
        return numberOfYear;
    }

    public void setNumberOfYear(String numberOfYear) {
        this.numberOfYear = numberOfYear;
    }

    public String getNumberOfMonth() {
        return numberOfMonth;
    }

    public void setNumberOfMonth(String numberOfMonth) {
        this.numberOfMonth = numberOfMonth;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSaleRegion() {
        return saleRegion;
    }

    public void setSaleRegion(String saleRegion) {
        this.saleRegion = saleRegion;
    }

    public String getShopArea() {
        return shopArea;
    }

    public void setShopArea(String shopArea) {
        this.shopArea = shopArea;
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

    public String getUserLinkMan() {
        return userLinkMan;
    }

    public void setUserLinkMan(String userLinkMan) {
        this.userLinkMan = userLinkMan;
    }

    public String getUserCompanyName() {
        return userCompanyName;
    }

    public void setUserCompanyName(String userCompanyName) {
        this.userCompanyName = userCompanyName;
    }

    public String getUserArea() {
        return userArea;
    }

    public void setUserArea(String userArea) {
        this.userArea = userArea;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getInstalledAddress() {
        return installedAddress;
    }

    public void setInstalledAddress(String installedAddress) {
        this.installedAddress = installedAddress;
    }

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
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

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }
}
