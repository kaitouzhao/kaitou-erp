package kaitou.ppp.dao.warranty;

import kaitou.ppp.domain.warranty.WarrantyConsumables;

import java.util.List;

/**
 * 耗材保修DAO.
 * User: 赵立伟
 * Date: 2015/5/18
 * Time: 17:59
 */
public interface WarrantyConsumablesDao {

    /**
     * 添加/更新
     *
     * @param warrantyConsumables 耗材保修集合，可以是单个，也可是多个
     * @return 成功记录数
     */
    public int save(Object... warrantyConsumables);

    /**
     * 查询耗材保修
     *
     * @param numberOfYear 查询年份。为空则为全部
     * @return 耗材保修记录
     */
    public List<WarrantyConsumables> query(String... numberOfYear);

    /**
     * 删除
     *
     * @param warrantyConsumables 待删除集合。支持一个或多个
     * @return 成功执行记录数
     */
    public int delete(Object... warrantyConsumables);
}
