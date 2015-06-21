package kaitou.ppp.service.impl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import kaitou.ppp.common.log.BaseLogManager;
import kaitou.ppp.domain.export.ExportSOIDCodeModel;
import kaitou.ppp.domain.export.ExportShopDetailModel1;
import kaitou.ppp.domain.shop.ShopDetail;
import kaitou.ppp.domain.tech.SOIDCode;
import kaitou.ppp.manager.shop.ShopDetailManager;
import kaitou.ppp.manager.tech.SOIDCodeManager;
import kaitou.ppp.service.ExportService;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.womai.bsp.tool.utils.BeanCopyUtil.copyBean;

/**
 * 导出业务层实现.
 * User: 赵立伟
 * Date: 2015/4/18
 * Time: 23:14
 */
public class ExportServiceImpl extends BaseLogManager implements ExportService {

    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final String TEMPLATES = "/templates";
    private static final String SOID_CODE_EXPORT1_FTL = "export_soid_code.ftl";
    private static final String SHOP_DETAIL_EXPORT1_FTL = "export_shop_detail.ftl";

    private SOIDCodeManager soidCodeManager;
    private ShopDetailManager shopDetailManager;

    public void setSoidCodeManager(SOIDCodeManager soidCodeManager) {
        this.soidCodeManager = soidCodeManager;
    }

    public void setShopDetailManager(ShopDetailManager shopDetailManager) {
        this.shopDetailManager = shopDetailManager;
    }

    @Override
    public void exportShopDetails(File targetFile, String... numberOfYear) {
        List<ShopDetail> shopDetails = shopDetailManager.query(numberOfYear);
        List<ExportShopDetailModel1> dataList = new ArrayList<ExportShopDetailModel1>();
        ExportShopDetailModel1 data;
        for (ShopDetail detail : shopDetails) {
            data = new ExportShopDetailModel1();
            data.setShopId(detail.getId());
            int index = dataList.indexOf(data);
            if (index >= 0) {
                data = dataList.get(index);
            } else {
                data.setShopName(detail.getName());
                data.setSaleRegion(detail.getSaleRegion());
                dataList.add(data);
            }
            if ("2013".equals(detail.getNumberOfYear())) {
                if ("CPP".equals(detail.getProductLine())) {
                    data.setCpp2013(detail.getLevel());
                    data.setCppModel(detail.getModel());
                } else if ("WFP".equals(detail.getProductLine())) {
                    data.setWfp2013(detail.getLevel());
                    data.setWfpModel(detail.getModel());
                } else {
                    data.setIpf2013(detail.getLevel());
                    data.setIpfModel(detail.getModel());
                }
            } else if ("2014".equals(detail.getNumberOfYear())) {
                if ("CPP".equals(detail.getProductLine())) {
                    data.setCpp2014(detail.getLevel());
                    data.setCppModel(detail.getModel());
                } else if ("WFP".equals(detail.getProductLine())) {
                    data.setWfp2014(detail.getLevel());
                    data.setWfpModel(detail.getModel());
                } else {
                    data.setIpf2014(detail.getLevel());
                    data.setIpfModel(detail.getModel());
                }
            } else {
                if ("CPP".equals(detail.getProductLine())) {
                    data.setCpp2015(detail.getLevel());
                    data.setCppModel(detail.getModel());
                } else if ("WFP".equals(detail.getProductLine())) {
                    data.setWfp2015(detail.getLevel());
                    data.setWfpModel(detail.getModel());
                } else if ("IPF".equals(detail.getProductLine())) {
                    data.setIpf2015(detail.getLevel());
                    data.setIpfModel(detail.getModel());
                } else if ("DP".equals(detail.getProductLine())) {
                    data.setDp2015(detail.getLevel());
                    data.setDpModel(detail.getModel());
                } else if ("PGA".equals(detail.getProductLine())) {
                    data.setPga2015(detail.getLevel());
                    data.setPgaModel(detail.getModel());
                } else if ("TDS".equals(detail.getProductLine())) {
                    data.setTds2015(detail.getLevel());
                    data.setTdsModel(detail.getModel());
                } else if ("DGS".equals(detail.getProductLine())) {
                    data.setDgs2015(detail.getLevel());
                    data.setDgsModel(detail.getModel());
                }
            }
        }
        output(SHOP_DETAIL_EXPORT1_FTL, dataList, targetFile);
    }

    @Override
    public void exportSOIDCode(File targetFile) {
        List<SOIDCode> soidCodeList = soidCodeManager.query();
        List<ExportSOIDCodeModel> dataList = new ArrayList<ExportSOIDCodeModel>();
        ExportSOIDCodeModel data;
        for (SOIDCode code : soidCodeList) {
            data = new ExportSOIDCodeModel();
            copyBean(code, data);
            if ("DP".toLowerCase().equals(code.getPermissionProductLine().toLowerCase())) {
                data.setDp("是");
            }
            if ("PGA".toLowerCase().equals(code.getPermissionProductLine().toLowerCase())) {
                data.setPga("是");
            }
            if ("TDS".toLowerCase().equals(code.getPermissionProductLine().toLowerCase())) {
                data.setTds("是");
            }
            if ("DGS".toLowerCase().equals(code.getPermissionProductLine().toLowerCase())) {
                data.setDgs("是");
            }
            if ("iPF".toLowerCase().equals(code.getPermissionProductLine().toLowerCase())) {
                data.setIpf("是");
            }
            if ("PP".toLowerCase().equals(code.getPermissionProductLine().toLowerCase())) {
                data.setPp("是");
            }
            dataList.add(data);
        }
        output(SOID_CODE_EXPORT1_FTL, dataList, targetFile);
    }

    /**
     * 输出
     *
     * @param ftl        模板名
     * @param dataList   数据集合
     * @param targetFile 目标文件
     */
    private void output(String ftl, List dataList, File targetFile) {
        try {
            Configuration configuration = new Configuration();
            configuration.setDefaultEncoding(DEFAULT_ENCODING);
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("dataList", dataList);
            configuration.setClassForTemplateLoading(getClass(), TEMPLATES);
            Template template = configuration.getTemplate(ftl);
            if (targetFile.exists()) {
                targetFile.deleteOnExit();
            }
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile), DEFAULT_ENCODING));
            template.process(dataMap, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
