package kaitou.ppp.domain.warranty;

import kaitou.ppp.domain.BaseDomain;
import kaitou.ppp.domain.system.SysCode;
import org.apache.commons.lang.StringUtils;

/**
 * 保修零件及索赔零件.
 * User: 赵立伟
 * Date: 2015/5/10
 * Time: 15:22
 */
public class WarrantyParts extends BaseDomain {
    /**
     * 年份
     */
    private String numberOfYear;
    /**
     * 月份
     */
    private String numberOfMonth;
    /**
     * 费用承担部门
     */
    @Deprecated
    private String expenseDepartment;
    /**
     * 费用承担区域
     */
    @Deprecated
    private String expenseRegion;
    /**
     * 维修站所属销售区域
     *
     * @see kaitou.ppp.domain.system.SysCode.SaleRegion
     */
    private String saleRegion;
    /**
     * 产品线
     */
    private String productLine;
    /**
     * 结账类型
     */
    private String payType;
    /**
     * 保修类型
     */
    @Deprecated
    private String warrantyType;
    /**
     * 订单类型
     *
     * @see kaitou.ppp.domain.system.SysCode.WarrantyOrderType
     */
    private String warrantyOrderType;
    /**
     * Exp Class
     */
    @Deprecated
    private String expClass;
    /**
     * Exp P/Div
     */
    @Deprecated
    private String expPDiv;
    /**
     * Selling Division
     */
    private String sellingDivision;
    /**
     * EO订单号/ME管理号
     */
    private String eoOrMeOrderNo;
    /**
     * 发票日期
     */
    @Deprecated
    private String invoiceDate;
    /**
     * RAMBO订单号
     */
    @Deprecated
    private String ramboOrderNo;
    /**
     * RAMBO发票号
     */
    @Deprecated
    private String ramboInvoiceNo;
    /**
     * 认定店公司代码
     */
    private String shopCompanyCode;
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
     * SIZE
     */
    @Deprecated
    private String size;
    /**
     * DIV
     */
    @Deprecated
    private String div;
    /**
     * CAMG
     */
    @Deprecated
    private String camg;
    /**
     * 零件名称
     */
    private String productName;
    /**
     * 系统机型
     */
    @Deprecated
    private String sysModel;
    /**
     * 单位成本
     */
    @Deprecated
    private String costPerUnit;
    /**
     * 数量
     */
    private String deliveryNumber;
    /**
     * 税前合计
     */
    @Deprecated
    private String totalPreTax;
    /**
     * 税金
     */
    @Deprecated
    private String tax;
    /**
     * 金额（税后合计）
     */
    private String totalAfterTax;
    /**
     * 订单提交日期
     */
    @Deprecated
    private String orderSubmitDate;
    /**
     * 最终用户名称
     */
    private String userCompanyName;
    /**
     * 联系人
     */
    private String userLinkMan;
    /**
     * 电话
     */
    private String userContact;
    /**
     * 购买日期
     */
    private String purchaseDate;
    /**
     * 机型
     */
    private String model;
    /**
     * 机身号
     */
    private String fuselage;
    /**
     * 维修单号
     */
    private String repairOrder;
    /**
     * 故障现象
     */
    private String faultPhenomenon;
    /**
     * class
     */
    @Deprecated
    private String classNo;
    /**
     * DIV
     */
    @Deprecated
    private String div1;
    /**
     * CAMG
     */
    @Deprecated
    private String camg1;
    /**
     * ICP特殊
     */
    @Deprecated
    private String icpSpecial;
    /**
     * ICP特殊税
     */
    @Deprecated
    private String icpSpecialTax;
    /**
     * ICP特殊合计
     */
    @Deprecated
    private String icpSpecialTotal;
    /**
     * A
     */
    @Deprecated
    private String a;
    /**
     * CRS
     */
    @Deprecated
    private String crs;
    /**
     * 类别
     */
    @Deprecated
    private String category;
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
        return SysCode.DB_FILE_NAME_SPLIT + getClass().getSimpleName() + DB_SUFFIX;
    }

    @Override
    public String toString() {
        return "WarrantyParts{" +
                "numberOfYear='" + numberOfYear + '\'' +
                ", numberOfMonth='" + numberOfMonth + '\'' +
                ", saleRegion='" + saleRegion + '\'' +
                ", productLine='" + productLine + '\'' +
                ", payType='" + payType + '\'' +
                ", warrantyOrderType='" + warrantyOrderType + '\'' +
                ", sellingDivision='" + sellingDivision + '\'' +
                ", eoOrMeOrderNo='" + eoOrMeOrderNo + '\'' +
                ", shopCompanyCode='" + shopCompanyCode + '\'' +
                ", shopId='" + shopId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", partNo='" + partNo + '\'' +
                ", productName='" + productName + '\'' +
                ", deliveryNumber='" + deliveryNumber + '\'' +
                ", totalAfterTax='" + totalAfterTax + '\'' +
                ", userCompanyName='" + userCompanyName + '\'' +
                ", userLinkMan='" + userLinkMan + '\'' +
                ", purchaseDate='" + purchaseDate + '\'' +
                ", userContact='" + userContact + '\'' +
                ", model='" + model + '\'' +
                ", fuselage='" + fuselage + '\'' +
                ", repairOrder='" + repairOrder + '\'' +
                ", faultPhenomenon='" + faultPhenomenon + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getSellingDivision() {
        return sellingDivision;
    }

    public void setSellingDivision(String sellingDivision) {
        this.sellingDivision = sellingDivision;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public String getExpenseDepartment() {
        return expenseDepartment;
    }

    public void setExpenseDepartment(String expenseDepartment) {
        this.expenseDepartment = expenseDepartment;
    }

    public String getExpenseRegion() {
        return expenseRegion;
    }

    public void setExpenseRegion(String expenseRegion) {
        this.expenseRegion = expenseRegion;
    }

    public String getSaleRegion() {
        return SysCode.SaleRegion.convert2Value(saleRegion);
    }

    public void setSaleRegion(String saleRegion) {
        this.saleRegion = saleRegion;
    }

    public String getWarrantyType() {
        return warrantyType;
    }

    public void setWarrantyType(String warrantyType) {
        this.warrantyType = warrantyType;
    }

    public String getWarrantyOrderType() {
        return warrantyOrderType;
    }

    public void setWarrantyOrderType(String warrantyOrderType) {
        this.warrantyOrderType = warrantyOrderType;
    }

    public String getExpClass() {
        return expClass;
    }

    public void setExpClass(String expClass) {
        this.expClass = expClass;
    }

    public String getExpPDiv() {
        return expPDiv;
    }

    public void setExpPDiv(String expPDiv) {
        this.expPDiv = expPDiv;
    }

    public String getEoOrMeOrderNo() {
        return eoOrMeOrderNo;
    }

    public void setEoOrMeOrderNo(String eoOrMeOrderNo) {
        this.eoOrMeOrderNo = eoOrMeOrderNo;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getRamboOrderNo() {
        return ramboOrderNo;
    }

    public void setRamboOrderNo(String ramboOrderNo) {
        this.ramboOrderNo = ramboOrderNo;
    }

    public String getRamboInvoiceNo() {
        return ramboInvoiceNo;
    }

    public void setRamboInvoiceNo(String ramboInvoiceNo) {
        this.ramboInvoiceNo = ramboInvoiceNo;
    }

    public String getShopCompanyCode() {
        return shopCompanyCode;
    }

    public void setShopCompanyCode(String shopCompanyCode) {
        this.shopCompanyCode = shopCompanyCode;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDiv() {
        return div;
    }

    public void setDiv(String div) {
        this.div = div;
    }

    public String getCamg() {
        return camg;
    }

    public void setCamg(String camg) {
        this.camg = camg;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSysModel() {
        return sysModel;
    }

    public void setSysModel(String sysModel) {
        this.sysModel = sysModel;
    }

    public String getCostPerUnit() {
        return costPerUnit;
    }

    public void setCostPerUnit(String costPerUnit) {
        this.costPerUnit = costPerUnit;
    }

    public String getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(String deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }

    public String getTotalPreTax() {
        return totalPreTax;
    }

    public void setTotalPreTax(String totalPreTax) {
        this.totalPreTax = totalPreTax;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getTotalAfterTax() {
        return totalAfterTax;
    }

    public void setTotalAfterTax(String totalAfterTax) {
        this.totalAfterTax = totalAfterTax;
    }

    public String getOrderSubmitDate() {
        return orderSubmitDate;
    }

    public void setOrderSubmitDate(String orderSubmitDate) {
        this.orderSubmitDate = orderSubmitDate;
    }

    public String getUserCompanyName() {
        return userCompanyName;
    }

    public void setUserCompanyName(String userCompanyName) {
        this.userCompanyName = userCompanyName;
    }

    public String getUserLinkMan() {
        return userLinkMan;
    }

    public void setUserLinkMan(String userLinkMan) {
        this.userLinkMan = userLinkMan;
    }

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
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

    public String getRepairOrder() {
        return repairOrder;
    }

    public void setRepairOrder(String repairOrder) {
        this.repairOrder = repairOrder;
    }

    public String getFaultPhenomenon() {
        return faultPhenomenon;
    }

    public void setFaultPhenomenon(String faultPhenomenon) {
        this.faultPhenomenon = faultPhenomenon;
    }

    public String getClassNo() {
        return classNo;
    }

    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }

    public String getDiv1() {
        return div1;
    }

    public void setDiv1(String div1) {
        this.div1 = div1;
    }

    public String getCamg1() {
        return camg1;
    }

    public void setCamg1(String camg1) {
        this.camg1 = camg1;
    }

    public String getIcpSpecial() {
        return icpSpecial;
    }

    public void setIcpSpecial(String icpSpecial) {
        this.icpSpecial = icpSpecial;
    }

    public String getIcpSpecialTax() {
        return icpSpecialTax;
    }

    public void setIcpSpecialTax(String icpSpecialTax) {
        this.icpSpecialTax = icpSpecialTax;
    }

    public String getIcpSpecialTotal() {
        return icpSpecialTotal;
    }

    public void setIcpSpecialTotal(String icpSpecialTotal) {
        this.icpSpecialTotal = icpSpecialTotal;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getCrs() {
        return crs;
    }

    public void setCrs(String crs) {
        this.crs = crs;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
