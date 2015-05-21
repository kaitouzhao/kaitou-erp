package kaitou.ppp.dao.ts;

import kaitou.ppp.domain.ts.ComponentBorrowing;

import java.util.List;

/**
 * 零件借用管理DAO.
 * User: 赵立伟
 * Date: 2015/5/21
 * Time: 14:56
 */
public interface ComponentBorrowingDao {

    /**
     * 添加/更新
     *
     * @param componentBorrowing 零件借用记录集合，可以是单个，也可是多个
     * @return 成功记录数
     */
    public int save(Object... componentBorrowing);

    /**
     * 查询零件借用记录
     *
     * @param numberOfYear 查询年份。为空则为全部
     * @return 零件借用记录
     */
    public List<ComponentBorrowing> query(String... numberOfYear);

    /**
     * 删除
     *
     * @param componentBorrowing 待删除集合。支持一个或多个
     * @return 成功执行记录数
     */
    public int delete(Object... componentBorrowing);
}
