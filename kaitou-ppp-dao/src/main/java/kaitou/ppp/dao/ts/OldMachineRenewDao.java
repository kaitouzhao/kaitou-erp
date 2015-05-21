package kaitou.ppp.dao.ts;

import kaitou.ppp.domain.ts.OldMachineRenew;

import java.util.List;

/**
 * 旧机翻新管理DAO.
 * User: 赵立伟
 * Date: 2015/5/21
 * Time: 12:42
 */
public interface OldMachineRenewDao {

    /**
     * 添加/更新
     *
     * @param oldMachineRenew 旧机翻新记录集合，可以是单个，也可是多个
     * @return 成功记录数
     */
    public int save(Object... oldMachineRenew);

    /**
     * 查询旧机翻新记录
     *
     * @param numberOfYear 查询年份。为空则为全部
     * @return 旧机翻新记录
     */
    public List<OldMachineRenew> query(String... numberOfYear);

    /**
     * 删除
     *
     * @param oldMachineRenew 待删除集合。支持一个或多个
     * @return 成功执行记录数
     */
    public int delete(Object... oldMachineRenew);
}
