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
        return '_' + getClass().getSimpleName() + DB_SUFFIX;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TSTraining training = (TSTraining) o;

        if (contact != null ? !contact.equals(training.contact) : training.contact != null) return false;
        if (email != null ? !email.equals(training.email) : training.email != null) return false;
        if (engineerName != null ? !engineerName.equals(training.engineerName) : training.engineerName != null)
            return false;
        if (model != null ? !model.equals(training.model) : training.model != null) return false;
        if (modelType != null ? !modelType.equals(training.modelType) : training.modelType != null) return false;
        if (note != null ? !note.equals(training.note) : training.note != null) return false;
        if (numberOfMonth != null ? !numberOfMonth.equals(training.numberOfMonth) : training.numberOfMonth != null)
            return false;
        if (numberOfYear != null ? !numberOfYear.equals(training.numberOfYear) : training.numberOfYear != null)
            return false;
        if (productLine != null ? !productLine.equals(training.productLine) : training.productLine != null)
            return false;
        if (quarter != null ? !quarter.equals(training.quarter) : training.quarter != null) return false;
        if (saleRegion != null ? !saleRegion.equals(training.saleRegion) : training.saleRegion != null) return false;
        if (trainer != null ? !trainer.equals(training.trainer) : training.trainer != null) return false;
        if (trainingDate != null ? !trainingDate.equals(training.trainingDate) : training.trainingDate != null)
            return false;
        if (trainingSite != null ? !trainingSite.equals(training.trainingSite) : training.trainingSite != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = numberOfYear != null ? numberOfYear.hashCode() : 0;
        result = 31 * result + (quarter != null ? quarter.hashCode() : 0);
        result = 31 * result + (numberOfMonth != null ? numberOfMonth.hashCode() : 0);
        result = 31 * result + (trainingDate != null ? trainingDate.hashCode() : 0);
        result = 31 * result + (productLine != null ? productLine.hashCode() : 0);
        result = 31 * result + (modelType != null ? modelType.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (trainer != null ? trainer.hashCode() : 0);
        result = 31 * result + (saleRegion != null ? saleRegion.hashCode() : 0);
        result = 31 * result + (engineerName != null ? engineerName.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (trainingSite != null ? trainingSite.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        return result;
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
