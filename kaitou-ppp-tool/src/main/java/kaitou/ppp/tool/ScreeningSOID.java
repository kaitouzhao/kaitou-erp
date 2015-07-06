package kaitou.ppp.tool;

import com.womai.bsp.tool.utils.ExcelUtil;
import kaitou.ppp.domain.tech.SOIDCode;
import kaitou.ppp.service.BaseExcelService;
import kaitou.ppp.service.TechService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 筛选SOID.
 * User: 赵立伟
 * Date: 2015/6/25
 * Time: 20:06
 */
public class ScreeningSOID {

    /**
     * 主程序入口
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        File srcFile = new File("C:\\Users\\zhao\\Desktop\\PPP\\需求\\SOID-PPP服务认定店_20150330 new.xls");
        List<String[]> dataList = ExcelUtil.readExcel(srcFile, "汇总", 10, 3);
        TechService techService = new ClassPathXmlApplicationContext(
                new String[]{
                        "applicationContext-service.xml"
                }
        ).getBean(TechService.class);
        List<SOIDCode> soidCodeList = new ArrayList<SOIDCode>();
        SOIDCode soidCode;
        for (String[] data : dataList) {
            if ("√".equals(data[2])) {
                soidCode = new SOIDCode();
                soidCode.setSaleRegion(data[0]);
                soidCode.setShopName(data[1]);
                soidCode.setNewSoid(data[8]);
                soidCode.setNewVerificationCode(data[9]);
                soidCode.setPermissionProductLine("DP");
                soidCodeList.add(soidCode);
            }
            if ("√".equals(data[3])) {
                soidCode = new SOIDCode();
                soidCode.setSaleRegion(data[0]);
                soidCode.setShopName(data[1]);
                soidCode.setNewSoid(data[8]);
                soidCode.setNewVerificationCode(data[9]);
                soidCode.setPermissionProductLine("PGA");
                soidCodeList.add(soidCode);
            }
            if ("√".equals(data[4])) {
                soidCode = new SOIDCode();
                soidCode.setSaleRegion(data[0]);
                soidCode.setShopName(data[1]);
                soidCode.setNewSoid(data[8]);
                soidCode.setNewVerificationCode(data[9]);
                soidCode.setPermissionProductLine("TDS");
                soidCodeList.add(soidCode);
            }
            if ("√".equals(data[5])) {
                soidCode = new SOIDCode();
                soidCode.setSaleRegion(data[0]);
                soidCode.setShopName(data[1]);
                soidCode.setNewSoid(data[8]);
                soidCode.setNewVerificationCode(data[9]);
                soidCode.setPermissionProductLine("DGS");
                soidCodeList.add(soidCode);
            }
            if ("√".equals(data[6])) {
                soidCode = new SOIDCode();
                soidCode.setSaleRegion(data[0]);
                soidCode.setShopName(data[1]);
                soidCode.setNewSoid(data[8]);
                soidCode.setNewVerificationCode(data[9]);
                soidCode.setPermissionProductLine("iPF");
                soidCodeList.add(soidCode);
            }
            if ("√".equals(data[7])) {
                soidCode = new SOIDCode();
                soidCode.setSaleRegion(data[0]);
                soidCode.setShopName(data[1]);
                soidCode.setNewSoid(data[8]);
                soidCode.setNewVerificationCode(data[9]);
                soidCode.setPermissionProductLine("PP");
                soidCodeList.add(soidCode);
            }
        }
        File targetFile = new File("C:\\Users\\zhao\\Desktop\\PPP\\需求\\SOID.xlsx");
        ((BaseExcelService) techService).export2Excel(soidCodeList, targetFile, SOIDCode.class);
        System.out.println("success");
    }
}
