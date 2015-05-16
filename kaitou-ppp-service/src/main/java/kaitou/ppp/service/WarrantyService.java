package kaitou.ppp.service;

import kaitou.ppp.domain.warranty.WarrantyFee;
import kaitou.ppp.domain.warranty.WarrantyParts;

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
     * @param targetFile 目标文件
     */
    public void exportWarrantyFee(File targetFile);

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

}
