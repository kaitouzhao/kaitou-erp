package kaitou.ppp.domain.shop;

import kaitou.ppp.domain.BaseDomain;
import kaitou.ppp.domain.system.SysCode;
import org.apache.commons.lang.StringUtils;

/**
 * 零件备库管理.
 * User: 赵立伟
 * Date: 2015/5/17
 * Time: 22:05
 */
public class PartsLibrary extends BaseDomain {
    /**
     * 年份
     */
    private String numberOfYear;
    /**
     * 月份
     */
    private String numberOfMonth;
    /**
     * 产品线
     */
    private String productLine;
    /**
     * 四区
     *
     * @see kaitou.ppp.domain.system.SysCode.SaleRegion
     */
    private String saleRegion;
    /**
     * CAMG Code
     */
    private String camgCode;
    /**
     * EO单号
     */
    private String eoOrMeOrderNo;
    /**
     * 认定店编号
     */
    private String shopId;
    /**
     * 认定店名称
     */
    private String shopName;
    /**
     * 零件编号
     */
    private String partNo;
    /**
     * 零件名称
     */
    private String productName;
    /**
     * 数量
     */
    private String amount;
    /**
     * 成本
     */
    private String cost;
    /**
     * 价格
     */
    private String price;
    /**
     * 备注
     */
    private String note;

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
        return "PartsLibrary{" +
                "numberOfYear='" + numberOfYear + '\'' +
                ", numberOfMonth='" + numberOfMonth + '\'' +
                ", productLine='" + productLine + '\'' +
                ", saleRegion='" + saleRegion + '\'' +
                ", camgCode='" + camgCode + '\'' +
                ", eoOrMeOrderNo='" + eoOrMeOrderNo + '\'' +
                ", shopId='" + shopId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", partNo='" + partNo + '\'' +
                ", productName='" + productName + '\'' +
                ", amount='" + amount + '\'' +
                ", cost='" + cost + '\'' +
                ", price='" + price + '\'' +
                ", note='" + note + '\'' +
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

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getSaleRegion() {
        return SysCode.SaleRegion.convert2Value(saleRegion);
    }

    public void setSaleRegion(String saleRegion) {
        this.saleRegion = saleRegion;
    }

    public String getCamgCode() {
        return camgCode;
    }

    public void setCamgCode(String camgCode) {
        this.camgCode = camgCode;
    }

    public String getEoOrMeOrderNo() {
        return eoOrMeOrderNo;
    }

    public void setEoOrMeOrderNo(String eoOrMeOrderNo) {
        this.eoOrMeOrderNo = eoOrMeOrderNo;
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

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
