package kaitou.ppp.app.ui.table;

import kaitou.ppp.app.SpringContextManager;
import kaitou.ppp.domain.card.CardApplicationRecord;
import kaitou.ppp.domain.engineer.Engineer;
import kaitou.ppp.domain.engineer.EngineerTraining;
import kaitou.ppp.domain.shop.*;
import kaitou.ppp.domain.tech.*;
import kaitou.ppp.domain.ts.*;
import kaitou.ppp.domain.warranty.WarrantyConsumables;
import kaitou.ppp.domain.warranty.WarrantyFee;
import kaitou.ppp.domain.warranty.WarrantyParts;
import kaitou.ppp.domain.warranty.WarrantyPrint;
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
        if (ComponentBorrowing.class.getSimpleName().equals(domainType)) {
            getTsService().saveOrUpdateComponentBorrowing((ComponentBorrowing[]) toDoObj);
            return;
        }
        if (ToolRecipients.class.getSimpleName().equals(domainType)) {
            getTsService().saveOrUpdateToolRecipients((ToolRecipients[]) toDoObj);
            return;
        }
        if (NewMachineClaim.class.getSimpleName().equals(domainType)) {
            getTsService().saveOrUpdateNewMachineClaim((NewMachineClaim[]) toDoObj);
            return;
        }
        if (OldMachineRenew.class.getSimpleName().equals(domainType)) {
            getTsService().saveOrUpdateOldMachineRenew((OldMachineRenew[]) toDoObj);
            return;
        }
        if (TSInstallPermission.class.getSimpleName().equals(domainType)) {
            getTsService().saveOrUpdateTSInstallPermission((TSInstallPermission[]) toDoObj);
            return;
        }
        if (TSSDSPermission.class.getSimpleName().equals(domainType)) {
            getTsService().saveOrUpdateTSSDSPermission((TSSDSPermission[]) toDoObj);
            return;
        }
        if (TSManualPermissions.class.getSimpleName().equals(domainType)) {
            getTsService().saveOrUpdateTSManualPermission((TSManualPermissions[]) toDoObj);
            return;
        }
        if (TSTraining.class.getSimpleName().equals(domainType)) {
            getTsService().saveOrUpdateTSTraining((TSTraining[]) toDoObj);
            return;
        }
        if (TechInstallPermission.class.getSimpleName().equals(domainType)) {
            getTechService().saveOrUpdateInstallPermission((TechInstallPermission[]) toDoObj);
            return;
        }
        if (TechSupport.class.getSimpleName().equals(domainType)) {
            getTechService().saveOrUpdateTechSupport((TechSupport[]) toDoObj);
            return;
        }
        if (TechSDSPermission.class.getSimpleName().equals(domainType)) {
            getTechService().saveOrUpdateSDSPermissions((TechSDSPermission[]) toDoObj);
            return;
        }
        if (SOIDCode.class.getSimpleName().equals(domainType)) {
            getTechService().saveOrUpdateSOIDCode((SOIDCode[]) toDoObj);
            return;
        }
        if (TechManualPermissions.class.getSimpleName().equals(domainType)) {
            getTechService().saveOrUpdateManualPermissions((TechManualPermissions[]) toDoObj);
            return;
        }
        if (WarrantyConsumables.class.getSimpleName().equals(domainType)) {
            getWarrantyService().saveOrUpdateWarrantyConsumables((WarrantyConsumables[]) toDoObj);
            return;
        }
        if (WarrantyPrint.class.getSimpleName().equals(domainType)) {
            getWarrantyService().saveOrUpdateWarrantyPrint((WarrantyPrint[]) toDoObj);
            return;
        }
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
        if (ShopContract.class.getSimpleName().equals(domainType)) {
            getShopService().saveOrUpdateShopContracts((ShopContract[]) toDoObj);
            return;
        }
        if (PartsLibrary.class.getSimpleName().equals(domainType)) {
            getShopService().saveOrUpdatePartsLibrary((PartsLibrary[]) toDoObj);
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
    @SuppressWarnings("unchecked")
    public static void export(String domainType, List dataList, File targetFile) {
        if (ComponentBorrowing.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getTsService()).export2Excel(dataList, targetFile, ComponentBorrowing.class);
            return;
        }
        if (ToolRecipients.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getTsService()).export2Excel(dataList, targetFile, ToolRecipients.class);
            return;
        }
        if (NewMachineClaim.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getTsService()).export2Excel(dataList, targetFile, NewMachineClaim.class);
            return;
        }
        if (OldMachineRenew.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getTsService()).export2Excel(dataList, targetFile, OldMachineRenew.class);
            return;
        }
        if (TSInstallPermission.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getTsService()).export2Excel(dataList, targetFile, TSInstallPermission.class);
            return;
        }
        if (TSSDSPermission.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getTsService()).export2Excel(dataList, targetFile, TSSDSPermission.class);
            return;
        }
        if (TSManualPermissions.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getTsService()).export2Excel(dataList, targetFile, TSManualPermissions.class);
            return;
        }
        if (TSTraining.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getTsService()).export2Excel(dataList, targetFile, TSTraining.class);
            return;
        }
        if (TechInstallPermission.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getTechService()).export2Excel(dataList, targetFile, TechInstallPermission.class);
            return;
        }
        if (TechSupport.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getTechService()).export2Excel(dataList, targetFile, TechSupport.class);
            return;
        }
        if (TechSDSPermission.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getTechService()).export2Excel(dataList, targetFile, TechSDSPermission.class);
            return;
        }
        if (SOIDCode.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getTechService()).export2Excel(dataList, targetFile, SOIDCode.class);
            return;
        }
        if (TechManualPermissions.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getTechService()).export2Excel(dataList, targetFile, TechManualPermissions.class);
            return;
        }
        if (WarrantyConsumables.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getWarrantyService()).export2Excel(dataList, targetFile, WarrantyConsumables.class);
            return;
        }
        if (WarrantyPrint.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getWarrantyService()).export2Excel(dataList, targetFile, WarrantyPrint.class);
            return;
        }
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
        if (ShopContract.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getShopService()).export2Excel(dataList, targetFile, ShopContract.class);
            return;
        }
        if (ShopPay.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getShopService()).export2Excel(dataList, targetFile, ShopPay.class);
            return;
        }
        if (PartsLibrary.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getShopService()).export2Excel(dataList, targetFile, PartsLibrary.class);
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
        if (ComponentBorrowing.class.getSimpleName().equals(domainType)) {
            getTsService().deleteComponentBorrowing(deleted);
            return;
        }
        if (ToolRecipients.class.getSimpleName().equals(domainType)) {
            getTsService().deleteToolRecipients(deleted);
            return;
        }
        if (NewMachineClaim.class.getSimpleName().equals(domainType)) {
            getTsService().deleteNewMachineClaim(deleted);
            return;
        }
        if (OldMachineRenew.class.getSimpleName().equals(domainType)) {
            getTsService().deleteOldMachineRenew(deleted);
            return;
        }
        if (TSInstallPermission.class.getSimpleName().equals(domainType)) {
            getTsService().deleteTSInstallPermission(deleted);
            return;
        }
        if (TSSDSPermission.class.getSimpleName().equals(domainType)) {
            getTsService().deleteTSSDSPermission(deleted);
            return;
        }
        if (TSManualPermissions.class.getSimpleName().equals(domainType)) {
            getTsService().deleteTSManualPermission(deleted);
            return;
        }
        if (TSTraining.class.getSimpleName().equals(domainType)) {
            getTsService().deleteTSTraining(deleted);
            return;
        }
        if (TechInstallPermission.class.getSimpleName().equals(domainType)) {
            getTechService().deleteInstallPermission(deleted);
            return;
        }
        if (TechSupport.class.getSimpleName().equals(domainType)) {
            getTechService().deleteTechSupport(deleted);
            return;
        }
        if (TechSDSPermission.class.getSimpleName().equals(domainType)) {
            getTechService().deleteSDSPermissions(deleted);
            return;
        }
        if (SOIDCode.class.getSimpleName().equals(domainType)) {
            getTechService().deleteSOIDCode(deleted);
            return;
        }
        if (TechManualPermissions.class.getSimpleName().equals(domainType)) {
            getTechService().deleteManualPermissions(deleted);
            return;
        }
        if (WarrantyConsumables.class.getSimpleName().equals(domainType)) {
            getWarrantyService().deleteWarrantyConsumables(deleted);
            return;
        }
        if (WarrantyPrint.class.getSimpleName().equals(domainType)) {
            getWarrantyService().deleteWarrantyPrint(deleted);
            return;
        }
        if (PartsLibrary.class.getSimpleName().equals(domainType)) {
            getShopService().deletePartsLibrary(deleted);
            return;
        }
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
        if (ShopContract.class.getSimpleName().equals(domainType)) {
            getShopService().deleteShopContract(deleted);
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
