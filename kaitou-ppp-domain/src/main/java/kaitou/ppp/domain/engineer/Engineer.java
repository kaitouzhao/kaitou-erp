package kaitou.ppp.domain.engineer;

import kaitou.ppp.domain.BaseDomain;
import org.apache.commons.lang.StringUtils;

import static kaitou.ppp.domain.system.SysCode.*;

/**
 * 工程师.
 * User: 赵立伟
 * Date: 2015/1/17
 * Time: 10:36
 */
public class Engineer extends BaseDomain {
    /**
     * 销售区域
     *
     * @see SaleRegion
     */
    protected String saleRegion;
    /**
     * 认定店编编码
     */
    protected String shopId;
    /**
     * 认定店名
     */
    protected String shopName;
    /**
     * 认定店等级
     */
    protected String shopLevel;
    /**
     * 编号
     */
    protected String id;
    /**
     * 姓名
     */
    protected String name;
    /**
     * 产品线
     */
    protected String productLine;
    /**
     * 认定年限
     */
    protected String numberOfYear;
    /**
     * ACE等级
     */
    protected String aceLevel;
    /**
     * 入职日期
     */
    protected String dateOfEntry;
    /**
     * 离职日期
     */
    protected String dateOfDeparture;
    /**
     * 状态
     *
     * @see EngineerStatus
     */
    protected String status;
    /**
     * 邮箱
     */
    protected String email;
    /**
     * 电话
     */
    protected String phone;
    /**
     * 地址
     */
    protected String address;

    @Override
    public String dbFileSuffix() {
        return DB_FILE_NAME_SPLIT + shopId + DB_FILE_NAME_SPLIT + getClass().getSimpleName() + DB_SUFFIX;
    }

    /**
     * 生成数据文件名
     *
     * @return 文件名
     */
    @Override
    public String dbFileName() {
        return SaleRegion.convert2Code(saleRegion) + dbFileSuffix();
    }

    @Override
    public void check() {
        if (StringUtils.isEmpty(name)) {
            throw new RuntimeException("姓名为空");
        }
        if (StringUtils.isEmpty(saleRegion)) {
            throw new RuntimeException("区域为空");
        }
        if (StringUtils.isEmpty(shopId)) {
            throw new RuntimeException("认定店编码为空");
        }
        if (StringUtils.isEmpty(shopName)) {
            throw new RuntimeException("认定店名为空");
        }
        if (StringUtils.isEmpty(productLine)) {
            throw new RuntimeException("产品线为空");
        }
    }

    @Override
    public String toString() {
        return "Engineer{" +
                "saleRegion='" + saleRegion + '\'' +
                ", shopId='" + shopId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", shopLevel='" + shopLevel + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", productLine='" + productLine + '\'' +
                ", numberOfYear='" + numberOfYear + '\'' +
                ", aceLevel='" + aceLevel + '\'' +
                ", dateOfEntry='" + dateOfEntry + '\'' +
                ", dateOfDeparture='" + dateOfDeparture + '\'' +
                ", status='" + status + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
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

    public String getShopLevel() {
        return shopLevel;
    }

    public void setShopLevel(String shopLevel) {
        this.shopLevel = shopLevel;
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

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getNumberOfYear() {
        return numberOfYear;
    }

    public void setNumberOfYear(String numberOfYear) {
        this.numberOfYear = numberOfYear;
    }

    public String getAceLevel() {
        return aceLevel;
    }

    public void setAceLevel(String aceLevel) {
        this.aceLevel = aceLevel;
    }

    public String getDateOfEntry() {
        return dateOfEntry;
    }

    public void setDateOfEntry(String dateOfEntry) {
        this.dateOfEntry = dateOfEntry;
    }

    public String getDateOfDeparture() {
        return dateOfDeparture;
    }

    public void setDateOfDeparture(String dateOfDeparture) {
        this.dateOfDeparture = dateOfDeparture;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
