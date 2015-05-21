package kaitou.ppp.domain.tech;

import kaitou.ppp.domain.BaseDomain;
import org.apache.commons.lang.StringUtils;

/**
 * 技术支援.
 * User: 赵立伟
 * Date: 2015/5/19
 * Time: 18:00
 */
public class TechSupport extends BaseDomain {
    /**
     * 年份
     */
    private String numberOfYear;
    /**
     * 支援管理号
     */
    private String supportManagementNo;
    /**
     * 产品线
     */
    private String productLine;
    /**
     * 区域支援日期
     */
    private String regionSupportDate;
    /**
     * 支援类型
     */
    private String supportType;
    /**
     * 支援认定店编号
     */
    private String shopId;
    /**
     * 支援认定店名称
     */
    private String shopName;
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
     * 服务费
     */
    private String serviceFee;
    /**
     * 差旅费
     */
    private String travelFee;
    /**
     * 支援工程师
     */
    private String supporter;
    /**
     * 支援原因
     */
    private String supportReason;
    /**
     * 是否发邮件
     */
    private String sendEmail;
    /**
     * 是否已经付费
     */
    private String alreadyPay;
    /**
     * 是否已开票
     */
    private String alreadyInvoice;
    /**
     * 跟进状态
     */
    private String followStatus;
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
        return "TechSupport{" +
                "numberOfYear='" + numberOfYear + '\'' +
                ", supportManagementNo='" + supportManagementNo + '\'' +
                ", productLine='" + productLine + '\'' +
                ", regionSupportDate='" + regionSupportDate + '\'' +
                ", supportType='" + supportType + '\'' +
                ", shopId='" + shopId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", modelType='" + modelType + '\'' +
                ", model='" + model + '\'' +
                ", fuselage='" + fuselage + '\'' +
                ", serviceFee='" + serviceFee + '\'' +
                ", travelFee='" + travelFee + '\'' +
                ", supporter='" + supporter + '\'' +
                ", supportReason='" + supportReason + '\'' +
                ", sendEmail='" + sendEmail + '\'' +
                ", alreadyPay='" + alreadyPay + '\'' +
                ", alreadyInvoice='" + alreadyInvoice + '\'' +
                ", followStatus='" + followStatus + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TechSupport that = (TechSupport) o;

        if (alreadyInvoice != null ? !alreadyInvoice.equals(that.alreadyInvoice) : that.alreadyInvoice != null)
            return false;
        if (alreadyPay != null ? !alreadyPay.equals(that.alreadyPay) : that.alreadyPay != null) return false;
        if (followStatus != null ? !followStatus.equals(that.followStatus) : that.followStatus != null) return false;
        if (fuselage != null ? !fuselage.equals(that.fuselage) : that.fuselage != null) return false;
        if (model != null ? !model.equals(that.model) : that.model != null) return false;
        if (modelType != null ? !modelType.equals(that.modelType) : that.modelType != null) return false;
        if (note != null ? !note.equals(that.note) : that.note != null) return false;
        if (numberOfYear != null ? !numberOfYear.equals(that.numberOfYear) : that.numberOfYear != null) return false;
        if (productLine != null ? !productLine.equals(that.productLine) : that.productLine != null) return false;
        if (regionSupportDate != null ? !regionSupportDate.equals(that.regionSupportDate) : that.regionSupportDate != null)
            return false;
        if (sendEmail != null ? !sendEmail.equals(that.sendEmail) : that.sendEmail != null) return false;
        if (serviceFee != null ? !serviceFee.equals(that.serviceFee) : that.serviceFee != null) return false;
        if (shopId != null ? !shopId.equals(that.shopId) : that.shopId != null) return false;
        if (shopName != null ? !shopName.equals(that.shopName) : that.shopName != null) return false;
        if (supportManagementNo != null ? !supportManagementNo.equals(that.supportManagementNo) : that.supportManagementNo != null)
            return false;
        if (supportReason != null ? !supportReason.equals(that.supportReason) : that.supportReason != null)
            return false;
        if (supportType != null ? !supportType.equals(that.supportType) : that.supportType != null) return false;
        if (supporter != null ? !supporter.equals(that.supporter) : that.supporter != null) return false;
        if (travelFee != null ? !travelFee.equals(that.travelFee) : that.travelFee != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = numberOfYear != null ? numberOfYear.hashCode() : 0;
        result = 31 * result + (supportManagementNo != null ? supportManagementNo.hashCode() : 0);
        result = 31 * result + (productLine != null ? productLine.hashCode() : 0);
        result = 31 * result + (regionSupportDate != null ? regionSupportDate.hashCode() : 0);
        result = 31 * result + (supportType != null ? supportType.hashCode() : 0);
        result = 31 * result + (shopId != null ? shopId.hashCode() : 0);
        result = 31 * result + (shopName != null ? shopName.hashCode() : 0);
        result = 31 * result + (modelType != null ? modelType.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (fuselage != null ? fuselage.hashCode() : 0);
        result = 31 * result + (serviceFee != null ? serviceFee.hashCode() : 0);
        result = 31 * result + (travelFee != null ? travelFee.hashCode() : 0);
        result = 31 * result + (supporter != null ? supporter.hashCode() : 0);
        result = 31 * result + (supportReason != null ? supportReason.hashCode() : 0);
        result = 31 * result + (sendEmail != null ? sendEmail.hashCode() : 0);
        result = 31 * result + (alreadyPay != null ? alreadyPay.hashCode() : 0);
        result = 31 * result + (alreadyInvoice != null ? alreadyInvoice.hashCode() : 0);
        result = 31 * result + (followStatus != null ? followStatus.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        return result;
    }

    public String getNumberOfYear() {
        return numberOfYear;
    }

    public void setNumberOfYear(String numberOfYear) {
        this.numberOfYear = numberOfYear;
    }

    public String getSupportManagementNo() {
        return supportManagementNo;
    }

    public void setSupportManagementNo(String supportManagementNo) {
        this.supportManagementNo = supportManagementNo;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getRegionSupportDate() {
        return regionSupportDate;
    }

    public void setRegionSupportDate(String regionSupportDate) {
        this.regionSupportDate = regionSupportDate;
    }

    public String getSupportType() {
        return supportType;
    }

    public void setSupportType(String supportType) {
        this.supportType = supportType;
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

    public String getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(String serviceFee) {
        this.serviceFee = serviceFee;
    }

    public String getTravelFee() {
        return travelFee;
    }

    public void setTravelFee(String travelFee) {
        this.travelFee = travelFee;
    }

    public String getSupporter() {
        return supporter;
    }

    public void setSupporter(String supporter) {
        this.supporter = supporter;
    }

    public String getSupportReason() {
        return supportReason;
    }

    public void setSupportReason(String supportReason) {
        this.supportReason = supportReason;
    }

    public String getSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(String sendEmail) {
        this.sendEmail = sendEmail;
    }

    public String getAlreadyPay() {
        return alreadyPay;
    }

    public void setAlreadyPay(String alreadyPay) {
        this.alreadyPay = alreadyPay;
    }

    public String getAlreadyInvoice() {
        return alreadyInvoice;
    }

    public void setAlreadyInvoice(String alreadyInvoice) {
        this.alreadyInvoice = alreadyInvoice;
    }

    public String getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(String followStatus) {
        this.followStatus = followStatus;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
