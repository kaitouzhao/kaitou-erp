package kaitou.ppp.domain.tech;

import kaitou.ppp.domain.BaseDomain;
import kaitou.ppp.domain.system.SysCode;

/**
 * 装机权限.
 * User: 赵立伟
 * Date: 2015/5/19
 * Time: 18:57
 */
public class TechInstallPermission extends BaseDomain {
    /**
     * 销售区域
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
     * 机型
     */
    private String model;
    /**
     * 机身号
     */
    private String fuselage;
    /**
     * MAC地址
     */
    private String mac;
    /**
     * 开放时间
     */
    private String openingTime;
    /**
     * 最终用户名称
     */
    private String userCompanyName;
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
        return "InstallPermission{" +
                "saleRegion='" + saleRegion + '\'' +
                ", productLine='" + productLine + '\'' +
                ", shopId='" + shopId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", model='" + model + '\'' +
                ", fuselage='" + fuselage + '\'' +
                ", mac='" + mac + '\'' +
                ", openingTime='" + openingTime + '\'' +
                ", userCompanyName='" + userCompanyName + '\'' +
                ", note='" + note + '\'' +
                '}';
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

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getUserCompanyName() {
        return userCompanyName;
    }

    public void setUserCompanyName(String userCompanyName) {
        this.userCompanyName = userCompanyName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
