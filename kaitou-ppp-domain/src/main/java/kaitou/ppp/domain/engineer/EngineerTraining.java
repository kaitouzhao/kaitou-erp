package kaitou.ppp.domain.engineer;

import kaitou.ppp.domain.BaseDomain;
import org.apache.commons.lang.StringUtils;

import static kaitou.ppp.domain.system.SysCode.DB_FILE_NAME_SPLIT;
import static kaitou.ppp.domain.system.SysCode.SaleRegion;

/**
 * 工程师培训信息.
 * User: 赵立伟
 * Date: 2015/1/18
 * Time: 16:44
 */
public class EngineerTraining extends BaseDomain {
    /**
     * 编号
     */
    protected String id;
    /**
     * 姓名
     */
    protected String name;
    /**
     * 销售区域
     *
     * @see SaleRegion
     */
    protected String saleRegion;
    /**
     * 认定店编码
     */
    protected String shopId;
    /**
     * 认定店名
     */
    protected String shopName;
    /**
     * 认定店等级
     */
    protected String shopLevel;
    /**
     * 认定年限
     */
    protected String numberOfYear;
    /**
     * ACE等级
     */
    protected String aceLevel;
    /**
     * 入职日期
     */
    protected String dateOfEntry;
    /**
     * 离职日期
     */
    protected String dateOfDeparture;
    /**
     * 状态
     */
    protected String status;
    /**
     * 产品线
     */
    protected String productLine;
    /**
     * 培训师
     */
    protected String trainer;
    /**
     * 培训类型
     */
    protected String trainingType;
    /**
     * 培训日期
     */
    protected String dateOfTraining;
    /**
     * 培训机型
     */
    protected String trainingModel;
    /**
     * 培训是否合格
     */
    private String qualified;
    /**
     * 备注
     */
    private String note;
    /**
     * 年份
     */
    private String year;

    @Override
    public String dbFileSuffix() {
        return DB_FILE_NAME_SPLIT + shopId + DB_FILE_NAME_SPLIT + getClass().getSimpleName() + DB_SUFFIX;
    }

    @Override
    public String dbFileName() {
        return SaleRegion.convert2Code(saleRegion) + dbFileSuffix();
    }

    @Override
    public void check() {
        if (StringUtils.isEmpty(id)) {
            throw new RuntimeException("编号为空");
        }
        if (StringUtils.isEmpty(name)) {
            throw new RuntimeException("姓名为空");
        }
        if (StringUtils.isEmpty(saleRegion)) {
            throw new RuntimeException("区域为空");
        }
        if (StringUtils.isEmpty(shopId)) {
            throw new RuntimeException("认定店编码为空");
        }
        if (StringUtils.isEmpty(shopName)) {
            throw new RuntimeException("认定店名为空");
        }
        if (StringUtils.isEmpty(productLine)) {
            throw new RuntimeException("产品线为空");
        }
        if (StringUtils.isEmpty(trainer)) {
            throw new RuntimeException("培训师为空");
        }
        if (StringUtils.isEmpty(trainingModel)) {
            throw new RuntimeException("培训机型为空");
        }
        if (StringUtils.isEmpty(trainingType)) {
            throw new RuntimeException("培训类型为空");
        }
        if (StringUtils.isEmpty(dateOfTraining)) {
            throw new RuntimeException("培训时间为空");
        }
    }

    @Override
    public String toString() {
        return "EngineerTraining{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", saleRegion='" + saleRegion + '\'' +
                ", shopId='" + shopId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", shopLevel='" + shopLevel + '\'' +
                ", numberOfYear='" + numberOfYear + '\'' +
                ", aceLevel='" + aceLevel + '\'' +
                ", dateOfEntry='" + dateOfEntry + '\'' +
                ", dateOfDeparture='" + dateOfDeparture + '\'' +
                ", status='" + status + '\'' +
                ", productLine='" + productLine + '\'' +
                ", trainer='" + trainer + '\'' +
                ", trainingType='" + trainingType + '\'' +
                ", dateOfTraining='" + dateOfTraining + '\'' +
                ", trainingModel='" + trainingModel + '\'' +
                ", qualified='" + qualified + '\'' +
                ", note='" + note + '\'' +
                ", year='" + year + '\'' +
                '}';
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getQualified() {
        return qualified;
    }

    public void setQualified(String qualified) {
        this.qualified = qualified;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSaleRegion() {
        return SaleRegion.convert2Value(saleRegion);
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

    public String getShopLevel() {
        return shopLevel;
    }

    public void setShopLevel(String shopLevel) {
        this.shopLevel = shopLevel;
    }

    public String getNumberOfYear() {
        return numberOfYear;
    }

    public void setNumberOfYear(String numberOfYear) {
        this.numberOfYear = numberOfYear;
    }

    public String getAceLevel() {
        return aceLevel;
    }

    public void setAceLevel(String aceLevel) {
        this.aceLevel = aceLevel;
    }

    public String getDateOfEntry() {
        return dateOfEntry;
    }

    public void setDateOfEntry(String dateOfEntry) {
        this.dateOfEntry = dateOfEntry;
    }

    public String getDateOfDeparture() {
        return dateOfDeparture;
    }

    public void setDateOfDeparture(String dateOfDeparture) {
        this.dateOfDeparture = dateOfDeparture;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public String getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(String trainingType) {
        this.trainingType = trainingType;
    }

    public String getDateOfTraining() {
        return dateOfTraining;
    }

    public void setDateOfTraining(String dateOfTraining) {
        this.dateOfTraining = dateOfTraining;
    }

    public String getTrainingModel() {
        return trainingModel;
    }

    public void setTrainingModel(String trainingModel) {
        this.trainingModel = trainingModel;
    }
}
