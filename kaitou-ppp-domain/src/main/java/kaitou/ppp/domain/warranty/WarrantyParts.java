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
        return '_' + getClass().getSimpleName() + DB_SUFFIX;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WarrantyParts that = (WarrantyParts) o;

        if (a != null ? !a.equals(that.a) : that.a != null) return false;
        if (camg != null ? !camg.equals(that.camg) : that.camg != null) return false;
        if (camg1 != null ? !camg1.equals(that.camg1) : that.camg1 != null) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (classNo != null ? !classNo.equals(that.classNo) : that.classNo != null) return false;
        if (costPerUnit != null ? !costPerUnit.equals(that.costPerUnit) : that.costPerUnit != null) return false;
        if (crs != null ? !crs.equals(that.crs) : that.crs != null) return false;
        if (deliveryNumber != null ? !deliveryNumber.equals(that.deliveryNumber) : that.deliveryNumber != null)
            return false;
        if (div != null ? !div.equals(that.div) : that.div != null) return false;
        if (div1 != null ? !div1.equals(that.div1) : that.div1 != null) return false;
        if (eoOrMeOrderNo != null ? !eoOrMeOrderNo.equals(that.eoOrMeOrderNo) : that.eoOrMeOrderNo != null)
            return false;
        if (expClass != null ? !expClass.equals(that.expClass) : that.expClass != null) return false;
        if (expPDiv != null ? !expPDiv.equals(that.expPDiv) : that.expPDiv != null) return false;
        if (expenseDepartment != null ? !expenseDepartment.equals(that.expenseDepartment) : that.expenseDepartment != null)
            return false;
        if (expenseRegion != null ? !expenseRegion.equals(that.expenseRegion) : that.expenseRegion != null)
            return false;
        if (faultPhenomenon != null ? !faultPhenomenon.equals(that.faultPhenomenon) : that.faultPhenomenon != null)
            return false;
        if (fuselage != null ? !fuselage.equals(that.fuselage) : that.fuselage != null) return false;
        if (icpSpecial != null ? !icpSpecial.equals(that.icpSpecial) : that.icpSpecial != null) return false;
        if (icpSpecialTax != null ? !icpSpecialTax.equals(that.icpSpecialTax) : that.icpSpecialTax != null)
            return false;
        if (icpSpecialTotal != null ? !icpSpecialTotal.equals(that.icpSpecialTotal) : that.icpSpecialTotal != null)
            return false;
        if (invoiceDate != null ? !invoiceDate.equals(that.invoiceDate) : that.invoiceDate != null) return false;
        if (model != null ? !model.equals(that.model) : that.model != null) return false;
        if (numberOfMonth != null ? !numberOfMonth.equals(that.numberOfMonth) : that.numberOfMonth != null)
            return false;
        if (numberOfYear != null ? !numberOfYear.equals(that.numberOfYear) : that.numberOfYear != null) return false;
        if (orderSubmitDate != null ? !orderSubmitDate.equals(that.orderSubmitDate) : that.orderSubmitDate != null)
            return false;
        if (partNo != null ? !partNo.equals(that.partNo) : that.partNo != null) return false;
        if (productName != null ? !productName.equals(that.productName) : that.productName != null) return false;
        if (purchaseDate != null ? !purchaseDate.equals(that.purchaseDate) : that.purchaseDate != null) return false;
        if (ramboInvoiceNo != null ? !ramboInvoiceNo.equals(that.ramboInvoiceNo) : that.ramboInvoiceNo != null)
            return false;
        if (ramboOrderNo != null ? !ramboOrderNo.equals(that.ramboOrderNo) : that.ramboOrderNo != null) return false;
        if (repairOrder != null ? !repairOrder.equals(that.repairOrder) : that.repairOrder != null) return false;
        if (saleRegion != null ? !saleRegion.equals(that.saleRegion) : that.saleRegion != null) return false;
        if (shopCompanyCode != null ? !shopCompanyCode.equals(that.shopCompanyCode) : that.shopCompanyCode != null)
            return false;
        if (shopId != null ? !shopId.equals(that.shopId) : that.shopId != null) return false;
        if (shopName != null ? !shopName.equals(that.shopName) : that.shopName != null) return false;
        if (size != null ? !size.equals(that.size) : that.size != null) return false;
        if (sysModel != null ? !sysModel.equals(that.sysModel) : that.sysModel != null) return false;
        if (tax != null ? !tax.equals(that.tax) : that.tax != null) return false;
        if (totalAfterTax != null ? !totalAfterTax.equals(that.totalAfterTax) : that.totalAfterTax != null)
            return false;
        if (totalPreTax != null ? !totalPreTax.equals(that.totalPreTax) : that.totalPreTax != null) return false;
        if (userCompanyName != null ? !userCompanyName.equals(that.userCompanyName) : that.userCompanyName != null)
            return false;
        if (userContact != null ? !userContact.equals(that.userContact) : that.userContact != null) return false;
        if (userLinkMan != null ? !userLinkMan.equals(that.userLinkMan) : that.userLinkMan != null) return false;
        if (warrantyOrderType != null ? !warrantyOrderType.equals(that.warrantyOrderType) : that.warrantyOrderType != null)
            return false;
        if (warrantyType != null ? !warrantyType.equals(that.warrantyType) : that.warrantyType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = numberOfYear != null ? numberOfYear.hashCode() : 0;
        result = 31 * result + (numberOfMonth != null ? numberOfMonth.hashCode() : 0);
        result = 31 * result + (expenseDepartment != null ? expenseDepartment.hashCode() : 0);
        result = 31 * result + (expenseRegion != null ? expenseRegion.hashCode() : 0);
        result = 31 * result + (saleRegion != null ? saleRegion.hashCode() : 0);
        result = 31 * result + (warrantyType != null ? warrantyType.hashCode() : 0);
        result = 31 * result + (warrantyOrderType != null ? warrantyOrderType.hashCode() : 0);
        result = 31 * result + (expClass != null ? expClass.hashCode() : 0);
        result = 31 * result + (expPDiv != null ? expPDiv.hashCode() : 0);
        result = 31 * result + (eoOrMeOrderNo != null ? eoOrMeOrderNo.hashCode() : 0);
        result = 31 * result + (invoiceDate != null ? invoiceDate.hashCode() : 0);
        result = 31 * result + (ramboOrderNo != null ? ramboOrderNo.hashCode() : 0);
        result = 31 * result + (ramboInvoiceNo != null ? ramboInvoiceNo.hashCode() : 0);
        result = 31 * result + (shopCompanyCode != null ? shopCompanyCode.hashCode() : 0);
        result = 31 * result + (shopId != null ? shopId.hashCode() : 0);
        result = 31 * result + (shopName != null ? shopName.hashCode() : 0);
        result = 31 * result + (partNo != null ? partNo.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (div != null ? div.hashCode() : 0);
        result = 31 * result + (camg != null ? camg.hashCode() : 0);
        result = 31 * result + (productName != null ? productName.hashCode() : 0);
        result = 31 * result + (sysModel != null ? sysModel.hashCode() : 0);
        result = 31 * result + (costPerUnit != null ? costPerUnit.hashCode() : 0);
        result = 31 * result + (deliveryNumber != null ? deliveryNumber.hashCode() : 0);
        result = 31 * result + (totalPreTax != null ? totalPreTax.hashCode() : 0);
        result = 31 * result + (tax != null ? tax.hashCode() : 0);
        result = 31 * result + (totalAfterTax != null ? totalAfterTax.hashCode() : 0);
        result = 31 * result + (orderSubmitDate != null ? orderSubmitDate.hashCode() : 0);
        result = 31 * result + (userCompanyName != null ? userCompanyName.hashCode() : 0);
        result = 31 * result + (userLinkMan != null ? userLinkMan.hashCode() : 0);
        result = 31 * result + (userContact != null ? userContact.hashCode() : 0);
        result = 31 * result + (purchaseDate != null ? purchaseDate.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (fuselage != null ? fuselage.hashCode() : 0);
        result = 31 * result + (repairOrder != null ? repairOrder.hashCode() : 0);
        result = 31 * result + (faultPhenomenon != null ? faultPhenomenon.hashCode() : 0);
        result = 31 * result + (classNo != null ? classNo.hashCode() : 0);
        result = 31 * result + (div1 != null ? div1.hashCode() : 0);
        result = 31 * result + (camg1 != null ? camg1.hashCode() : 0);
        result = 31 * result + (icpSpecial != null ? icpSpecial.hashCode() : 0);
        result = 31 * result + (icpSpecialTax != null ? icpSpecialTax.hashCode() : 0);
        result = 31 * result + (icpSpecialTotal != null ? icpSpecialTotal.hashCode() : 0);
        result = 31 * result + (a != null ? a.hashCode() : 0);
        result = 31 * result + (crs != null ? crs.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
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
