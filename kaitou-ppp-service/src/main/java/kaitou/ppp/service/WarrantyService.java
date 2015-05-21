package kaitou.ppp.service;

import kaitou.ppp.domain.warranty.WarrantyConsumables;
import kaitou.ppp.domain.warranty.WarrantyFee;
import kaitou.ppp.domain.warranty.WarrantyParts;
import kaitou.ppp.domain.warranty.WarrantyPrint;

import java.io.File;
import java.util.List;

/**
 * 保修管理服务层.
 * User: 赵立伟
 * Date: 2015/5/7
 * Time: 10:11
 */
public interface WarrantyService {

    /**
     * 导入保修费记录
     *
     * @param srcFile 源文件
     */
    public void importWarrantyFee(File srcFile);

    /**
     * 导出保修费记录
     *
     * @param targetFile   目标文件
     * @param numberOfYear 查询年份，可以是单年，也可以是多年
     */
    public void exportWarrantyFee(File targetFile, String... numberOfYear);

    /**
     * 查询保修费记录
     *
     * @return 保修费列表
     */
    public List<WarrantyFee> queryWarrantyFee();

    /**
     * 保存/更新保修费记录
     *
     * @param warrantyFee 保修费记录
     */
    public void saveOrUpdateWarrantyFee(WarrantyFee... warrantyFee);

    /**
     * 删除保修费记录
     *
     * @param warrantyFee 保修费记录
     */
    public void deleteWarrantyFee(Object... warrantyFee);

    /**
     * 导入保修及索赔零件
     *
     * @param srcFile 源文件
     */
    public void importWarrantyParts(File srcFile);

    /**
     * 导出保修及索赔零件
     *
     * @param targetFile   目标文件
     * @param numberOfYear 查询年份，可以是单年，也可以是多年
     */
    public void exportWarrantyParts(File targetFile, String... numberOfYear);

    /**
     * 查询保修及索赔零件
     *
     * @return 零件列表
     */
    public List<WarrantyParts> queryWarrantyParts();

    /**
     * 保存/更新保修及索赔零件
     *
     * @param warrantyParts 零件
     */
    public void saveOrUpdateWarrantyParts(WarrantyParts... warrantyParts);

    /**
     * 删除保修及索赔零件
     *
     * @param warrantyParts 零件
     */
    public void deleteWarrantyParts(Object... warrantyParts);

    /**
     * 导入打印头保修
     *
     * @param srcFile 源文件
     */
    public void importWarrantyPrint(File srcFile);

    /**
     * 导出打印头保修
     *
     * @param targetFile   目标文件
     * @param numberOfYear 查询年份，可以是单年，也可以是多年
     */
    public void exportWarrantyPrint(File targetFile, String... numberOfYear);

    /**
     * 查询打印头保修
     *
     * @return 打印头保修列表
     */
    public List<WarrantyPrint> queryWarrantyPrint();

    /**
     * 保存/更新打印头保修
     *
     * @param warrantyPrint 打印头保修
     */
    public void saveOrUpdateWarrantyPrint(WarrantyPrint... warrantyPrint);

    /**
     * 删除打印头保修
     *
     * @param warrantyPrint 打印头保修
     */
    public void deleteWarrantyPrint(Object... warrantyPrint);

    /**
     * 导入耗材保修
     *
     * @param srcFile 源文件
     */
    public void importWarrantyConsumables(File srcFile);

    /**
     * 导出耗材保修
     *
     * @param targetFile   目标文件
     * @param numberOfYear 查询年份，可以是单年，也可以是多年
     */
    public void exportWarrantyConsumables(File targetFile, String... numberOfYear);

    /**
     * 查询耗材保修
     *
     * @return 耗材保修列表
     */
    public List<WarrantyConsumables> queryWarrantyConsumables();

    /**
     * 保存/更新耗材保修
     *
     * @param warrantyConsumables 耗材保修
     */
    public void saveOrUpdateWarrantyConsumables(WarrantyConsumables... warrantyConsumables);

    /**
     * 删除耗材保修
     *
     * @param warrantyConsumables 耗材保修
     */
    public void deleteWarrantyConsumables(Object... warrantyConsumables);

}
