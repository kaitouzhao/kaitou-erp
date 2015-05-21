package kaitou.ppp.manager.warranty;

import kaitou.ppp.domain.warranty.WarrantyConsumables;

import java.util.List;

/**
 * 耗材保修DAO事务控制层.
 * User: 赵立伟
 * Date: 2015/5/18
 * Time: 18:02
 */
public interface WarrantyConsumablesManager {

    /**
     * 导入耗材保修
     *
     * @param warrantyConsumablesList 耗材保修记录列表
     * @return 成功执行个数
     */
    public int save(List<WarrantyConsumables> warrantyConsumablesList);

    /**
     * 获取耗材保修记录
     *
     * @param numberOfYear 查询年份，可以是单年，也可以是多年
     * @return 耗材保修记录列表
     */
    public List<WarrantyConsumables> query(String... numberOfYear);

    /**
     * 删除
     *
     * @param warrantyConsumables 待删除集合。支持一个或多个
     * @return 成功执行条数
     */
    public int delete(Object... warrantyConsumables);
}
