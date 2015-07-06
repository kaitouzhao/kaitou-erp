package kaitou.ppp.domain.ts;

import kaitou.ppp.domain.BaseDomain;
import org.apache.commons.lang.StringUtils;

import static kaitou.ppp.common.utils.NumberUtil.toInt;

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
    private String serialNumber;
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
     * 员工号
     */
    private String employeeNo;
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

    /**
     * 序号比较器
     *
     * @param o 比较对象
     * @return 正序
     */
    public int comparatorBySerialNumber(ComponentBorrowing o) {
        return toInt(serialNumber).compareTo(toInt(o.getSerialNumber()));
    }

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
                "serialNumber='" + serialNumber + '\'' +
                ", numberOfYear='" + numberOfYear + '\'' +
                ", numberOfMonth='" + numberOfMonth + '\'' +
                ", borrowingDate='" + borrowingDate + '\'' +
                ", insideOrOutside='" + insideOrOutside + '\'' +
                ", borrowingRegion='" + borrowingRegion + '\'' +
                ", borrowingCompany='" + borrowingCompany + '\'' +
                ", employeeNo='" + employeeNo + '\'' +
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

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
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
