package kaitou.ppp.app.ui.table;

import kaitou.ppp.app.SpringContextManager;
import kaitou.ppp.app.ui.dialog.BaseSaveDialog;
import kaitou.ppp.app.ui.dialog.OperationHint;
import kaitou.ppp.app.ui.dialog.add.SaveDialog;
import kaitou.ppp.app.ui.dialog.add.TechInstallPermissionSaveDialog;
import kaitou.ppp.app.ui.table.queryobject.IQueryObject;
import kaitou.ppp.app.ui.table.queryobject.tech.InstallPermissionQueryObject;
import kaitou.ppp.dao.support.Condition;
import kaitou.ppp.dao.support.Pager;
import kaitou.ppp.domain.BaseDomain;
import kaitou.ppp.domain.basic.Models;
import kaitou.ppp.domain.card.CardApplicationRecord;
import kaitou.ppp.domain.engineer.Engineer;
import kaitou.ppp.domain.engineer.EngineerTraining;
import kaitou.ppp.domain.shop.*;
import kaitou.ppp.domain.system.IPRegistry;
import kaitou.ppp.domain.system.OperationLog;
import kaitou.ppp.domain.tech.*;
import kaitou.ppp.domain.ts.*;
import kaitou.ppp.domain.warranty.*;
import kaitou.ppp.service.BaseExcelService;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

import static kaitou.ppp.app.ui.UIUtil.handleEx;

/**
 * 操作管理.
 * User: 赵立伟
 * Date: 2015/5/13
 * Time: 11:38
 */
public abstract class OPManager extends SpringContextManager {

    /**
     * 保存界面
     * <p>
     * 可以自定义。默认是SaveDialog
     * </p>
     *
     * @param queryObject 查询对象
     * @param frame       窗体
     * @return 保存界面
     * @see kaitou.ppp.app.ui.dialog.BaseSaveDialog
     * @see kaitou.ppp.app.ui.dialog.add.SaveDialog
     */
    public static <T extends BaseDomain> BaseSaveDialog<T> wannaSave(IQueryObject<T> queryObject, JFrame frame) {
        if (TechInstallPermission.class.getSimpleName().equals(queryObject.domainType())) {
            return (BaseSaveDialog<T>) new TechInstallPermissionSaveDialog(frame, (InstallPermissionQueryObject) queryObject);
        }
        return new SaveDialog<T>(frame, queryObject);
    }

