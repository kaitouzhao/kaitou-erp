package kaitou.ppp.domain.warranty;

import kaitou.ppp.domain.BaseDomain;
import kaitou.ppp.domain.system.SysCode;
import org.apache.commons.lang.StringUtils;

/**
 * 耗材保修.
 * User: 赵立伟
 * Date: 2015/5/18
 * Time: 17:33
 */
public class WarrantyConsumables extends BaseDomain {
    /**
     * 年份
     */
    private String numberOfYear;
    /**
     * 季度
     */
    private String quarter;
    /**
     * 索赔编号
     */
    private String claimNo;
    /**
     * 申请日期
     */
    private String applyDate;
    /**
     * 资料提交日期
     */
    private String submitDate;
    /**
     * EO单号
     */
    private String eoOrMeOrderNo;
    /**
     * 区域
     *
     * @see kaitou.ppp.domain.system.SysCode.SaleRegion
     */
    private String saleRegion;
    /**
     * 索赔类型
     */
    private String claimType;
    /**
     * 认定店编号
     */
    private String shopId;
    /**
     * 认定店名称
     */
    private String shopName;
    /**
     * 认定店公司代码
     */
    private String shopCompanyCode;
    /**
     * 机型分类
     */
    private String modelType;
    /**
     * 机型
     */
    private String models;
    /**
     * 机身号
     */
    private String fuselage;
    /**
     * 零件编号
     */
    private String partNo;
    /**
     * 数量
     */
    private String amount;
    /**
     * 零件名称
     */
    private String productName;
    /**
     * 地址
     */
    private String address;
    /**
     * 收件人
     */
    private String recipient;
    /**
     * 邮编
     */
    private String postcode;
    /**
     * 电话
     */
    private String phone;
    /**
     * 申请索赔理由
     */
    private String claimReason;
    /**
     * 通过/拒绝
     */
    private String yesOrNo;
    /**
     * 通过/拒绝理由
     */
    private String yesOrNoReason;
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
        return "WarrantyConsumables{" +
                "numberOfYear='" + numberOfYear + '\'' +
                ", quarter='" + quarter + '\'' +
                ", claimNo='" + claimNo + '\'' +
                ", applyDate='" + applyDate + '\'' +
                ", submitDate='" + submitDate + '\'' +
                ", eoOrMeOrderNo='" + eoOrMeOrderNo + '\'' +
                ", saleRegion='" + saleRegion + '\'' +
                ", claimType='" + claimType + '\'' +
                ", shopId='" + shopId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", shopCompanyCode='" + shopCompanyCode + '\'' +
                ", modelType='" + modelType + '\'' +
                ", models='" + models + '\'' +
                ", fuselage='" + fuselage + '\'' +
                ", partNo='" + partNo + '\'' +
                ", amount='" + amount + '\'' +
                ", productName='" + productName + '\'' +
                ", address='" + address + '\'' +
                ", recipient='" + recipient + '\'' +
                ", postcode='" + postcode + '\'' +
                ", phone='" + phone + '\'' +
                ", claimReason='" + claimReason + '\'' +
                ", yesOrNo='" + yesOrNo + '\'' +
                ", yesOrNoReason='" + yesOrNoReason + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

    public String getNumberOfYear() {
        return numberOfYear;
    }

    public void setNumberOfYear(String numberOfYear) {
        this.numberOfYear = numberOfYear;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public String getClaimNo() {
        return claimNo;
    }

    public void setClaimNo(String claimNo) {
        this.claimNo = claimNo;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(String submitDate) {
        this.submitDate = submitDate;
    }

    public String getEoOrMeOrderNo() {
        return eoOrMeOrderNo;
    }

    public void setEoOrMeOrderNo(String eoOrMeOrderNo) {
        this.eoOrMeOrderNo = eoOrMeOrderNo;
    }

    public String getClaimType() {
        return claimType;
    }

    public void setClaimType(String claimType) {
        this.claimType = claimType;
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

    public String getShopCompanyCode() {
        return shopCompanyCode;
    }

    public void setShopCompanyCode(String shopCompanyCode) {
        this.shopCompanyCode = shopCompanyCode;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public String getModels() {
        return models;
    }

    public void setModels(String models) {
        this.models = models;
    }

    public String getFuselage() {
        return fuselage;
    }

    public void setFuselage(String fuselage) {
        this.fuselage = fuselage;
    }

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getClaimReason() {
        return claimReason;
    }

    public void setClaimReason(String claimReason) {
        this.claimReason = claimReason;
    }

    public String getYesOrNo() {
        return yesOrNo;
    }

    public void setYesOrNo(String yesOrNo) {
        this.yesOrNo = yesOrNo;
    }

    public String getYesOrNoReason() {
        return yesOrNoReason;
    }

    public void setYesOrNoReason(String yesOrNoReason) {
        this.yesOrNoReason = yesOrNoReason;
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
