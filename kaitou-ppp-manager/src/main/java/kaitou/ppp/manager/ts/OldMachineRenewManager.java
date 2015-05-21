package kaitou.ppp.manager.ts;

import kaitou.ppp.domain.ts.OldMachineRenew;

import java.util.List;

/**
 * 旧机翻新管理DAO事务控制层.
 * User: 赵立伟
 * Date: 2015/5/21
 * Time: 12:48
 */
public interface OldMachineRenewManager {

    /**
     * 导入旧机翻新记录
     *
     * @param oldMachineRenewList 旧机翻新记录列表
     * @return 成功执行个数
     */
    public int save(List<OldMachineRenew> oldMachineRenewList);

    /**
     * 获取旧机翻新记录
     *
     * @param numberOfYear 查询年份，可以是单年，也可以是多年
     * @return 旧机翻新记录
     */
    public List<OldMachineRenew> query(String... numberOfYear);

    /**
     * 删除
     *
     * @param oldMachineRenew 待删除集合。支持一个或多个
     * @return 成功执行条数
     */
    public int delete(Object... oldMachineRenew);
}