    /**
     * 公共保存/更新接口
     *
     * @param domainType 领域类型
     * @param toDoObj    待操作对象
     */
    public static void saveOrUpdate(String domainType, Object... toDoObj) {
        if (IPRegistry.class.getSimpleName().equals(domainType)) {
            getLocalRegistryService().saveOrUpdateIPRegistry((IPRegistry[]) toDoObj);
            return;
        }
        if (Models.class.getSimpleName().equals(domainType)) {
            getBasicService().saveOrUpdateBasicModels((Models[]) toDoObj);
            return;
        }
        if (EngineerTS.class.getSimpleName().equals(domainType)) {
            getTsService().saveOrUpdateTSEngineer((EngineerTS[]) toDoObj);
            return;
        }
        if (TSDongle.class.getSimpleName().equals(domainType)) {
            getTsService().saveOrUpdateTSDongles((TSDongle[]) toDoObj);
            return;
        }
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
        if (TechDongle.class.getSimpleName().equals(domainType)) {
            getTechService().saveOrUpdateTechDongles((TechDongle[]) toDoObj);
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
        if (IpfEquipment.class.getSimpleName().equals(domainType)) {
            getWarrantyService().saveOrUpdateIpfEquipment((IpfEquipment[]) toDoObj);
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
     * 公共导出导入模板接口
     *
     * @param domainType 领域类型
     * @param targetFile 目标文件
     */
    public static void exportImportModel(String domainType, File targetFile) {
        if (Models.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getBasicService()).exportImportModel(targetFile, Models.class);
            return;
        }
        if (EngineerTS.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getTsService()).exportImportModel(targetFile, EngineerTS.class);
            return;
        }
        if (TSDongle.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getTsService()).exportImportModel(targetFile, TSDongle.class);
            return;
        }
        if (ComponentBorrowing.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getTsService()).exportImportModel(targetFile, ComponentBorrowing.class);
            return;
        }
        if (ToolRecipients.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getTsService()).exportImportModel(targetFile, ToolRecipients.class);
            return;
        }
        if (NewMachineClaim.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getTsService()).exportImportModel(targetFile, NewMachineClaim.class);
            return;
        }
        if (OldMachineRenew.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getTsService()).exportImportModel(targetFile, OldMachineRenew.class);
            return;
        }
        if (TSInstallPermission.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getTsService()).exportImportModel(targetFile, TSInstallPermission.class);
            return;
        }
        if (TSSDSPermission.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getTsService()).exportImportModel(targetFile, TSSDSPermission.class);
            return;
        }
        if (TSManualPermissions.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getTsService()).exportImportModel(targetFile, TSManualPermissions.class);
            return;
        }
        if (TSTraining.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getTsService()).exportImportModel(targetFile, TSTraining.class);
            return;
        }
        if (TechDongle.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getTechService()).exportImportModel(targetFile, TechDongle.class);
            return;
        }
        if (TechInstallPermission.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getTechService()).exportImportModel(targetFile, TechInstallPermission.class);
            return;
        }
        if (TechSupport.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getTechService()).exportImportModel(targetFile, TechSupport.class);
            return;
        }
        if (TechSDSPermission.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getTechService()).exportImportModel(targetFile, TechSDSPermission.class);
            return;
        }
        if (SOIDCode.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getTechService()).exportImportModel(targetFile, SOIDCode.class);
            return;
        }
        if (TechManualPermissions.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getTechService()).exportImportModel(targetFile, TechManualPermissions.class);
            return;
        }
        if (IpfEquipment.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getWarrantyService()).exportImportModel(targetFile, IpfEquipment.class);
            return;
        }
        if (WarrantyConsumables.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getWarrantyService()).exportImportModel(targetFile, WarrantyConsumables.class);
            return;
        }
        if (WarrantyPrint.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getWarrantyService()).exportImportModel(targetFile, WarrantyPrint.class);
            return;
        }
        if (WarrantyParts.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getWarrantyService()).exportImportModel(targetFile, WarrantyParts.class);
            return;
        }
        if (WarrantyFee.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getWarrantyService()).exportImportModel(targetFile, WarrantyFee.class);
            return;
        }
        if (Shop.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getShopService()).exportImportModel(targetFile, Shop.class);
            return;
        }
        if (ShopDetail.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getShopService()).exportImportModel(targetFile, ShopDetail.class);
            return;
        }
        if (ShopRTS.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getShopService()).exportImportModel(targetFile, ShopRTS.class);
            return;
        }
        if (ShopContract.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getShopService()).exportImportModel(targetFile, ShopContract.class);
            return;
        }
        if (ShopPay.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getShopService()).exportImportModel(targetFile, ShopPay.class);
            return;
        }
        if (PartsLibrary.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getShopService()).exportImportModel(targetFile, PartsLibrary.class);
            return;
        }
        if (Engineer.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getEngineerService()).exportImportModel(targetFile, Engineer.class);
            return;
        }
        if (EngineerTraining.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getEngineerService()).exportImportModel(targetFile, EngineerTraining.class);
            return;
        }
        if (CardApplicationRecord.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getEngineerService()).exportImportModel(targetFile, CardApplicationRecord.class);
            return;
        }
        throw new RuntimeException("尚未支持此类型导出：" + domainType);
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
        if (Models.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getBasicService()).export2Excel(dataList, targetFile, Models.class);
            return;
        }
        if (EngineerTS.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getTsService()).export2Excel(dataList, targetFile, EngineerTS.class);
            return;
        }
        if (TSDongle.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getTsService()).export2Excel(dataList, targetFile, TSDongle.class);
            return;
        }
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
        if (TechDongle.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getTechService()).export2Excel(dataList, targetFile, TechDongle.class);
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
        if (IpfEquipment.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getWarrantyService()).export2Excel(dataList, targetFile, IpfEquipment.class);
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
        if (IPRegistry.class.getSimpleName().equals(domainType)) {
            getLocalRegistryService().deleteIPRegistry(deleted);
            return;
        }
        if (Models.class.getSimpleName().equals(domainType)) {
            getBasicService().deleteBasicModels(deleted);
            return;
        }
        if (TSDongle.class.getSimpleName().equals(domainType)) {
            getTsService().deleteTSDongles(deleted);
            return;
        }
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
        if (EngineerTS.class.getSimpleName().equals(domainType)) {
            getTsService().deleteTSEngineer(deleted);
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
        if (TechDongle.class.getSimpleName().equals(domainType)) {
            getTechService().deleteTechDongles(deleted);
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
        if (IpfEquipment.class.getSimpleName().equals(domainType)) {
            getWarrantyService().deleteIpfEquipment(deleted);
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

    /**
     * 公共查询接口
     *
     * @param domainType 领域类型
     * @param <T>        对象类型
     * @return 对象列表
     */
    public static <T extends BaseDomain> List<T> query(String domainType) {
        if (OperationLog.class.getSimpleName().equals(domainType)) {
            return (List<T>) getSystemSettingsService().queryTodayOperationLogs();
        }
        if (IPRegistry.class.getSimpleName().equals(domainType)) {
            return (List<T>) getLocalRegistryService().queryIPRegistry();
        }
        if (Models.class.getSimpleName().equals(domainType)) {
            return (List<T>) getBasicService().queryBasicModels();
        }
        if (EngineerTS.class.getSimpleName().equals(domainType)) {
            return (List<T>) getTsService().queryTSEngineer();
        }
        if (TSDongle.class.getSimpleName().equals(domainType)) {
            return (List<T>) getTsService().queryTSDongles();
        }
        if (ComponentBorrowing.class.getSimpleName().equals(domainType)) {
            return (List<T>) getTsService().queryComponentBorrowing();
        }
        if (ToolRecipients.class.getSimpleName().equals(domainType)) {
            return (List<T>) getTsService().queryToolRecipients();
        }
        if (NewMachineClaim.class.getSimpleName().equals(domainType)) {
            return (List<T>) getTsService().queryNewMachineClaim();
        }
        if (OldMachineRenew.class.getSimpleName().equals(domainType)) {
            return (List<T>) getTsService().queryOldMachineRenew();
        }
        if (TSInstallPermission.class.getSimpleName().equals(domainType)) {
            return (List<T>) getTsService().queryTSInstallPermission();
        }
        if (TSSDSPermission.class.getSimpleName().equals(domainType)) {
            return (List<T>) getTsService().queryTSSDSPermission();
        }
        if (TSManualPermissions.class.getSimpleName().equals(domainType)) {
            return (List<T>) getTsService().queryTSManualPermission();
        }
        if (TSTraining.class.getSimpleName().equals(domainType)) {
            return (List<T>) getTsService().queryTSTraining();
        }
        if (TechDongle.class.getSimpleName().equals(domainType)) {
            return (List<T>) getTechService().queryTechDongles();
        }
        if (TechInstallPermission.class.getSimpleName().equals(domainType)) {
            return (List<T>) getTechService().queryInstallPermission();
        }
        if (TechSupport.class.getSimpleName().equals(domainType)) {
            return (List<T>) getTechService().queryTechSupport();
        }
        if (TechSDSPermission.class.getSimpleName().equals(domainType)) {
            return (List<T>) getTechService().querySDSPermissions();
        }
        if (SOIDCode.class.getSimpleName().equals(domainType)) {
            return (List<T>) getTechService().querySOIDCode();
        }
        if (TechManualPermissions.class.getSimpleName().equals(domainType)) {
            return (List<T>) getTechService().queryManualPermissions();
        }
        if (IpfEquipment.class.getSimpleName().equals(domainType)) {
            return (List<T>) getWarrantyService().queryIpfEquipment();
        }
        if (WarrantyConsumables.class.getSimpleName().equals(domainType)) {
            return (List<T>) getWarrantyService().queryWarrantyConsumables();
        }
        if (WarrantyPrint.class.getSimpleName().equals(domainType)) {
            return (List<T>) getWarrantyService().queryWarrantyPrint();
        }
        if (WarrantyParts.class.getSimpleName().equals(domainType)) {
            return (List<T>) getWarrantyService().queryWarrantyParts();
        }
        if (WarrantyFee.class.getSimpleName().equals(domainType)) {
            return (List<T>) getWarrantyService().queryWarrantyFee();
        }
        if (Shop.class.getSimpleName().equals(domainType)) {
            return (List<T>) getShopService().queryAllShops();
        }
        if (ShopDetail.class.getSimpleName().equals(domainType)) {
            return (List<T>) getShopService().queryAllDetails();
        }
        if (ShopRTS.class.getSimpleName().equals(domainType)) {
            return (List<T>) getShopService().queryAllRTSs();
        }
        if (ShopContract.class.getSimpleName().equals(domainType)) {
            return (List<T>) getShopService().queryShopContracts();
        }
        if (ShopPay.class.getSimpleName().equals(domainType)) {
            return (List<T>) getShopService().queryAllPays();
        }
        if (PartsLibrary.class.getSimpleName().equals(domainType)) {
            return (List<T>) getShopService().queryPartsLibrary();
        }
        if (Engineer.class.getSimpleName().equals(domainType)) {
            return (List<T>) getEngineerService().queryAllEngineers();
        }
        if (EngineerTraining.class.getSimpleName().equals(domainType)) {
            return (List<T>) getEngineerService().queryAllTrainings();
        }
        if (CardApplicationRecord.class.getSimpleName().equals(domainType)) {
            return (List<T>) getCardService().queryCardApplicationRecords();
        }
        throw new RuntimeException("尚未支持此类型导出：" + domainType);
    }

    /**
     * 公共分页查询接口
     *
     * @param domainType  领域类型
     * @param currentPage 当前页码
     * @param conditions  查询条件列表
     * @param <T>         对象类型
     * @return 封装后的分页对象
     */
    public static <T extends BaseDomain> Pager<T> queryPager(String domainType, int currentPage, List<Condition> conditions) {
        if (Models.class.getSimpleName().equals(domainType)) {
            return (Pager<T>) getBasicService().queryBasicModels(currentPage, conditions);
        }
        if (TechDongle.class.getSimpleName().equals(domainType)) {
            return (Pager<T>) getTechService().queryTechDonglePager(currentPage, conditions);
        }
        if (EngineerTS.class.getSimpleName().equals(domainType)) {
            return (Pager<T>) getTsService().queryTSEngineer(currentPage, conditions);
        }
        if (TSSDSPermission.class.getSimpleName().equals(domainType)) {
            return (Pager<T>) getTsService().queryTSSDSPermissionPager(currentPage, conditions);
        }
        if (TSDongle.class.getSimpleName().equals(domainType)) {
            return (Pager<T>) getTsService().queryTSDonglePager(currentPage, conditions);
        }
        if (IpfEquipment.class.getSimpleName().equals(domainType)) {
            return (Pager<T>) getWarrantyService().queryIpfEquipmentPager(currentPage, conditions);
        }
        if (WarrantyParts.class.getSimpleName().equals(domainType)) {
            return (Pager<T>) getWarrantyService().queryWarrantyPartsPager(currentPage, conditions);
        }
        if (CardApplicationRecord.class.getSimpleName().equals(domainType)) {
            return (Pager<T>) getCardService().queryCardApplicationRecordPager(currentPage, conditions);
        }
        throw new RuntimeException("尚未支持此类型导出：" + domainType);
    }

    /**
     * 公共不分页查询接口
     *
     * @param domainType 领域类型
     * @param conditions 查询条件列表
     * @param <T>        对象类型
     * @return 结果集
     */
    public static <T extends BaseDomain> List<T> queryAll(String domainType, List<Condition> conditions) {
        if (Models.class.getSimpleName().equals(domainType)) {
            return (List<T>) getBasicService().queryBasicModels(conditions);
        }
        if (TechDongle.class.getSimpleName().equals(domainType)) {
            return (List<T>) getTechService().queryTechDongle(conditions);
        }
        if (EngineerTS.class.getSimpleName().equals(domainType)) {
            return (List<T>) getTsService().queryTSEngineer(conditions);
        }
        if (TSSDSPermission.class.getSimpleName().equals(domainType)) {
            return (List<T>) getTsService().queryTSSDSPermission(conditions);
        }
        if (TSDongle.class.getSimpleName().equals(domainType)) {
            return (List<T>) getTsService().queryTSDongle(conditions);
        }
        if (IpfEquipment.class.getSimpleName().equals(domainType)) {
            return (List<T>) getWarrantyService().queryIpfEquipment(conditions);
        }
        if (WarrantyParts.class.getSimpleName().equals(domainType)) {
            return (List<T>) getWarrantyService().queryWarrantyParts(conditions);
        }
        if (CardApplicationRecord.class.getSimpleName().equals(domainType)) {
            return (List<T>) getCardService().queryCardApplicationRecord(conditions);
        }
        throw new RuntimeException("尚未支持此类型导出：" + domainType);
    }

    /**
     * 带统一的异常处理的执行
     *
     * @param frame    窗体
     * @param runnable 执行体
     */
    public static void doRunWithExHandler(JFrame frame, OpRunnable runnable) {
        try {
            runnable.run();
        } catch (Exception ex) {
            handleEx(ex, frame);
        }
    }

    /**
     * 带等待提示的执行
     *
     * @param frame       窗体
     * @param runnable    执行体
     * @param successHint 成功提示。为空则不提示
     */
    public static void doRunWithWaiting(JFrame frame, OpRunnable runnable, String successHint) {
        String title = frame.getTitle();
        frame.setTitle(title + " 正在操作，请稍候......");
        frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            runnable.run();
            if (StringUtils.isNotEmpty(successHint)) {
                new OperationHint(frame, successHint);
            }
        } catch (Exception e) {
            handleEx(e, frame);
        }
        frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        frame.setTitle(title);
    }

    /**
     * 执行体
     */
    public interface OpRunnable {
        /**
         * 执行逻辑
         */
        public abstract void run();
    }
}
