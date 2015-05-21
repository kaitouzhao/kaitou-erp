package kaitou.ppp.domain.ts;

import kaitou.ppp.domain.BaseDomain;
import kaitou.ppp.domain.system.SysCode;
import org.apache.commons.lang.StringUtils;

/**
 * 新装机索赔.
 * User: 赵立伟
 * Date: 2015/5/21
 * Time: 11:45
 */
public class NewMachineClaim extends BaseDomain {
    /**
     * 年份
     */
    private String numberOfYear;
    /**
     * 月份
     */
    private String numberOfMonth;
    /**
     * 申请时间
     */
    private String applyDate;
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
     * 机型分类
     */
    private String modelType;
    /**
     * 机型
     */
    private String model;
    /**
     * 机身号
     */
    private String fuselage;
    /**
     * 客户名称
     */
    private String userCompanyName;
    /**
     * 维修工程师
     */
    private String maintenanceEngineerName;
    /**
     * 零件编号
     */
    private String partNo;
    /**
     * 单价
     */
    private String price;
    /**
     * 数量
     */
    private String amount;
    /**
     * 总价
     */
    private String totalPrice;
    /**
     * 內领原因
     */
    private String fetchReason;
    /**
     * 领导确认
     */
    private String managerConfirm;
    /**
     * 邮件同意时间
     */
    private String confirmDate;
    /**
     * 财务编号
     */
    private String financialNo;
    /**
     * 部门编号
     */
    private String departmentNo;
    /**
     * 费用编号
     */
    private String feeNo;
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
        return "NewMachineClaim{" +
                "numberOfYear='" + numberOfYear + '\'' +
                ", numberOfMonth='" + numberOfMonth + '\'' +
                ", applyDate='" + applyDate + '\'' +
                ", saleRegion='" + saleRegion + '\'' +
                ", productLine='" + productLine + '\'' +
                ", modelType='" + modelType + '\'' +
                ", model='" + model + '\'' +
                ", fuselage='" + fuselage + '\'' +
                ", userCompanyName='" + userCompanyName + '\'' +
                ", maintenanceEngineerName='" + maintenanceEngineerName + '\'' +
                ", partNo='" + partNo + '\'' +
                ", price='" + price + '\'' +
                ", amount='" + amount + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                ", fetchReason='" + fetchReason + '\'' +
                ", managerConfirm='" + managerConfirm + '\'' +
                ", confirmDate='" + confirmDate + '\'' +
                ", financialNo='" + financialNo + '\'' +
                ", departmentNo='" + departmentNo + '\'' +
                ", feeNo='" + feeNo + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewMachineClaim that = (NewMachineClaim) o;

        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (applyDate != null ? !applyDate.equals(that.applyDate) : that.applyDate != null) return false;
        if (confirmDate != null ? !confirmDate.equals(that.confirmDate) : that.confirmDate != null) return false;
        if (departmentNo != null ? !departmentNo.equals(that.departmentNo) : that.departmentNo != null) return false;
        if (feeNo != null ? !feeNo.equals(that.feeNo) : that.feeNo != null) return false;
        if (fetchReason != null ? !fetchReason.equals(that.fetchReason) : that.fetchReason != null) return false;
        if (financialNo != null ? !financialNo.equals(that.financialNo) : that.financialNo != null) return false;
        if (fuselage != null ? !fuselage.equals(that.fuselage) : that.fuselage != null) return false;
        if (maintenanceEngineerName != null ? !maintenanceEngineerName.equals(that.maintenanceEngineerName) : that.maintenanceEngineerName != null)
            return false;
        if (managerConfirm != null ? !managerConfirm.equals(that.managerConfirm) : that.managerConfirm != null)
            return false;
        if (model != null ? !model.equals(that.model) : that.model != null) return false;
        if (modelType != null ? !modelType.equals(that.modelType) : that.modelType != null) return false;
        if (note != null ? !note.equals(that.note) : that.note != null) return false;
        if (numberOfMonth != null ? !numberOfMonth.equals(that.numberOfMonth) : that.numberOfMonth != null)
            return false;
        if (numberOfYear != null ? !numberOfYear.equals(that.numberOfYear) : that.numberOfYear != null) return false;
        if (partNo != null ? !partNo.equals(that.partNo) : that.partNo != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (productLine != null ? !productLine.equals(that.productLine) : that.productLine != null) return false;
        if (saleRegion != null ? !saleRegion.equals(that.saleRegion) : that.saleRegion != null) return false;
        if (totalPrice != null ? !totalPrice.equals(that.totalPrice) : that.totalPrice != null) return false;
        if (userCompanyName != null ? !userCompanyName.equals(that.userCompanyName) : that.userCompanyName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = numberOfYear != null ? numberOfYear.hashCode() : 0;
        result = 31 * result + (numberOfMonth != null ? numberOfMonth.hashCode() : 0);
        result = 31 * result + (applyDate != null ? applyDate.hashCode() : 0);
        result = 31 * result + (saleRegion != null ? saleRegion.hashCode() : 0);
        result = 31 * result + (productLine != null ? productLine.hashCode() : 0);
        result = 31 * result + (modelType != null ? modelType.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (fuselage != null ? fuselage.hashCode() : 0);
        result = 31 * result + (userCompanyName != null ? userCompanyName.hashCode() : 0);
        result = 31 * result + (maintenanceEngineerName != null ? maintenanceEngineerName.hashCode() : 0);
        result = 31 * result + (partNo != null ? partNo.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (totalPrice != null ? totalPrice.hashCode() : 0);
        result = 31 * result + (fetchReason != null ? fetchReason.hashCode() : 0);
        result = 31 * result + (managerConfirm != null ? managerConfirm.hashCode() : 0);
        result = 31 * result + (confirmDate != null ? confirmDate.hashCode() : 0);
        result = 31 * result + (financialNo != null ? financialNo.hashCode() : 0);
        result = 31 * result + (departmentNo != null ? departmentNo.hashCode() : 0);
        result = 31 * result + (feeNo != null ? feeNo.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        return result;
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

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
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

    public String getUserCompanyName() {
        return userCompanyName;
    }

    public void setUserCompanyName(String userCompanyName) {
        this.userCompanyName = userCompanyName;
    }

    public String getMaintenanceEngineerName() {
        return maintenanceEngineerName;
    }

    public void setMaintenanceEngineerName(String maintenanceEngineerName) {
        this.maintenanceEngineerName = maintenanceEngineerName;
    }

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getFetchReason() {
        return fetchReason;
    }

    public void setFetchReason(String fetchReason) {
        this.fetchReason = fetchReason;
    }

    public String getManagerConfirm() {
        return managerConfirm;
    }

    public void setManagerConfirm(String managerConfirm) {
        this.managerConfirm = managerConfirm;
    }

    public String getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getFinancialNo() {
        return financialNo;
    }

    public void setFinancialNo(String financialNo) {
        this.financialNo = financialNo;
    }

    public String getDepartmentNo() {
        return departmentNo;
    }

    public void setDepartmentNo(String departmentNo) {
        this.departmentNo = departmentNo;
    }

    public String getFeeNo() {
        return feeNo;
    }

    public void setFeeNo(String feeNo) {
        this.feeNo = feeNo;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSaleRegion() {
        return SysCode.SaleRegion.convert2Value(saleRegion);
    }

    public void setSaleRegion(String saleRegion) {
        this.saleRegion = saleRegion;
    }

}
