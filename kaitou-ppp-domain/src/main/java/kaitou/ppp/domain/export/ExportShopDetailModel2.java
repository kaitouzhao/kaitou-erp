package kaitou.ppp.domain.export;

import static org.apache.commons.lang.StringEscapeUtils.escapeXml;

/**
 * 认定级别导出模板2.
 * User: 赵立伟
 * Date: 2015/6/28
 * Time: 20:45
 */
public class ExportShopDetailModel2 {
    private String saleRegion;
    private String shopId;
    private String shopName;
    private String cpp2013 = "";
    private String wfp2013 = "";
    private String ipf2013 = "";
    private String cppModel2013 = "";
    private String wfpModel2013 = "";
    private String ipfModel2013 = "";
    private String cpp2014 = "";
    private String wfp2014 = "";
    private String ipf2014 = "";
    private String cppModel2014 = "";
    private String wfpModel2014 = "";
    private String ipfModel2014 = "";
    private String cpp2015 = "";
    private String wfp2015 = "";
    private String ipf2015 = "";
    private String dpModel = "";
    private String pgaModel = "";
    private String tdsModel = "";
    private String dgsModel = "";
    private String ipfModel2015 = "";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExportShopDetailModel2 that = (ExportShopDetailModel2) o;

        if (shopId != null ? !shopId.equals(that.shopId) : that.shopId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return shopId != null ? shopId.hashCode() : 0;
    }

    public String getSaleRegion() {
        return saleRegion;
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

    public String getCpp2013() {
        return escapeXml(cpp2013);
    }

    public void setCpp2013(String cpp2013) {
        this.cpp2013 = cpp2013;
    }

    public String getWfp2013() {
        return escapeXml(wfp2013);
    }

    public void setWfp2013(String wfp2013) {
        this.wfp2013 = wfp2013;
    }

    public String getIpf2013() {
        return escapeXml(ipf2013);
    }

    public void setIpf2013(String ipf2013) {
        this.ipf2013 = ipf2013;
    }

    public String getCppModel2013() {
        return escapeXml(cppModel2013);
    }

    public void setCppModel2013(String cppModel2013) {
        this.cppModel2013 = cppModel2013;
    }

    public String getWfpModel2013() {
        return escapeXml(wfpModel2013);
    }

    public void setWfpModel2013(String wfpModel2013) {
        this.wfpModel2013 = wfpModel2013;
    }

    public String getIpfModel2013() {
        return escapeXml(ipfModel2013);
    }

    public void setIpfModel2013(String ipfModel2013) {
        this.ipfModel2013 = ipfModel2013;
    }

    public String getCpp2014() {
        return escapeXml(cpp2014);
    }

    public void setCpp2014(String cpp2014) {
        this.cpp2014 = cpp2014;
    }

    public String getWfp2014() {
        return escapeXml(wfp2014);
    }

    public void setWfp2014(String wfp2014) {
        this.wfp2014 = wfp2014;
    }

    public String getIpf2014() {
        return escapeXml(ipf2014);
    }

    public void setIpf2014(String ipf2014) {
        this.ipf2014 = ipf2014;
    }

    public String getCppModel2014() {
        return escapeXml(cppModel2014);
    }

    public void setCppModel2014(String cppModel2014) {
        this.cppModel2014 = cppModel2014;
    }

    public String getWfpModel2014() {
        return escapeXml(wfpModel2014);
    }

    public void setWfpModel2014(String wfpModel2014) {
        this.wfpModel2014 = wfpModel2014;
    }

    public String getIpfModel2014() {
        return escapeXml(ipfModel2014);
    }

    public void setIpfModel2014(String ipfModel2014) {
        this.ipfModel2014 = ipfModel2014;
    }

    public String getCpp2015() {
        return escapeXml(cpp2015);
    }

    public void setCpp2015(String cpp2015) {
        this.cpp2015 = cpp2015;
    }

    public String getWfp2015() {
        return escapeXml(wfp2015);
    }

    public void setWfp2015(String wfp2015) {
        this.wfp2015 = wfp2015;
    }

    public String getIpf2015() {
        return escapeXml(ipf2015);
    }

    public void setIpf2015(String ipf2015) {
        this.ipf2015 = ipf2015;
    }

    public String getDpModel() {
        return escapeXml(dpModel);
    }

    public void setDpModel(String dpModel) {
        this.dpModel = dpModel;
    }

    public String getPgaModel() {
        return escapeXml(pgaModel);
    }

    public void setPgaModel(String pgaModel) {
        this.pgaModel = pgaModel;
    }

    public String getTdsModel() {
        return escapeXml(tdsModel);
    }

    public void setTdsModel(String tdsModel) {
        this.tdsModel = tdsModel;
    }

    public String getDgsModel() {
        return escapeXml(dgsModel);
    }

    public void setDgsModel(String dgsModel) {
        this.dgsModel = dgsModel;
    }

    public String getIpfModel2015() {
        return escapeXml(ipfModel2015);
    }

    public void setIpfModel2015(String ipfModel2015) {
        this.ipfModel2015 = ipfModel2015;
    }
}
