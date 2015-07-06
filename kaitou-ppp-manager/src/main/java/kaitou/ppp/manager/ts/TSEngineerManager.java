package kaitou.ppp.manager.ts;

import kaitou.ppp.dao.support.Condition;
import kaitou.ppp.dao.support.Pager;
import kaitou.ppp.domain.ts.EngineerTS;

import java.util.List;

/**
 * TS工程师DAO事务控制层.
 * User: 赵立伟
 * Date: 2015/6/23
 * Time: 14:54
 */
public interface TSEngineerManager {
    /**
     * 导入工程师记录
     *
     * @param engineerTSes 工程师记录列表
     * @return 成功执行个数
     */
    public int save(List<EngineerTS> engineerTSes);

    /**
     * 获取工程师记录
     *
     * @return 工程师记录列表
     */
    public List<EngineerTS> queryAll();

    /**
     * 删除
     *
     * @param tsEngineers 待删除集合。支持一个或多个
     * @return 成功执行条数
     */
    public int delete(Object... tsEngineers);

    /**
     * 分页查询
     *
     * @param currentPage 当前页码
     * @param conditions  查询条件列表
     * @return 封装结果集的分页对象
     */
    public Pager<EngineerTS> queryPager(int currentPage, List<Condition> conditions);

    /**
     * 不分页查询
     *
     * @param conditions 查询条件列表
     * @return 结果集
     */
    public List<EngineerTS> queryAll(List<Condition> conditions);
}
