package kaitou.ppp.domain.ts;

import kaitou.ppp.domain.BaseDomain;
import org.apache.commons.lang.StringUtils;

/**
 * 零件借用.
 * User: 赵立伟
 * Date: 2015/5/21
 * Time: 14:37
 */
public class ComponentBorrowing extends BaseDomain {
    /**
     * 序号
     */
    private String serialNo;
    /**
     * 年份
     */
    private String numberOfYear;
    /**
     * 月份
     */
    private String numberOfMonth;
    /**
     * 具体借用日期
     */
    private String borrowingDate;
    /**
     * 内部/外部
     */
    private String insideOrOutside;
    /**
     * 借用区域
     */
    private String borrowingRegion;
    /**
     * 借用公司
     */
    private String borrowingCompany;
    /**
     * 借用人
     */
    private String borrowingPerson;
    /**
     * 产品线
     */
    private String productLine;
    /**
     * 机型
     */
    private String model;
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
     * 拆件工程师
     */
    private String piecemealEngineerName;
    /**
     * 零件出处
     */
    private String partFrom;
    /**
     * 设备是否恢复
     */
    private String equipmentRecoveryOrNot;
    /**
     * 订单信息
     */
    private String orderInfo;
    /**
     * 归还日期
     */
    private String returnDate;
    /**
     * 零件中心担当联系人
     */
    private String partCenterLinkman;
    /**
     * 发货地点
     */
    private String deliverySite;
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
        return "ComponentBorrowing{" +
                "serialNo='" + serialNo + '\'' +
                ", numberOfYear='" + numberOfYear + '\'' +
                ", numberOfMonth='" + numberOfMonth + '\'' +
                ", borrowingDate='" + borrowingDate + '\'' +
                ", insideOrOutside='" + insideOrOutside + '\'' +
                ", borrowingRegion='" + borrowingRegion + '\'' +
                ", borrowingCompany='" + borrowingCompany + '\'' +
                ", borrowingPerson='" + borrowingPerson + '\'' +
                ", productLine='" + productLine + '\'' +
                ", model='" + model + '\'' +
                ", partNo='" + partNo + '\'' +
                ", productName='" + productName + '\'' +
                ", amount='" + amount + '\'' +
                ", piecemealEngineerName='" + piecemealEngineerName + '\'' +
                ", partFrom='" + partFrom + '\'' +
                ", equipmentRecoveryOrNot='" + equipmentRecoveryOrNot + '\'' +
                ", orderInfo='" + orderInfo + '\'' +
                ", returnDate='" + returnDate + '\'' +
                ", partCenterLinkman='" + partCenterLinkman + '\'' +
                ", deliverySite='" + deliverySite + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComponentBorrowing that = (ComponentBorrowing) o;

        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (borrowingCompany != null ? !borrowingCompany.equals(that.borrowingCompany) : that.borrowingCompany != null)
            return false;
        if (borrowingDate != null ? !borrowingDate.equals(that.borrowingDate) : that.borrowingDate != null)
            return false;
        if (borrowingPerson != null ? !borrowingPerson.equals(that.borrowingPerson) : that.borrowingPerson != null)
            return false;
        if (borrowingRegion != null ? !borrowingRegion.equals(that.borrowingRegion) : that.borrowingRegion != null)
            return false;
        if (deliverySite != null ? !deliverySite.equals(that.deliverySite) : that.deliverySite != null) return false;
        if (equipmentRecoveryOrNot != null ? !equipmentRecoveryOrNot.equals(that.equipmentRecoveryOrNot) : that.equipmentRecoveryOrNot != null)
            return false;
        if (insideOrOutside != null ? !insideOrOutside.equals(that.insideOrOutside) : that.insideOrOutside != null)
            return false;
        if (model != null ? !model.equals(that.model) : that.model != null) return false;
        if (note != null ? !note.equals(that.note) : that.note != null) return false;
        if (numberOfMonth != null ? !numberOfMonth.equals(that.numberOfMonth) : that.numberOfMonth != null)
            return false;
        if (numberOfYear != null ? !numberOfYear.equals(that.numberOfYear) : that.numberOfYear != null) return false;
        if (orderInfo != null ? !orderInfo.equals(that.orderInfo) : that.orderInfo != null) return false;
        if (partCenterLinkman != null ? !partCenterLinkman.equals(that.partCenterLinkman) : that.partCenterLinkman != null)
            return false;
        if (partFrom != null ? !partFrom.equals(that.partFrom) : that.partFrom != null) return false;
        if (partNo != null ? !partNo.equals(that.partNo) : that.partNo != null) return false;
        if (piecemealEngineerName != null ? !piecemealEngineerName.equals(that.piecemealEngineerName) : that.piecemealEngineerName != null)
            return false;
        if (productLine != null ? !productLine.equals(that.productLine) : that.productLine != null) return false;
        if (productName != null ? !productName.equals(that.productName) : that.productName != null) return false;
        if (returnDate != null ? !returnDate.equals(that.returnDate) : that.returnDate != null) return false;
        if (serialNo != null ? !serialNo.equals(that.serialNo) : that.serialNo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = serialNo != null ? serialNo.hashCode() : 0;
        result = 31 * result + (numberOfYear != null ? numberOfYear.hashCode() : 0);
        result = 31 * result + (numberOfMonth != null ? numberOfMonth.hashCode() : 0);
        result = 31 * result + (borrowingDate != null ? borrowingDate.hashCode() : 0);
        result = 31 * result + (insideOrOutside != null ? insideOrOutside.hashCode() : 0);
        result = 31 * result + (borrowingRegion != null ? borrowingRegion.hashCode() : 0);
        result = 31 * result + (borrowingCompany != null ? borrowingCompany.hashCode() : 0);
        result = 31 * result + (borrowingPerson != null ? borrowingPerson.hashCode() : 0);
        result = 31 * result + (productLine != null ? productLine.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (partNo != null ? partNo.hashCode() : 0);
        result = 31 * result + (productName != null ? productName.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (piecemealEngineerName != null ? piecemealEngineerName.hashCode() : 0);
        result = 31 * result + (partFrom != null ? partFrom.hashCode() : 0);
        result = 31 * result + (equipmentRecoveryOrNot != null ? equipmentRecoveryOrNot.hashCode() : 0);
        result = 31 * result + (orderInfo != null ? orderInfo.hashCode() : 0);
        result = 31 * result + (returnDate != null ? returnDate.hashCode() : 0);
        result = 31 * result + (partCenterLinkman != null ? partCenterLinkman.hashCode() : 0);
        result = 31 * result + (deliverySite != null ? deliverySite.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        return result;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
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

    public String getBorrowingDate() {
        return borrowingDate;
    }

    public void setBorrowingDate(String borrowingDate) {
        this.borrowingDate = borrowingDate;
    }

    public String getInsideOrOutside() {
        return insideOrOutside;
    }

    public void setInsideOrOutside(String insideOrOutside) {
        this.insideOrOutside = insideOrOutside;
    }

    public String getBorrowingRegion() {
        return borrowingRegion;
    }

    public void setBorrowingRegion(String borrowingRegion) {
        this.borrowingRegion = borrowingRegion;
    }

    public String getBorrowingCompany() {
        return borrowingCompany;
    }

    public void setBorrowingCompany(String borrowingCompany) {
        this.borrowingCompany = borrowingCompany;
    }

    public String getBorrowingPerson() {
        return borrowingPerson;
    }

    public void setBorrowingPerson(String borrowingPerson) {
        this.borrowingPerson = borrowingPerson;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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

    public String getPiecemealEngineerName() {
        return piecemealEngineerName;
    }

    public void setPiecemealEngineerName(String piecemealEngineerName) {
        this.piecemealEngineerName = piecemealEngineerName;
    }

    public String getPartFrom() {
        return partFrom;
    }

    public void setPartFrom(String partFrom) {
        this.partFrom = partFrom;
    }

    public String getEquipmentRecoveryOrNot() {
        return equipmentRecoveryOrNot;
    }

    public void setEquipmentRecoveryOrNot(String equipmentRecoveryOrNot) {
        this.equipmentRecoveryOrNot = equipmentRecoveryOrNot;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getPartCenterLinkman() {
        return partCenterLinkman;
    }

    public void setPartCenterLinkman(String partCenterLinkman) {
        this.partCenterLinkman = partCenterLinkman;
    }

    public String getDeliverySite() {
        return deliverySite;
    }

    public void setDeliverySite(String deliverySite) {
        this.deliverySite = deliverySite;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
