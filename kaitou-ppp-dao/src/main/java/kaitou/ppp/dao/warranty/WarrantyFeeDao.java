package kaitou.ppp.dao.warranty;

import kaitou.ppp.domain.warranty.WarrantyFee;

import java.util.List;

/**
 * 保修费DAO.
 * User: 赵立伟
 * Date: 2015/5/6
 * Time: 14:22
 */
public interface WarrantyFeeDao {

    /**
     * 添加/更新
     *
     * @param warrantyFee 保修费集合，可以是单个，也可是多个
     * @return 成功记录数
     */
    public int save(Object... warrantyFee);

    /**
     * 查询保修费
     *
     * @return 保修费
     */
    public List<WarrantyFee> query();

    /**
     * 删除
     *
     * @param warrantyFee 待删除集合。支持一个或多个
     * @return 成功执行记录数
     */
    public int delete(Object... warrantyFee);
}
