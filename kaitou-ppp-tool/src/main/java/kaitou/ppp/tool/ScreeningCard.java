package kaitou.ppp.tool;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;

import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import static org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted;

/**
 * 筛选保修卡.
 * User: 赵立伟
 * Date: 2015/7/1
 * Time: 16:45
 */
public class ScreeningCard {
    /**
     * 主程序入口
     *
     * @param args 参数
     */
    public static void main(String[] args) throws FileNotFoundException {
        File srcFile = new File("C:\\Users\\zhao\\Desktop\\PPP\\需求\\保修卡汇总表.xls");
        InputStream is = new FileInputStream(srcFile);
        HSSFSheet sheet = ((HSSFWorkbook) createWorkbook(is)).getSheet("汇总");
        HSSFRow row;
        int startReadRowNum = 1;
        int stopReadRowNum = sheet.getLastRowNum() + 1;
        Map<String, String> result = new HashMap<String, String>();
        for (int i = startReadRowNum; i < stopReadRowNum; i++) {
            row = sheet.getRow(i);
            HSSFCell cell = row.getCell(6);
            HSSFComment cellComment = cell.getCellComment();
            if (cellComment == null) {
                continue;
            }
            String comment = cellComment.getString().toString();
            result.put(getStringCellValue(cell), comment.replace(cellComment.getAuthor(), ""));
        }
        System.out.println("true");
    }

    /**
     * 转换数字
     * <p>如果小数位是0，则输出不带小数位</p>
     *
     * @param data double型输入
     * @return 转换后的字符串
     */
    private static String convertNumber(double data) {
        BigDecimal bigDecimal = new BigDecimal(data);
        String noScientific = bigDecimal.toPlainString();
        if (!noScientific.contains(".")) {
            return noScientific;
        }
        return new DecimalFormat("0.00").format(new Double(noScientific));
    }

    /**
     * 获取字符型单元格数据
     *
     * @param cell 单元格
     * @return 字符型数据
     */
    private static String getStringCellValue(Cell cell) {
        try {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    return cell.getStringCellValue();
                case Cell.CELL_TYPE_NUMERIC:
                    if (isCellDateFormatted(cell)) {
                        return new DateTime(cell.getDateCellValue().getTime()).toString("yyyy/MM/dd");
                    }
                    return convertNumber(cell.getNumericCellValue());
                case Cell.CELL_TYPE_BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case Cell.CELL_TYPE_FORMULA:
                    if (isCellDateFormatted(cell)) {
                        return new DateTime(cell.getDateCellValue().getTime()).toString("yyyy/MM/dd");
                    }
                    return convertNumber(cell.getNumericCellValue());
                default:
                    return "";
            }
        } catch (Exception e) {
            return cell.getStringCellValue();
        }
    }

    /**
     * 创建工作表
     *
     * @param is 输入流
     * @return 工作表
     */
    private static Workbook createWorkbook(InputStream is) {
        try {
            if (!is.markSupported()) {
                is = new PushbackInputStream(is, 8);
            }
            if (POIFSFileSystem.hasPOIFSHeader(is)) {
                return new HSSFWorkbook(is);
            }
            if (POIXMLDocument.hasOOXMLHeader(is)) {
                return new XSSFWorkbook(OPCPackage.open(is));
            }
            throw new RuntimeException("当前版本不支持");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
    }
}
