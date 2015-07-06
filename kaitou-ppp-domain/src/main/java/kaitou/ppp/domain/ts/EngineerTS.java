package kaitou.ppp.domain.ts;

import kaitou.ppp.domain.BaseDomain;

import static kaitou.ppp.domain.system.SysCode.SaleRegion;

/**
 * TS工程师基本信息.
 * User: 赵立伟
 * Date: 2015/6/23
 * Time: 14:40
 */
public class EngineerTS extends BaseDomain {
    /**
     * 区域
     *
     * @see SaleRegion
     */
    private String saleRegion;
    /**
     * 工程师姓名
     */
    private String engineerName;
    /**
     * 办公城市
     */
    private String city;
    /**
     * 员工号
     */
    private String employeeNo;
    /**
     * 邮箱地址
     */
    private String email;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 组别
     */
    private String level;
    /**
     * 负责产品线
     */
    private String productLine;
    /**
     * 职位
     */
    private String occupation;
    /**
     * 分机号
     */
    private String phone;
    /**
     * 常用地址
     */
    private String address;

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
        return "TSEngineer{" +
                "saleRegion='" + saleRegion + '\'' +
                ", engineerName='" + engineerName + '\'' +
                ", city='" + city + '\'' +
                ", employeeNo='" + employeeNo + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", level='" + level + '\'' +
                ", productLine='" + productLine + '\'' +
                ", occupation='" + occupation + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public String getEngineerName() {
        return engineerName;
    }

    public void setEngineerName(String engineerName) {
        this.engineerName = engineerName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSaleRegion() {
        return SaleRegion.convert2Value(saleRegion);
    }

    public void setSaleRegion(String saleRegion) {
        this.saleRegion = saleRegion;
    }
}
