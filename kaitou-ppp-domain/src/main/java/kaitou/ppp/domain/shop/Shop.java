package kaitou.ppp.domain.shop;

import kaitou.ppp.domain.BaseDomain;
import kaitou.ppp.domain.system.SysCode;
import org.apache.commons.lang.StringUtils;

/**
 * 认定店.
 * User: 赵立伟
 * Date: 2015/1/25
 * Time: 11:46
 */
public class Shop extends BaseDomain {
    /**
     * 销售区域
     *
     * @see kaitou.ppp.domain.system.SysCode.SaleRegion
     */
    protected String saleRegion;
    /**
     * 认定店编号（合同编号）
     */
    protected String id;
    /**
     * 认定店名
     */
    protected String name;
    /**
     * 合同联系人
     */
    protected String linkMan;
    /**
     * 联系电话
     */
    protected String phone;
    /**
     * 联系地址
     */
    protected String address;
    /**
     * 联系邮箱
     */
    protected String email;
    /**
     * 认定店状态
     *
     * @see kaitou.ppp.domain.system.SysCode.ShopStatus
     */
    protected String status = SysCode.ShopStatus.IN_THE_USE.getValue();
    /**
     * PPP业务接口人姓名
     */
    private String pppBusinessInterfacePeople;
    /**
     * 付款代码
     */
    protected String payCode;
    /**
     * 付款名称
     */
    protected String payName;
    /**
     * 开户行
     */
    protected String accountBank;
    /**
     * 帐号
     */
    protected String accountNo;
    /**
     * 备注
     */
    private String note;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shop shop = (Shop) o;

        return !(id != null ? !id.equals(shop.id) : shop.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "saleRegion='" + saleRegion + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", linkMan='" + linkMan + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                ", pppBusinessInterfacePeople='" + pppBusinessInterfacePeople + '\'' +
                ", payCode='" + payCode + '\'' +
                ", payName='" + payName + '\'' +
                ", accountBank='" + accountBank + '\'' +
                ", accountNo='" + accountNo + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

    @Override
    public void check() {
        if (StringUtils.isEmpty(id)) {
            throw new RuntimeException("认定店编码为空");
        }
        if (StringUtils.isEmpty(name)) {
            throw new RuntimeException("认定店名为空");
        }
    }

    @Override
    public String dbFileName() {
        return SysCode.SaleRegion.convert2Code(saleRegion) + dbFileSuffix();
    }

    @Override
    public String dbFileSuffix() {
        return '_' + getClass().getSimpleName() + DB_SUFFIX;
    }

    public String getPppBusinessInterfacePeople() {
        return pppBusinessInterfacePeople;
    }

    public void setPppBusinessInterfacePeople(String pppBusinessInterfacePeople) {
        this.pppBusinessInterfacePeople = pppBusinessInterfacePeople;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public String getAccountBank() {
        return accountBank;
    }

    public void setAccountBank(String accountBank) {
        this.accountBank = accountBank;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSaleRegion() {
        return SysCode.SaleRegion.convert2Value(saleRegion);
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

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
