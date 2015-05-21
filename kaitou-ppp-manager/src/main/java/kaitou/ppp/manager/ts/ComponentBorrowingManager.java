package kaitou.ppp.manager.ts;

import kaitou.ppp.domain.ts.ComponentBorrowing;

import java.util.List;

/**
 * 零件借用管理DAO事务控制层.
 * User: 赵立伟
 * Date: 2015/5/21
 * Time: 15:03
 */
public interface ComponentBorrowingManager {

    /**
     * 导入零件借用记录
     *
     * @param componentBorrowingList 零件借用记录列表
     * @return 成功执行个数
     */
    public int save(List<ComponentBorrowing> componentBorrowingList);

    /**
     * 获取零件借用记录
     *
     * @param numberOfYear 查询年份，可以是单年，也可以是多年
     * @return 零件借用记录
     */
    public List<ComponentBorrowing> query(String... numberOfYear);

    /**
     * 删除
     *
     * @param componentBorrowing 待删除集合。支持一个或多个
     * @return 成功执行条数
     */
    public int delete(Object... componentBorrowing);
}
