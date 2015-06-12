package kaitou.ppp.manager.listener;

import kaitou.ppp.domain.warranty.WarrantyFee;

/**
 * 保修管理更新监听对象接口.
 * User: 赵立伟
 * Date: 2015/6/10
 * Time: 19:39
 */
public interface WarrantyUpdateListener {
    /**
     * 更新保修费事件
     *
     * @param warrantyFees 保修费
     */
    public void updateWarrantyFeeEvent(WarrantyFee... warrantyFees);
}
