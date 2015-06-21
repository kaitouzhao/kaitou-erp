package kaitou.ppp.domain.ts;

import kaitou.ppp.domain.BaseDomain;
import kaitou.ppp.domain.system.SysCode;
import org.apache.commons.lang.StringUtils;

/**
 * 工具领用记录.
 * User: 赵立伟
 * Date: 2015/5/21
 * Time: 14:31
 */
public class ToolRecipients extends BaseDomain {
    /**
     * 年份
     */
    private String numberOfYear;
    /**
     * 月份
     */
    private String numberOfMonth;
    /**
     * 区域
     *
     * @see kaitou.ppp.domain.system.SysCode.SaleRegion
     */
    private String saleRegion;
    /**
     * 申请时间
     */
    private String applyDate;
    /**
     * 领用人
     */
    private String recipients;
    /**
     * 使用工程师姓名
     */
    private String useEngineerName;
    /**
     * 工具编号 （零件中心编号）
     */
    private String toolNo;
    /**
     * 工具名称
     */
    private String toolName;
    /**
     * 数量
     */
    private String amount;
    /**
     * 单价
     */
    private String price;
    /**
     * 金额
     */
    private String totalPrice;
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
        return "ToolRecipients{" +
                "numberOfYear='" + numberOfYear + '\'' +
                ", numberOfMonth='" + numberOfMonth + '\'' +
                ", applyDate='" + applyDate + '\'' +
                ", recipients='" + recipients + '\'' +
                ", useEngineerName='" + useEngineerName + '\'' +
                ", toolNo='" + toolNo + '\'' +
                ", toolName='" + toolName + '\'' +
                ", amount='" + amount + '\'' +
                ", price='" + price + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

    public String getSaleRegion() {
        return SysCode.SaleRegion.convert2Value(saleRegion);
    }

    public void setSaleRegion(String saleRegion) {
        this.saleRegion = saleRegion;
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

    public String getRecipients() {
        return recipients;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    public String getUseEngineerName() {
        return useEngineerName;
    }

    public void setUseEngineerName(String useEngineerName) {
        this.useEngineerName = useEngineerName;
    }

    public String getToolNo() {
        return toolNo;
    }

    public void setToolNo(String toolNo) {
        this.toolNo = toolNo;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
