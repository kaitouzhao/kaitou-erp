package kaitou.ppp.domain.shop;

import kaitou.ppp.domain.BaseDomain;
import kaitou.ppp.domain.system.SysCode;

/**
 * 基础信息全导出.
 * User: 赵立伟
 * Date: 2015/1/30
 * Time: 21:20
 */
public class ShopAll extends BaseDomain {
    /**
     * 认定店状态
     *
     * @see kaitou.ppp.domain.system.SysCode.ShopStatus
     */
    private String status;
    /**
     * 销售区域
     *
     * @see kaitou.ppp.domain.system.SysCode.SaleRegion
     */
    private String saleRegion;
    /**
     * 认定店编号（合同编号）
     */
    private String id;
    /**
     * 认定店名
     */
    private String name;
    /**
     * 合同联系人
     */
    private String pppBusinessInterfacePeople;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 邮寄地址
     */
    private String address;
    /**
     * 联系邮箱
     */
    private String email;
    /**
     * 认定年份
     */
    private String numberOfYear;
    /**
     * 产品线
     */
    private String productLine;
    /**
     * 认定级别
     */
    private String level;
    /**
     * 机型
     */
    private String model;

    @Override
    public void check() {

    }

    @Override
    public String dbFileName() {
        return dbFileSuffix();
    }

    @Override
    public String dbFileSuffix() {
        return "";
    }

    @Override
    public String toString() {
        return "ShopAll{" +
                "status='" + status + '\'' +
                ", saleRegion='" + saleRegion + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", pppBusinessInterfacePeople='" + pppBusinessInterfacePeople + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", numberOfYear='" + numberOfYear + '\'' +
                ", productLine='" + productLine + '\'' +
                ", level='" + level + '\'' +
                ", model='" + model + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ShopAll shopAll = (ShopAll) o;

        if (address != null ? !address.equals(shopAll.address) : shopAll.address != null) return false;
        if (email != null ? !email.equals(shopAll.email) : shopAll.email != null) return false;
        if (id != null ? !id.equals(shopAll.id) : shopAll.id != null) return false;
        if (level != null ? !level.equals(shopAll.level) : shopAll.level != null) return false;
        if (model != null ? !model.equals(shopAll.model) : shopAll.model != null) return false;
        if (name != null ? !name.equals(shopAll.name) : shopAll.name != null) return false;
        if (numberOfYear != null ? !numberOfYear.equals(shopAll.numberOfYear) : shopAll.numberOfYear != null)
            return false;
        if (phone != null ? !phone.equals(shopAll.phone) : shopAll.phone != null) return false;
        if (pppBusinessInterfacePeople != null ? !pppBusinessInterfacePeople.equals(shopAll.pppBusinessInterfacePeople) : shopAll.pppBusinessInterfacePeople != null)
            return false;
        if (productLine != null ? !productLine.equals(shopAll.productLine) : shopAll.productLine != null) return false;
        if (saleRegion != null ? !saleRegion.equals(shopAll.saleRegion) : shopAll.saleRegion != null) return false;
        if (status != null ? !status.equals(shopAll.status) : shopAll.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (saleRegion != null ? saleRegion.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (pppBusinessInterfacePeople != null ? pppBusinessInterfacePeople.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (numberOfYear != null ? numberOfYear.hashCode() : 0);
        result = 31 * result + (productLine != null ? productLine.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        return result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSaleRegion() {
        return SysCode.SaleRegion.convert2Value(saleRegion);
    }

    public void setSaleRegion(String saleRegion) {
        this.saleRegion = saleRegion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPppBusinessInterfacePeople() {
        return pppBusinessInterfacePeople;
    }

    public void setPppBusinessInterfacePeople(String pppBusinessInterfacePeople) {
        this.pppBusinessInterfacePeople = pppBusinessInterfacePeople;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumberOfYear() {
        return numberOfYear;
    }

    public void setNumberOfYear(String numberOfYear) {
        this.numberOfYear = numberOfYear;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
