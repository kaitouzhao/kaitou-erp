package kaitou.ppp.domain.shop;

import kaitou.ppp.domain.BaseDomain;

/**
 * 认定店合同.
 * User: 赵立伟
 * Date: 2015/5/17
 * Time: 21:08
 */
public class ShopContract extends BaseDomain {
    /**
     * 认定店合同号
     */
    private String contractNo;
    /**
     * 认定店编号
     */
    private String shopId;
    /**
     * 认定店名称
     */
    private String shopName;
    /**
     * 合同邮寄地址
     */
    private String deliveryAddress;
    /**
     * 收件人
     */
    private String recipient;
    /**
     * 电话
     */
    private String phone;
    /**
     * 快递单号
     */
    private String trackingNumber;
    /**
     * 邮寄时间
     */
    private String deliveryTime;
    /**
     * 备注
     */
    private String note;

    @Override
    public void check() {
    }

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
        return "ShopContract{" +
                "contractNo='" + contractNo + '\'' +
                ", shopId='" + shopId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", recipient='" + recipient + '\'' +
                ", phone='" + phone + '\'' +
                ", trackingNumber='" + trackingNumber + '\'' +
                ", deliveryTime='" + deliveryTime + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
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

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
