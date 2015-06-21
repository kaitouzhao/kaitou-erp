package kaitou.ppp.domain.ts;

import kaitou.ppp.domain.BaseDomain;

/**
 * dongle记录.
 * User: 赵立伟
 * Date: 2015/6/13
 * Time: 23:17
 */
public class TSDongle extends BaseDomain {
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 姓名
     */
    private String name;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * Dongle序列号
     */
    private String dongleSerialNo;
    /**
     * Licenses失效日期
     */
    private String outOfDate;
    /**
     * PW900
     */
    private String pw900;
    /**
     * PW340
     */
    private String pw340;
    /**
     * PW360
     */
    private String pw360;
    /**
     * PW500
     */
    private String pw500;
    /**
     * PW750
     */
    private String pw750;
    /**
     * CW500
     */
    private String cw500;
    /**
     * CW550
     */
    private String cw550;
    /**
     * CW650
     */
    private String cw650;
    /**
     * CW700
     */
    private String cw700;

    @Override
    public String toString() {
        return "TSDongle{" +
                "companyName='" + companyName + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", dongleSerialNo='" + dongleSerialNo + '\'' +
                ", outOfDate='" + outOfDate + '\'' +
                ", pw900='" + pw900 + '\'' +
                ", pw340='" + pw340 + '\'' +
                ", pw360='" + pw360 + '\'' +
                ", pw500='" + pw500 + '\'' +
                ", pw750='" + pw750 + '\'' +
                ", cw500='" + cw500 + '\'' +
                ", cw550='" + cw550 + '\'' +
                ", cw650='" + cw650 + '\'' +
                ", cw700='" + cw700 + '\'' +
                '}';
    }

    @Override
    public String dbFileName() {
        return dbFileSuffix();
    }

    @Override
    public String dbFileSuffix() {
        return getClass().getSimpleName() + DB_SUFFIX;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDongleSerialNo() {
        return dongleSerialNo;
    }

    public void setDongleSerialNo(String dongleSerialNo) {
        this.dongleSerialNo = dongleSerialNo;
    }

    public String getOutOfDate() {
        return outOfDate;
    }

    public void setOutOfDate(String outOfDate) {
        this.outOfDate = outOfDate;
    }

    public String getPw900() {
        return pw900;
    }

    public void setPw900(String pw900) {
        this.pw900 = pw900;
    }

    public String getPw340() {
        return pw340;
    }

    public void setPw340(String pw340) {
        this.pw340 = pw340;
    }

    public String getPw360() {
        return pw360;
    }

    public void setPw360(String pw360) {
        this.pw360 = pw360;
    }

    public String getPw500() {
        return pw500;
    }

    public void setPw500(String pw500) {
        this.pw500 = pw500;
    }

    public String getPw750() {
        return pw750;
    }

    public void setPw750(String pw750) {
        this.pw750 = pw750;
    }

    public String getCw500() {
        return cw500;
    }

    public void setCw500(String cw500) {
        this.cw500 = cw500;
    }

    public String getCw550() {
        return cw550;
    }

    public void setCw550(String cw550) {
        this.cw550 = cw550;
    }

    public String getCw650() {
        return cw650;
    }

    public void setCw650(String cw650) {
        this.cw650 = cw650;
    }

    public String getCw700() {
        return cw700;
    }

    public void setCw700(String cw700) {
        this.cw700 = cw700;
    }
}
