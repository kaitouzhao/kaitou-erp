package kaitou.ppp.service.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.dao.support.Condition;
import kaitou.ppp.dao.support.Pager;
import kaitou.ppp.domain.basic.Models;
import kaitou.ppp.domain.card.CardApplication;
import kaitou.ppp.domain.card.CardApplicationRecord;
import kaitou.ppp.domain.system.SysCode;
import kaitou.ppp.domain.warranty.WarrantyFee;
import kaitou.ppp.manager.basic.ModelsManager;
import kaitou.ppp.manager.card.CardApplicationRecordManager;
import kaitou.ppp.manager.shop.ShopManager;
import kaitou.ppp.manager.warranty.WarrantyFeeManager;
import kaitou.ppp.rmi.service.RemoteCardService;
import kaitou.ppp.service.BaseExcelService;
import kaitou.ppp.service.CardService;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;

import java.io.*;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.*;

import static com.womai.bsp.tool.utils.BeanCopyUtil.copyBean;
import static kaitou.ppp.domain.system.SysCode.*;
import static kaitou.ppp.service.ServiceInvokeManager.*;

/**
 * 保修卡处理业务层实现.
 * User: 赵立伟
 * Date: 2015/1/25
 * Time: 22:46
 */
public class CardServiceImpl extends BaseExcelService implements CardService {

    private static final String APPLICATION_SHEET_NAME = "申请表";

    private String workspace;
    private String complete;
    private String output;
    private String logFileName;
    private String templateName;
    private String template;

    private ShopManager shopManager;
    private ModelsManager modelsManager;
    private WarrantyFeeManager warrantyFeeManager;
    private CardApplicationRecordManager cardApplicationRecordManager;

    public void setShopManager(ShopManager shopManager) {
        this.shopManager = shopManager;
    }

    public void setModelsManager(ModelsManager modelsManager) {
        this.modelsManager = modelsManager;
    }

    public void setWarrantyFeeManager(WarrantyFeeManager warrantyFeeManager) {
        this.warrantyFeeManager = warrantyFeeManager;
    }

    public void setCardApplicationRecordManager(CardApplicationRecordManager cardApplicationRecordManager) {
        this.cardApplicationRecordManager = cardApplicationRecordManager;
    }

