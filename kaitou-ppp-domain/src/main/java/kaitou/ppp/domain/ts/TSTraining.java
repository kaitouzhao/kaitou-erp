package kaitou.ppp.domain.ts;

import kaitou.ppp.domain.BaseDomain;
import kaitou.ppp.domain.system.SysCode;
import org.apache.commons.lang.StringUtils;

/**
 * TS培训记录.
 * User: 赵立伟
 * Date: 2015/5/20
 * Time: 10:40
 */
public class TSTraining extends BaseDomain {
    /**
     * 年份
     */
    private String numberOfYear;
    /**
     * 季度
     */
    private String quarter;
    /**
     * 月份
     */
    private String numberOfMonth;
    /**
     * 培训日期
     */
    private String trainingDate;
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
     * 培训师
     */
    private String trainer;
    /**
     * 区域
     *
     * @see kaitou.ppp.domain.system.SysCode.SaleRegion
     */
    private String saleRegion;
    /**
     * 工程师姓名
     */
    private String engineerName;
    /**
     * 联系方式
     */
    private String contact;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 培训地点
     */
    private String trainingSite;
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
        return "TSTraining{" +
                "numberOfYear='" + numberOfYear + '\'' +
                ", quarter='" + quarter + '\'' +
                ", numberOfMonth='" + numberOfMonth + '\'' +
                ", trainingDate='" + trainingDate + '\'' +
                ", productLine='" + productLine + '\'' +
                ", modelType='" + modelType + '\'' +
                ", model='" + model + '\'' +
                ", trainer='" + trainer + '\'' +
                ", saleRegion='" + saleRegion + '\'' +
                ", engineerName='" + engineerName + '\'' +
                ", contact='" + contact + '\'' +
                ", email='" + email + '\'' +
                ", trainingSite='" + trainingSite + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public String getNumberOfMonth() {
        return numberOfMonth;
    }

    public void setNumberOfMonth(String numberOfMonth) {
        this.numberOfMonth = numberOfMonth;
    }

    public String getTrainingDate() {
        return trainingDate;
    }

    public void setTrainingDate(String trainingDate) {
        this.trainingDate = trainingDate;
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

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public String getEngineerName() {
        return engineerName;
    }

    public void setEngineerName(String engineerName) {
        this.engineerName = engineerName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTrainingSite() {
        return trainingSite;
    }

    public void setTrainingSite(String trainingSite) {
        this.trainingSite = trainingSite;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
