package kaitou.ppp.domain.warranty;

import kaitou.ppp.domain.BaseDomain4InDoubt;
import kaitou.ppp.domain.annotation.PKField;
import kaitou.ppp.domain.system.SysCode;
import org.apache.commons.lang.StringUtils;

/**
 * 保修费.
 * User: 赵立伟
 * Date: 2015/5/6
 * Time: 14:06
 */
public class WarrantyFee extends BaseDomain4InDoubt {
    /**
     * 年份
     */
    private String numberOfYear;
    /**
     * 季度
     */
    private String quarter;
    /**
     * 销售区域
     *
     * @see kaitou.ppp.domain.system.SysCode.SaleRegion
     */
    private String saleRegion;
    /**
     * 保修卡号
     */
    private String warrantyCardNo;
    /**
     * 认定店编号
     */
    private String shopId;
    /**
     * 认定店名称
     */
    private String shopName;
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
    @PKField(PKViolationType = SysCode.PKViolationType.IN_DOUBT)
    private String fuselage;
    /**
     * 安装日期
     */
    private String installedDate;
    /**
     * 到期日期
     */
    private String endDate;
    /**
     * 最终用户名称
     */
    private String userCompanyName;
    /**
     * 安装费
     */
    private String installedFee;
    /**
     * 保养费
     */
    private String maintenanceFee;
    /**
     * 延保支持费
     */
    private String extendedPayFee;
    /**
     * 总计
     */
    private String ttl;
    /**
     * 备注
     */
    private String note;

    /**
     * 按年份倒序排列
     *
     * @param o 比较
     * @return 年份大的是负数
     */
    public int comparator(WarrantyFee o) {
        Integer thisYear = 0;
        Integer otherYear = 0;
        try {
            thisYear = Integer.valueOf(numberOfYear);
        } catch (NumberFormatException e) {
        }
        try {
            otherYear = Integer.valueOf(o.getNumberOfYear());
        } catch (NumberFormatException e) {
        }
        return 0 - thisYear.compareTo(otherYear);
    }

    /**
     * 校验保修卡生成记录是否寄回
     * <p>
     * 安装费大于0则为是
     * </p>
     *
     * @return 是则返回"是"
     */
    public String checkCardApplicationRecordIsBack() {
        try {
            if (Double.valueOf(installedFee) > 0) {
                return "是";
            }
            return "";
        } catch (NumberFormatException e) {
            return "";
        }
    }

    /**
     * 校验保修卡生成记录是否过保
     * <p>
     * 保养费大于0则为真
     * </p>
     *
     * @return 过保为真
     */
    public boolean checkCardApplicationRecordIsOutOfWarranty() {
        try {
            if (Double.valueOf(maintenanceFee) > 0) {
                return true;
            }
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void check() {
        if (StringUtils.isEmpty(numberOfYear)) {
            throw new RuntimeException("年份为空");
        }
        if (StringUtils.isEmpty(shopId) || StringUtils.isEmpty(fuselage)) {
            doInDoubt();
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
        return "WarrantyFee{" +
                "numberOfYear='" + numberOfYear + '\'' +
                ", quarter='" + quarter + '\'' +
                ", saleRegion='" + saleRegion + '\'' +
                ", warrantyCardNo='" + warrantyCardNo + '\'' +
                ", shopId='" + shopId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", productLine='" + productLine + '\'' +
                ", modelType='" + modelType + '\'' +
                ", model='" + model + '\'' +
                ", fuselage='" + fuselage + '\'' +
                ", installedDate='" + installedDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", userCompanyName='" + userCompanyName + '\'' +
                ", installedFee='" + installedFee + '\'' +
                ", maintenanceFee='" + maintenanceFee + '\'' +
                ", extendedPayFee='" + extendedPayFee + '\'' +
                ", ttl='" + ttl + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public String getExtendedPayFee() {
        return extendedPayFee;
    }

    public void setExtendedPayFee(String extendedPayFee) {
        this.extendedPayFee = extendedPayFee;
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

    public String getWarrantyCardNo() {
        return warrantyCardNo;
    }

    public void setWarrantyCardNo(String warrantyCardNo) {
        this.warrantyCardNo = warrantyCardNo;
    }

    public String getSaleRegion() {
        return SysCode.SaleRegion.convert2Value(saleRegion);
    }

    public void setSaleRegion(String saleRegion) {
        this.saleRegion = saleRegion;
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

    public String getFuselage() {
        return fuselage;
    }

    public void setFuselage(String fuselage) {
        this.fuselage = fuselage;
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

    public String getUserCompanyName() {
        return userCompanyName;
    }

    public void setUserCompanyName(String userCompanyName) {
        this.userCompanyName = userCompanyName;
    }

    public String getInstalledFee() {
        return installedFee;
    }

    public void setInstalledFee(String installedFee) {
        this.installedFee = installedFee;
    }

    public String getMaintenanceFee() {
        return maintenanceFee;
    }

    public void setMaintenanceFee(String maintenanceFee) {
        this.maintenanceFee = maintenanceFee;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }
}
