package kaitou.ppp.manager.warranty;

import kaitou.ppp.domain.warranty.WarrantyFee;

import java.util.List;

/**
 * 保修费DAO事务管理层.
 * User: 赵立伟
 * Date: 2015/5/6
 * Time: 14:54
 */
public interface WarrantyFeeManager {

    /**
     * 导入保修费
     *
     * @param warrantyFeeList 保修费记录列表
     * @return 成功执行个数
     */
    public int save(List<WarrantyFee> warrantyFeeList);

    /**
     * 获取保修费记录
     *
     * @param numberOfYear 查询年份，可以是单年，也可以是多年
     * @return 保修费记录列表
     */
    public List<WarrantyFee> query(String... numberOfYear);

    /**
     * 删除
     *
     * @param warrantyFee 待删除集合。支持一个或多个
     * @return 成功执行条数
     */
    public int delete(Object... warrantyFee);
}
