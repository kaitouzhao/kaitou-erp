package kaitou.ppp.domain.ts;

import kaitou.ppp.domain.BaseDomain;
import kaitou.ppp.domain.system.SysCode;

/**
 * TS装机权限.
 * User: 赵立伟
 * Date: 2015/5/21
 * Time: 10:22
 */
public class TSInstallPermission extends BaseDomain {
    /**
     * 销售区域
     *
     * @see kaitou.ppp.domain.system.SysCode.SaleRegion
     */
    private String saleRegion;
    /**
     * 工程师姓名
     */
    private String engineerName;
    /**
     * 申请时间
     */
    private String applyDate;
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
     * MAC地址
     */
    private String mac;
    /**
     * 备注
     */
    private String note;

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
        return "TSInstallPermission{" +
                "saleRegion='" + saleRegion + '\'' +
                ", engineerName='" + engineerName + '\'' +
                ", applyDate='" + applyDate + '\'' +
                ", modelType='" + modelType + '\'' +
                ", model='" + model + '\'' +
                ", fuselage='" + fuselage + '\'' +
                ", userCompanyName='" + userCompanyName + '\'' +
                ", mac='" + mac + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

    public String getEngineerName() {
        return engineerName;
    }

    public void setEngineerName(String engineerName) {
        this.engineerName = engineerName;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
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

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
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
