package kaitou.ppp.app.ui.table;

import kaitou.ppp.app.SpringContextManager;
import kaitou.ppp.domain.card.CardApplicationRecord;
import kaitou.ppp.domain.engineer.Engineer;
import kaitou.ppp.domain.engineer.EngineerTraining;
import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.domain.shop.ShopDetail;
import kaitou.ppp.domain.shop.ShopPay;
import kaitou.ppp.domain.shop.ShopRTS;
import kaitou.ppp.domain.warranty.WarrantyFee;
import kaitou.ppp.domain.warranty.WarrantyParts;
import kaitou.ppp.service.BaseExcelService;

import java.io.File;
import java.util.List;

/**
 * 操作管理.
 * User: 赵立伟
 * Date: 2015/5/13
 * Time: 11:38
 */
public abstract class OPManager extends SpringContextManager {
    /**
     * 公共保存/更新接口
     *
     * @param domainType 领域类型
     * @param toDoObj    待操作对象
     */
    public static void saveOrUpdate(String domainType, Object... toDoObj) {
        if (WarrantyParts.class.getSimpleName().equals(domainType)) {
            getWarrantyService().saveOrUpdateWarrantyParts((WarrantyParts[]) toDoObj);
            return;
        }
        if (WarrantyFee.class.getSimpleName().equals(domainType)) {
            getWarrantyService().saveOrUpdateWarrantyFee((WarrantyFee[]) toDoObj);
            return;
        }
        if (Shop.class.getSimpleName().equals(domainType)) {
            getShopService().saveOrUpdateShop((Shop[]) toDoObj);
            return;
        }
        if (ShopDetail.class.getSimpleName().equals(domainType)) {
            getShopService().saveOrUpdateShopDetail((ShopDetail[]) toDoObj);
            return;
        }
        if (ShopPay.class.getSimpleName().equals(domainType)) {
            getShopService().saveOrUpdateShopPay((ShopPay[]) toDoObj);
            return;
        }
        if (ShopRTS.class.getSimpleName().equals(domainType)) {
            getShopService().saveOrUpdateShopRTS((ShopRTS[]) toDoObj);
            return;
        }
        if (Engineer.class.getSimpleName().equals(domainType)) {
            getEngineerService().saveOrUpdateEngineer((Engineer[]) toDoObj);
            return;
        }
        if (EngineerTraining.class.getSimpleName().equals(domainType)) {
            getEngineerService().saveOrUpdateEngineerTraining((EngineerTraining[]) toDoObj);
            return;
        }
        if (CardApplicationRecord.class.getSimpleName().equals(domainType)) {
            getCardService().saveOrUpdateCardApplicationRecord((CardApplicationRecord[]) toDoObj);
            return;
        }
        throw new RuntimeException("尚未支持此类型保存/更新：" + domainType);
    }

    /**
     * 公共导出接口
     *
     * @param domainType 领域类型
     * @param dataList   数据对象列表
     * @param targetFile 目标文件
     */
    public static void export(String domainType, List dataList, File targetFile) {
        if (WarrantyParts.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getWarrantyService()).export2Excel(dataList, targetFile, WarrantyParts.class);
            return;
        }
        if (WarrantyFee.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getWarrantyService()).export2Excel(dataList, targetFile, WarrantyFee.class);
            return;
        }
        if (Shop.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getShopService()).export2Excel(dataList, targetFile, Shop.class);
            return;
        }
        if (ShopDetail.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getShopService()).export2Excel(dataList, targetFile, ShopDetail.class);
            return;
        }
        if (ShopRTS.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getShopService()).export2Excel(dataList, targetFile, ShopRTS.class);
            return;
        }
        if (ShopPay.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getShopService()).export2Excel(dataList, targetFile, ShopPay.class);
            return;
        }
        if (Engineer.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getEngineerService()).export2Excel(dataList, targetFile, Engineer.class);
            return;
        }
        if (EngineerTraining.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getEngineerService()).export2Excel(dataList, targetFile, EngineerTraining.class);
            return;
        }
        if (CardApplicationRecord.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getEngineerService()).export2Excel(dataList, targetFile, CardApplicationRecord.class);
            return;
        }
        throw new RuntimeException("尚未支持此类型导出：" + domainType);
    }

    /**
     * 公共删除接口
     *
     * @param domainType 领域类型
     * @param deleted    待删除对象
     */
    public static void delete(String domainType, Object... deleted) {
        if (WarrantyParts.class.getSimpleName().equals(domainType)) {
            getWarrantyService().deleteWarrantyParts(deleted);
            return;
        }
        if (WarrantyFee.class.getSimpleName().equals(domainType)) {
            getWarrantyService().deleteWarrantyFee(deleted);
            return;
        }
        if (Shop.class.getSimpleName().equals(domainType)) {
            getShopService().deleteShops(deleted);
            return;
        }
        if (ShopDetail.class.getSimpleName().equals(domainType)) {
            getShopService().deleteShopDetails(deleted);
            return;
        }
        if (ShopRTS.class.getSimpleName().equals(domainType)) {
            getShopService().deleteShopRTSs(deleted);
            return;
        }
        if (ShopPay.class.getSimpleName().equals(domainType)) {
            getShopService().deleteShopPays(deleted);
            return;
        }
        if (Engineer.class.getSimpleName().equals(domainType)) {
            getEngineerService().deleteEngineers(deleted);
            return;
        }
        if (EngineerTraining.class.getSimpleName().equals(domainType)) {
            getEngineerService().deleteEngineerTrainings(deleted);
            return;
        }
        if (CardApplicationRecord.class.getSimpleName().equals(domainType)) {
            getCardService().deleteCardApplicationRecords(deleted);
            return;
        }
        throw new RuntimeException("尚未支持此类型删除：" + domainType);
    }
}
