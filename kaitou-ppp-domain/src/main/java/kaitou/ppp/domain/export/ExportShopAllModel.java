package kaitou.ppp.domain.export;

import static org.apache.commons.lang.StringEscapeUtils.escapeXml;

/**
 * 基础信息全导出模板.
 * User: 赵立伟
 * Date: 2015/7/3
 * Time: 16:44
 */
public class ExportShopAllModel {

    private String status;
    private String saleRegion;
    private String id;
    private String name;
    private String pppBusinessInterfacePeople;
    private String phone;
    private String address;
    private String email;
    private String numberOfYear;
    private String cppLevel = "";
    private String cppModel = "";
    private String wfpLevel = "";
    private String wfpModel = "";
    private String ipfLevel = "";
    private String ipfModel = "";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExportShopAllModel that = (ExportShopAllModel) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ExportShopAllModel{" +
                "status='" + status + '\'' +
                ", saleRegion='" + saleRegion + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", pppBusinessInterfacePeople='" + pppBusinessInterfacePeople + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", numberOfYear='" + numberOfYear + '\'' +
                ", cppLevel='" + cppLevel + '\'' +
                ", cppModel='" + cppModel + '\'' +
                ", wfpLevel='" + wfpLevel + '\'' +
                ", wfpModel='" + wfpModel + '\'' +
                ", ipfLevel='" + ipfLevel + '\'' +
                ", ipfModel='" + ipfModel + '\'' +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSaleRegion() {
        return saleRegion;
    }

    public void setSaleRegion(String saleRegion) {
        this.saleRegion = saleRegion;
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

    public String getPppBusinessInterfacePeople() {
        return escapeXml(pppBusinessInterfacePeople);
    }

    public void setPppBusinessInterfacePeople(String pppBusinessInterfacePeople) {
        this.pppBusinessInterfacePeople = pppBusinessInterfacePeople;
    }

    public String getPhone() {
        return escapeXml(phone);
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return escapeXml(address);
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return escapeXml(email);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumberOfYear() {
        return numberOfYear;
    }

    public void setNumberOfYear(String numberOfYear) {
        this.numberOfYear = numberOfYear;
    }

    public String getCppLevel() {
        return escapeXml(cppLevel);
    }

    public void setCppLevel(String cppLevel) {
        this.cppLevel = cppLevel;
    }

    public String getCppModel() {
        return escapeXml(cppModel);
    }

    public void setCppModel(String cppModel) {
        this.cppModel = cppModel;
    }

    public String getWfpLevel() {
        return escapeXml(wfpLevel);
    }

    public void setWfpLevel(String wfpLevel) {
        this.wfpLevel = wfpLevel;
    }

    public String getWfpModel() {
        return escapeXml(wfpModel);
    }

    public void setWfpModel(String wfpModel) {
        this.wfpModel = wfpModel;
    }

    public String getIpfLevel() {
        return escapeXml(ipfLevel);
    }

    public void setIpfLevel(String ipfLevel) {
        this.ipfLevel = ipfLevel;
    }

    public String getIpfModel() {
        return escapeXml(ipfModel);
    }

    public void setIpfModel(String ipfModel) {
        this.ipfModel = ipfModel;
    }
}
