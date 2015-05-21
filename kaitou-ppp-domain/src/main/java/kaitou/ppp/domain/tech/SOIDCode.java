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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SOIDCode soidCode = (SOIDCode) o;

        if (newSoid != null ? !newSoid.equals(soidCode.newSoid) : soidCode.newSoid != null) return false;
        if (newVerificationCode != null ? !newVerificationCode.equals(soidCode.newVerificationCode) : soidCode.newVerificationCode != null)
            return false;
        if (note != null ? !note.equals(soidCode.note) : soidCode.note != null) return false;
        if (permissionProductLine != null ? !permissionProductLine.equals(soidCode.permissionProductLine) : soidCode.permissionProductLine != null)
            return false;
        if (saleRegion != null ? !saleRegion.equals(soidCode.saleRegion) : soidCode.saleRegion != null) return false;
        if (shopId != null ? !shopId.equals(soidCode.shopId) : soidCode.shopId != null) return false;
        if (shopName != null ? !shopName.equals(soidCode.shopName) : soidCode.shopName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = saleRegion != null ? saleRegion.hashCode() : 0;
        result = 31 * result + (shopId != null ? shopId.hashCode() : 0);
        result = 31 * result + (shopName != null ? shopName.hashCode() : 0);
        result = 31 * result + (permissionProductLine != null ? permissionProductLine.hashCode() : 0);
        result = 31 * result + (newSoid != null ? newSoid.hashCode() : 0);
        result = 31 * result + (newVerificationCode != null ? newVerificationCode.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        return result;
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
