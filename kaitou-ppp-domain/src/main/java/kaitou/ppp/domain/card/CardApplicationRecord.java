package kaitou.ppp.domain.card;

import kaitou.ppp.domain.BaseDomain4InDoubt;
import kaitou.ppp.domain.annotation.PKField;
import kaitou.ppp.domain.system.SysCode;
import org.apache.commons.lang.StringUtils;

import java.awt.*;

import static kaitou.ppp.domain.system.SysCode.PKViolationType;
import static kaitou.ppp.domain.system.SysCode.WarrantyStatus;

/**
 * 保修卡生成记录.
 * User: 赵立伟
 * Date: 2015/3/7
 * Time: 21:22
 */
public class CardApplicationRecord extends BaseDomain4InDoubt {
    /**
     * 区域
     *
     * @see kaitou.ppp.domain.system.SysCode.SaleRegion
     */
    private String saleRegion;
    /**
     * 保修卡号
     */
    private String warrantyCard;
    /**
     * 申请日期
     */
    private String applyDate;
    /**
     * 状态
     *
     * @see kaitou.ppp.domain.system.SysCode.WarrantyStatus
     */
    private String status;
    /**
     * 保卡是否寄回
     */
    private String isBack;
    /**
     * 产品线
     *
     * @see kaitou.ppp.domain.system.SysCode.ModelCode
     */
    private String allModels;
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
     * <p>主键</p>
     */
    @PKField(PKViolationType = PKViolationType.IN_DOUBT)
    private String fuselage;
    /**
     * 认定店编号
     */
    private String shopId;
    /**
     * 认定店名称
     */
    private String shopName;
    /**
     * 装机时间
     */
    private String installedDate;
    /**
     * 到期时间
     */
    private String endDate;
    /**
     * 初始读数
     */
    private String initData;
    /**
     * 最终用户名称
     */
    private String userCompanyName;
    /**
     * 联系人
     */
    private String userLinkMan;
    /**
     * 联系方式
     */
    private String userContact;
    /**
     * 联系地址
     */
    private String installedAddress;
    /**
     * 备注
     */
    private String note;

    @Override
    public Color tableRowColor() {
        if (WarrantyStatus.JUST_OUT_WARRANTY.getValue().equals(status)) {
            return new Color(39, 200, 199);
        }
        return super.tableRowColor();
    }

    @Override
    public void check() {
        if (StringUtils.isEmpty(fuselage) || StringUtils.isEmpty(shopId) || StringUtils.isEmpty(shopName)) {
            doInDoubt();
        }
    }

    @Override
    public String dbFileName() {
        return dbFileSuffix();
    }

    @Override
    public String dbFileSuffix() {
        return getClass().getSimpleName() + DB_SUFFIX;
    }

    @Override
    public String toString() {
        return "CardApplicationRecord{" +
                "saleRegion='" + saleRegion + '\'' +
                ", warrantyCard='" + warrantyCard + '\'' +
                ", applyDate='" + applyDate + '\'' +
                ", status='" + status + '\'' +
                ", isBack='" + isBack + '\'' +
                ", allModels='" + allModels + '\'' +
                ", modelType='" + modelType + '\'' +
                ", models='" + models + '\'' +
                ", fuselage='" + fuselage + '\'' +
                ", shopId='" + shopId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", installedDate='" + installedDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", initData='" + initData + '\'' +
                ", userCompanyName='" + userCompanyName + '\'' +
                ", userLinkMan='" + userLinkMan + '\'' +
                ", userContact='" + userContact + '\'' +
                ", installedAddress='" + installedAddress + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

    public String getSaleRegion() {
        return SysCode.SaleRegion.convert2Value(saleRegion);
    }

    public void setSaleRegion(String saleRegion) {
        this.saleRegion = saleRegion;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getAllModels() {
        return allModels;
    }

    public void setAllModels(String allModels) {
        this.allModels = allModels;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsBack() {
        return isBack;
    }

    public void setIsBack(String isBack) {
        this.isBack = isBack;
    }

    public String getWarrantyCard() {
        return warrantyCard;
    }

    public void setWarrantyCard(String warrantyCard) {
        this.warrantyCard = warrantyCard;
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

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getInstalledDate() {
        return installedDate;
    }

    public void setInstalledDate(String installedDate) {
        this.installedDate = installedDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getInitData() {
        return initData;
    }

    public void setInitData(String initData) {
        this.initData = initData;
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

    public String getInstalledAddress() {
        return installedAddress;
    }

    public void setInstalledAddress(String installedAddress) {
        this.installedAddress = installedAddress;
    }
}
