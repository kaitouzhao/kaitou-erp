package kaitou.ppp.dao.ts;

import kaitou.ppp.domain.ts.NewMachineClaim;

import java.util.List;

/**
 * 新装机索赔管理DAO.
 * User: 赵立伟
 * Date: 2015/5/21
 * Time: 12:42
 */
public interface NewMachineClaimDao {

    /**
     * 添加/更新
     *
     * @param newMachineClaim 新装机索赔记录集合，可以是单个，也可是多个
     * @return 成功记录数
     */
    public int save(Object... newMachineClaim);

    /**
     * 查询新装机索赔记录
     *
     * @param numberOfYear 查询年份。为空则为全部
     * @return 新装机索赔记录
     */
    public List<NewMachineClaim> query(String... numberOfYear);

    /**
     * 删除
     *
     * @param newMachineClaim 待删除集合。支持一个或多个
     * @return 成功执行记录数
     */
    public int delete(Object... newMachineClaim);
}
