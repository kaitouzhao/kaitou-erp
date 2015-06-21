package kaitou.ppp.manager.card.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.common.utils.FileUtil;
import kaitou.ppp.domain.card.CardApplicationRecord;
import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.domain.shop.ShopDetail;
import kaitou.ppp.domain.system.SysCode;
import kaitou.ppp.domain.warranty.WarrantyFee;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.card.CardApplicationRecordManager;
import kaitou.ppp.manager.listener.ShopUpdateListener;
import kaitou.ppp.manager.listener.WarrantyUpdateListener;
import org.apache.commons.lang.StringUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 保修卡生成记录DAO事务管理层实现.
 * User: 赵立伟
 * Date: 2015/3/7
 * Time: 21:41
 */
public class CardApplicationRecordManagerImpl extends BaseFileDaoManager<CardApplicationRecord> implements CardApplicationRecordManager, WarrantyUpdateListener, ShopUpdateListener {

    @Override
    public Class<CardApplicationRecord> domainClass() {
        return CardApplicationRecord.class;
    }

    @Override
    public List<CardApplicationRecord> query() {
        List<CardApplicationRecord> cardApplicationRecords = super.query();
        Collections.sort(cardApplicationRecords, new Comparator<CardApplicationRecord>() {
            @Override
            public int compare(CardApplicationRecord o1, CardApplicationRecord o2) {
                if (StringUtils.isEmpty(o1.getFuselage()) || StringUtils.isEmpty(o2.getFuselage())) {
                    return -1;
                }
                return o1.getFuselage().equals(o2.getFuselage()) ? 0 : -1;
            }
        });
        return cardApplicationRecords;
    }

    @Override
    public void updateWarrantyFeeEvent(WarrantyFee... warrantyFees) {
        if (CollectionUtil.isEmpty(warrantyFees)) {
            return;
        }
        List<CardApplicationRecord> cardApplicationRecords = query();
        for (CardApplicationRecord cardApplicationRecord : cardApplicationRecords) {
            for (WarrantyFee warrantyFee : warrantyFees) {
                if (cardApplicationRecord.getFuselage().equals(warrantyFee.getFuselage())) {
                    if (StringUtils.isEmpty(cardApplicationRecord.getIsBack())) {
                        cardApplicationRecord.setIsBack(warrantyFee.checkCardApplicationRecordIsBack());
                    }
                    if (warrantyFee.checkCardApplicationRecordIsOutOfWarranty() && !SysCode.WarrantyStatus.OUT_WARRANTY.getValue().equals(cardApplicationRecord.getStatus())) {
                        cardApplicationRecord.setStatus(SysCode.WarrantyStatus.OUT_WARRANTY.getValue());
                    }
                }
            }
        }
        save(cardApplicationRecords);
    }

    @Override
    public void updateShopEvent(Shop... shops) {
        List<CardApplicationRecord> cardApplicationRecords = queryAll();
        for (CardApplicationRecord cardApplicationRecord : cardApplicationRecords) {
            for (Shop shop : shops) {
                if (shop.getId().equals(cardApplicationRecord.getShopId())) {
                    cardApplicationRecord.setShopName(shop.getName());
                }
            }
        }
        save(cardApplicationRecords);
    }

    @Override
    public void updateShopDetailEvent(ShopDetail... shopDetails) {

    }

    @Override
    public void updateShopIdEvent(Shop... shops) {
        List<CardApplicationRecord> cardApplicationRecords = queryAll();
        FileUtil.deleteFilesOfDir(dbDir, "CardApplicationRecord.kdb");
        for (CardApplicationRecord cardApplicationRecord : cardApplicationRecords) {
            for (Shop shop : shops) {
                if (!shop.getName().equals(cardApplicationRecord.getShopName())) {
                    continue;
                }
                cardApplicationRecord.setShopId(shop.getId());
            }
        }
        save(cardApplicationRecords);
    }
}
