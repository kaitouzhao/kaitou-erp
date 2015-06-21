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
