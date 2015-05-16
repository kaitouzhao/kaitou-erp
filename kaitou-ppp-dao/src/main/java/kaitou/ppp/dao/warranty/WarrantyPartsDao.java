package kaitou.ppp.dao.warranty;

import kaitou.ppp.domain.warranty.WarrantyParts;

import java.util.List;

/**
 * 保修零件及索赔零件DAO.
 * User: 赵立伟
 * Date: 2015/5/10
 * Time: 16:02
 */
public interface WarrantyPartsDao {

    /**
     * 添加/更新
     *
     * @param warrantyParts 零件集合，可以是单个，也可是多个
     * @return 成功记录数
     */
    public int save(Object... warrantyParts);

    /**
     * 查询保修零件及索赔零件
     *
     * @return 零件列表
     */
    public List<WarrantyParts> query(String... numberOfYear);

    /**
     * 删除
     *
     * @param warrantyParts 待删除集合。支持一个或多个
     * @return 成功执行记录数
     */
    public int delete(Object... warrantyParts);
}
