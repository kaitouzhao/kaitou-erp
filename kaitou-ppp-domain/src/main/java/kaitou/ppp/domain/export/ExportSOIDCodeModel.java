package kaitou.ppp.domain.export;

import static org.apache.commons.lang.StringEscapeUtils.escapeXml;

/**
 * 自定义SOID识别码导出模板.
 * User: 赵立伟
 * Date: 2015/6/14
 * Time: 16:43
 */
public class ExportSOIDCodeModel {

    private String saleRegion = "";
    private String shopName = "";
    private String dp = "";
    private String pga = "";
    private String tds = "";
    private String dgs = "";
    private String ipf = "";
    private String pp = "";
    private String newSoid = "";
    private String newVerificationCode = "";
    private String note = "";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExportSOIDCodeModel that = (ExportSOIDCodeModel) o;

        if (shopName != null ? !shopName.equals(that.shopName) : that.shopName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return shopName != null ? shopName.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ExportSOIDCodeModel{" +
                "saleRegion='" + saleRegion + '\'' +
                ", shopName='" + shopName + '\'' +
                ", dp='" + dp + '\'' +
                ", pga='" + pga + '\'' +
                ", tds='" + tds + '\'' +
                ", dgs='" + dgs + '\'' +
                ", ipf='" + ipf + '\'' +
                ", pp='" + pp + '\'' +
                ", newSoid='" + newSoid + '\'' +
                ", newVerificationCode='" + newVerificationCode + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

    public String getSaleRegion() {
        return saleRegion;
    }

    public void setSaleRegion(String saleRegion) {
        this.saleRegion = saleRegion;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getPga() {
        return pga;
    }

    public void setPga(String pga) {
        this.pga = pga;
    }

    public String getTds() {
        return tds;
    }

    public void setTds(String tds) {
        this.tds = tds;
    }

    public String getDgs() {
        return dgs;
    }

    public void setDgs(String dgs) {
        this.dgs = dgs;
    }

    public String getIpf() {
        return ipf;
    }

    public void setIpf(String ipf) {
        this.ipf = ipf;
    }

    public String getPp() {
        return pp;
    }

    public void setPp(String pp) {
        this.pp = pp;
    }

    public String getNewSoid() {
        return escapeXml(newSoid);
    }

    public void setNewSoid(String newSoid) {
        this.newSoid = newSoid;
    }

    public String getNewVerificationCode() {
        return escapeXml(newVerificationCode);
    }

    public void setNewVerificationCode(String newVerificationCode) {
        this.newVerificationCode = newVerificationCode;
    }

    public String getNote() {
        return escapeXml(note);
    }

    public void setNote(String note) {
        this.note = note;
    }
}
