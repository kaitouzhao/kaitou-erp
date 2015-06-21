package kaitou.ppp.domain.tech;

import kaitou.ppp.domain.BaseDomain;
import kaitou.ppp.domain.system.SysCode;

/**
 * 手册权限管理.
 * User: 赵立伟
 * Date: 2015/5/19
 * Time: 10:10
 */
public class TechManualPermissions extends BaseDomain {
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
     * 用户名
     */
    private String userName;
    /**
     * 邮箱地址
     */
    private String email;
    /**
     * 授权机型
     */
    private String permissionModels;
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
        return "ManualPermissions{" +
                "saleRegion='" + saleRegion + '\'' +
                ", productLine='" + productLine + '\'' +
                ", shopId='" + shopId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", permissionModels='" + permissionModels + '\'' +
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPermissionModels() {
        return permissionModels;
    }

    public void setPermissionModels(String permissionModels) {
        this.permissionModels = permissionModels;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