    public void setWorkspace(String workspace) {
        this.workspace = workspace;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public void setLogFileName(String logFileName) {
        this.logFileName = logFileName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    @Override
    public void generateCards() {
        String workspacePath = workspace;
        File workspace = new File(workspacePath);
        File[] appFiles = workspace.listFiles();
        if (appFiles == null) {
            return;
        }
        for (File appFile : appFiles) {
            Workbook workbook = create(appFile);
            Sheet sheet = workbook.getSheet(APPLICATION_SHEET_NAME);
            if (sheet == null) {
                throw new RuntimeException(appFile.getName() + " 找不到sheet：" + APPLICATION_SHEET_NAME);
            }
        }
        String completePath = complete;
        List<Object[]> rowDataList = new ArrayList<Object[]>();
        List<Object[]> sheetDataList = new ArrayList<Object[]>();
        File log = new File(output + logFileName);
        DecimalFormat df = new DecimalFormat("00000");
        DateTime now = new DateTime();
        List<CardApplication> applications = new ArrayList<CardApplication>();
        List<CardApplicationRecord> cardApplicationRecords = new ArrayList<CardApplicationRecord>();
        for (File appFile : appFiles) {
            Workbook workbook = create(appFile);
            Sheet sheet = workbook.getSheet(APPLICATION_SHEET_NAME);
            CardApplication application = new CardApplication();
            application.fill(sheet);
            ModelCode code = ModelCode.getCode(application.getModels().substring(0, 1));
            String lastWarrantyCard = getLastRowCellStrValue(log, code.getCardNoPref(), 3, CellType.STRING);
            long warrantyCardIndex = 0;
            if (lastWarrantyCard != null && !"".equals(lastWarrantyCard.trim())) {
                warrantyCardIndex = Long.valueOf(lastWarrantyCard.substring(5));
            }
            application.setWarrantyCard(code.getCardNoPref() + "-A" + df.format(++warrantyCardIndex));
            application.setApplyDate(now.toString(WarrantyStatus.DATE_FORMAT_YYYY_MM_DD));
            application.setInstalledDate(WarrantyStatus.convert2Standard(application.getInstalledDate()));
            application.setEndDate(WarrantyStatus.getEndDateStr(application.getInstalledDate()));
            application.setStatus(WarrantyStatus.getStatus(application.getInstalledDate()));
            application.setAllModels(code.getModels());
            application.setInitData(application.getInitData() + code.getReadUnit());
            logSystemInfo(application.toString());
            applications.add(application);
            CardApplicationRecord cardApplicationRecord = new CardApplicationRecord();
            copyBean(application, cardApplicationRecord);
            String shopName = application.getServiceCompanyName();
            cardApplicationRecord.setShopName(shopName);
            cardApplicationRecord.setShopId(shopManager.getCachedIdByName(shopName));
            Models models = modelsManager.getByModel(cardApplicationRecord.getModels());
            if (models != null) {
                cardApplicationRecord.setModelType(models.getModelType());
            }
            cardApplicationRecords.add(cardApplicationRecord);
            rowDataList.add(application.getAllRowData());
            sheetDataList.add(application.getRowData());
            add2Sheet(log, code.getCardNoPref(), sheetDataList);
            sheetDataList.clear();
            boolean b = appFile.renameTo(new File(completePath + appFile.getName()));
            if (!b) {
                throw new RuntimeException("生成保修卡失败");
            }
        }
        add2Sheet(log, "汇总", rowDataList);
        saveOrUpdateCardApplicationRecord(CollectionUtil.toArray(cardApplicationRecords, CardApplicationRecord.class));
        logOperation("成功导入/更新保修卡生成记录数：" + cardApplicationRecords.size());
        transform2Xls(applications, templateName);
    }

    /**
     * 根据模板与数据转换excel
     *
     * @param applications 申请集合
     * @param templateName 模板名
     */
    private void transform2Xls(List<CardApplication> applications, String templateName) {
        try {
            String templatePath = template + templateName;
//        String targetPath = getPath("output") + new DateTime().toString("yyyy_MM_dd") + ".xls";
//        multipleTransform(applications, templatePath, targetPath);
            Workbook wb;
            for (CardApplication application : applications) {
                String targetPath = output + application.getNewSheetName() + ".xls";
                wb = transform2Workbook(application, templatePath);
                OutputStream os = new BufferedOutputStream(new FileOutputStream(targetPath));
                wb.write(os);
                os.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 单个转换
     *
     * @param application  申请
     * @param templatePath 模板
     * @return 表
     */
    private Workbook transform2Workbook(CardApplication application, String templatePath) {
        try {
            List<String> sheetNames = new ArrayList<String>();
            List<Map<String, Object>> fieldMap = new ArrayList<Map<String, Object>>();
            sheetNames.add(application.getNewSheetName());
            fieldMap.add(application.field2Map());
            File templateFile = new File(templatePath);
            InputStream is = new BufferedInputStream(new FileInputStream(templateFile));
            XLSTransformer transformer = new XLSTransformer();
            return transformer.transformMultipleSheetsList(is, fieldMap, sheetNames, "results_JxLsC_", new HashMap(), 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 根据不同版本excel文件创建工作簿
     *
     * @param file 文件
     * @return 工作簿
     */
    private Workbook create(File file) {
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            if (!is.markSupported()) {
                is = new PushbackInputStream(is, 8);
            }
            if (POIFSFileSystem.hasPOIFSHeader(is)) {
                return new HSSFWorkbook(is);
            }
            if (POIXMLDocument.hasOOXMLHeader(is)) {
                return new XSSFWorkbook(OPCPackage.open(is));
            }
            throw new RuntimeException("你的excel版本目前poi解析不了");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logSystemEx(e);
                }
            }
        }
    }

    /**
     * 获取最后一行单元格数据
     *
     * @param file        excel文件
     * @param sheetName   工作单元名
     * @param columnIndex 列序号
     * @param type        类型
     * @return 单元格数据
     */
    private String getLastRowCellStrValue(File file, String sheetName, int columnIndex, CellType type) {
        Workbook workbook = create(file);
        Sheet sheet = workbook.getSheet(sheetName);
        int lastRowNum = sheet.getLastRowNum();
        if (lastRowNum < 1) {
            return "";
        }
        Row row = sheet.getRow(lastRowNum);
        return getStringCellValue(row.getCell(columnIndex), type);
    }

    /**
     * 将单元格数据转换成字符串
     *
     * @param cell 单元格
     * @param type 类型
     * @return 字符串
     */
    private String getStringCellValue(Cell cell, CellType type) {
        DecimalFormat df = new DecimalFormat("0");
        try {
            switch (type.getValue()) {
                case CELL_TYPE_STRING:
                    return cell.getStringCellValue();
                case CELL_TYPE_DATE:
                    return new DateTime(cell.getDateCellValue().getTime()).toString("yyyy/MM/dd");
                case CELL_TYPE_NUMERIC:
                    return df.format(cell.getNumericCellValue());
                default:
                    return "";
            }
        } catch (Exception e) {
            if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
                return df.format(cell.getNumericCellValue());
            }
            return "";
        }
    }

    /**
     * 加入到excel文件
     *
     * @param file      excel文件
     * @param sheetName 工作单元名
     * @param datas     新数据
     * @return 总行数
     */
    private int add2Sheet(File file, String sheetName, List<Object[]> datas) {
        try {
            Workbook workbook = create(file);
            Sheet sheet = workbook.getSheet(sheetName);
            int allRow = sheet.getLastRowNum();
            Row row;
            for (Object[] data : datas) {
                if (data == null || data.length <= 0) {
                    continue;
                }
                row = sheet.createRow(++allRow);
                for (int j = 0; j < data.length; j++) {
                    row.createCell(j).setCellValue(String.valueOf(data[j] == null ? "" : data[j]));
                }
            }
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(file);
                workbook.write(out);
                out.flush();
            } finally {
                if (out != null) {
                    out.close();
                }
            }
            return allRow;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void importCardApplicationRecords(File srcFile) {
        debugTime("开始导入");
        List<CardApplicationRecord> cardApplicationRecords = readFromExcel(srcFile, CardApplicationRecord.class);
        debugTime("获取数据");
        saveOrUpdateCardApplicationRecord(CollectionUtil.toArray(cardApplicationRecords, CardApplicationRecord.class));
    }

    @Override
    public void exportCardApplicationRecords(File targetFile) {
        export2Excel(cardApplicationRecordManager.query(), targetFile, CardApplicationRecord.class);
    }

    @Override
    public List<CardApplicationRecord> queryCardApplicationRecords() {
        List<CardApplicationRecord> cardApplicationRecords = cardApplicationRecordManager.query();
        for (CardApplicationRecord cardApplicationRecord : cardApplicationRecords) {
            cardApplicationRecord.setStatus(WarrantyStatus.getStatus(cardApplicationRecord.getInstalledDate()));
        }
        return cardApplicationRecords;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void saveOrUpdateCardApplicationRecord(final CardApplicationRecord... cardApplicationRecords) {
        final List<CardApplicationRecord> cardApplicationRecordList = new ArrayList<CardApplicationRecord>();
        List<WarrantyFee> warrantyFeeList = warrantyFeeManager.query();
        Collections.sort(warrantyFeeList, new Comparator<WarrantyFee>() {
            @Override
            public int compare(WarrantyFee o1, WarrantyFee o2) {
                return o1.comparator(o2);
            }
        });
        for (CardApplicationRecord cardApplicationRecord : cardApplicationRecords) {
            String shopId = shopManager.getCachedIdByName(cardApplicationRecord.getShopName());
            if (StringUtils.isNotEmpty(shopId)) {
                cardApplicationRecord.setShopId(shopId);
                cardApplicationRecord.setSaleRegion(shopManager.getCachedShop(shopId).getSaleRegion());
            }
            for (WarrantyFee warrantyFee : warrantyFeeList) {
                if (cardApplicationRecord.getFuselage().equals(warrantyFee.getFuselage())) {
                    if (StringUtils.isEmpty(cardApplicationRecord.getIsBack())) {
                        cardApplicationRecord.setIsBack(warrantyFee.checkCardApplicationRecordIsBack());
                    }
                    if (warrantyFee.checkCardApplicationRecordIsOutOfWarranty() && !SysCode.WarrantyStatus.OUT_WARRANTY.getValue().equals(cardApplicationRecord.getStatus())) {
                        cardApplicationRecord.setStatus(SysCode.WarrantyStatus.OUT_WARRANTY.getValue());
                    }
                }
            }
            cardApplicationRecordList.add(cardApplicationRecord);
        }
        debugTime("填充认定店编号");
        logOperation("成功导入/更新保修卡生成记录数：" + cardApplicationRecordManager.save(cardApplicationRecordList));
        debugTime("保存保修卡");
        asynchronousRun(new InvokeRunnable() {
            @Override
            public void run() throws RemoteException {
                List<RemoteCardService> remoteCardServices = queryRemoteService(RemoteCardService.class);
                logOperation("通知已注册的远程服务更新保修卡记录");
                for (RemoteCardService remoteCardService : remoteCardServices) {
                    remoteCardService.saveCardApplicationRecord(cardApplicationRecordList);
                }
            }
        });
    }

    @Override
    public void deleteCardApplicationRecords(final Object... cardApplicationRecords) {
        logOperation("成功删除保修卡生成记录个数：" + cardApplicationRecordManager.delete(cardApplicationRecords));
        asynchronousRun(new InvokeRunnable() {
            @Override
            public void run() throws RemoteException {
                List<RemoteCardService> remoteCardServices = queryRemoteService(RemoteCardService.class);
                logOperation("通知已注册的远程服务更新删除保修卡记录");
                for (RemoteCardService remoteCardService : remoteCardServices) {
                    remoteCardService.deleteCardApplicationRecord(cardApplicationRecords);
                }
            }
        });
    }

    @Override
    public Pager<CardApplicationRecord> queryCardApplicationRecordPager(int currentPage, List<Condition> conditions) {
        Pager<CardApplicationRecord> pager = cardApplicationRecordManager.queryPager(currentPage, conditions);
        List<CardApplicationRecord> cardApplicationRecords = pager.getResult();
        for (CardApplicationRecord cardApplicationRecord : cardApplicationRecords) {
            if (WarrantyStatus.OUT_WARRANTY.getValue().equals(cardApplicationRecord.getStatus())) {
                continue;
            }
            cardApplicationRecord.setStatus(WarrantyStatus.getStatus(cardApplicationRecord.getInstalledDate()));
        }
        return pager;
    }

    @Override
    public List<CardApplicationRecord> queryCardApplicationRecord(List<Condition> conditions) {
        return cardApplicationRecordManager.queryAll(conditions);
    }
}
