package kaitou.ppp.service;

import java.io.File;

/**
 * 导出业务层.
 * User: 赵立伟
 * Date: 2015/4/18
 * Time: 23:11
 */
public interface ExportService {

    /**
     * 导出认定级别
     *
     * @param targetFile   目标文件
     * @param numberOfYear 认定年份。默认全部，可以是一个或多个
     */
    public void exportShopDetails(File targetFile, String... numberOfYear);

    /**
     * 导出SOID识别码
     *
     * @param targetFile 目标文件
     */
    public void exportSOIDCode(File targetFile);

    /**
     * 基础信息全导出
     *
     * @param targetFile 目标文件
     */
    public void exportShopAll(File targetFile);
}
