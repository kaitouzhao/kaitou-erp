package kaitou.ppp.manager.ts;

import kaitou.ppp.domain.ts.NewMachineClaim;

import java.util.List;

/**
 * 新装机索赔管理DAO事务控制层.
 * User: 赵立伟
 * Date: 2015/5/21
 * Time: 12:50
 */
public interface NewMachineClaimManager {

    /**
     * 导入新装机索赔记录
     *
     * @param newMachineClaimList 新装机索赔记录列表
     * @return 成功执行个数
     */
    public int save(List<NewMachineClaim> newMachineClaimList);

    /**
     * 获取新装机索赔记录
     *
     * @param numberOfYear 查询年份，可以是单年，也可以是多年
     * @return 新装机索赔记录
     */
    public List<NewMachineClaim> query(String... numberOfYear);

    /**
     * 删除
     *
     * @param newMachineClaim 待删除集合。支持一个或多个
     * @return 成功执行条数
     */
    public int delete(Object... newMachineClaim);
}
