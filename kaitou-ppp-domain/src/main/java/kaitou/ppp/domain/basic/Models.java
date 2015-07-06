package kaitou.ppp.domain.basic;

import kaitou.ppp.domain.BaseDomain;

/**
 * 机型基础数据.
 * User: 赵立伟
 * Date: 2015/6/22
 * Time: 19:02
 */
public class Models extends BaseDomain {
    /**
     * 大分类
     */
    private String firstType;
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
     * CAMG CODE
     */
    private String camgCode;
    /**
     * 需要装机权限
     */
    private String needInstallPermission;

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
        return "Models{" +
                "firstType='" + firstType + '\'' +
                ", productLine='" + productLine + '\'' +
                ", modelType='" + modelType + '\'' +
                ", model='" + model + '\'' +
                ", camgCode='" + camgCode + '\'' +
                ", needInstallPermission='" + needInstallPermission + '\'' +
                '}';
    }

    public String getNeedInstallPermission() {
        return needInstallPermission;
    }

    public void setNeedInstallPermission(String needInstallPermission) {
        this.needInstallPermission = needInstallPermission;
    }

    public String getFirstType() {
        return firstType;
    }

    public void setFirstType(String firstType) {
        this.firstType = firstType;
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCamgCode() {
        return camgCode;
    }

    public void setCamgCode(String camgCode) {
        this.camgCode = camgCode;
    }
}
