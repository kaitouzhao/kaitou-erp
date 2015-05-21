package kaitou.ppp.manager.warranty;

import kaitou.ppp.domain.warranty.WarrantyPrint;

import java.util.List;

/**
 * 打印头保修DAO事务控制层.
 * User: 赵立伟
 * Date: 2015/5/18
 * Time: 11:47
 */
public interface WarrantyPrintManager {

    /**
     * 导入打印头保修记录
     *
     * @param warrantyPrint 打印头保修列表
     * @return 成功执行个数
     */
    public int save(List<WarrantyPrint> warrantyPrint);

    /**
     * 获取打印头保修记录
     *
     * @param numberOfYear 查询年份，可以是单年，也可以是多年
     * @return 打印头保修列表
     */
    public List<WarrantyPrint> query(String... numberOfYear);

    /**
     * 删除
     *
     * @param warrantyPrint 待删除集合。支持一个或多个
     * @return 成功执行条数
     */
    public int delete(Object... warrantyPrint);
}
