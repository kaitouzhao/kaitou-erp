package kaitou.ppp.manager.warranty;

import kaitou.ppp.domain.warranty.WarrantyParts;

import java.util.List;

/**
 * 保修零件及索赔零件DAO事务控制层.
 * User: 赵立伟
 * Date: 2015/5/10
 * Time: 16:15
 */
public interface WarrantyPartsManager {

    /**
     * 导入保修及索赔零件
     *
     * @param warrantyParts 零件列表
     * @return 成功执行个数
     */
    public int save(List<WarrantyParts> warrantyParts);

    /**
     * 获取保修及索赔零件
     *
     * @param numberOfYear 查询年份，可以是单年，也可以是多年
     * @return 零件列表
     */
    public List<WarrantyParts> query(String... numberOfYear);

    /**
     * 删除
     *
     * @param warrantyParts 待删除集合。支持一个或多个
     * @return 成功执行条数
     */
    public int delete(Object... warrantyParts);
}
