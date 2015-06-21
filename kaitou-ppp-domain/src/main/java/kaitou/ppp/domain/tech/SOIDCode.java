package kaitou.ppp.domain.tech;

import kaitou.ppp.domain.BaseDomain;
import kaitou.ppp.domain.system.SysCode;

/**
 * SOID识别码.
 * User: 赵立伟
 * Date: 2015/5/19
 * Time: 11:14
 */
public class SOIDCode extends BaseDomain {
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
     * 授权产品线
     */
    private String permissionProductLine;
    /**
     * NEW SOID
     */
    private String newSoid;
    /**
     * New Verification Code
     */
    private String newVerificationCode;
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
        return "SOIDCode{" +
                "saleRegion='" + saleRegion + '\'' +
                ", shopId='" + shopId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", permissionProductLine='" + permissionProductLine + '\'' +
                ", newSoid='" + newSoid + '\'' +
                ", newVerificationCode='" + newVerificationCode + '\'' +
                ", note='" + note + '\'' +
                '}';
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

    public String getPermissionProductLine() {
        return permissionProductLine;
    }

    public void setPermissionProductLine(String permissionProductLine) {
        this.permissionProductLine = permissionProductLine;
    }

    public String getNewSoid() {
        return newSoid;
    }

    public void setNewSoid(String newSoid) {
        this.newSoid = newSoid;
    }

    public String getNewVerificationCode() {
        return newVerificationCode;
    }

    public void setNewVerificationCode(String newVerificationCode) {
        this.newVerificationCode = newVerificationCode;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
