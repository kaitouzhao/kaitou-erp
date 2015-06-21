package kaitou.ppp.manager.ts;

import kaitou.ppp.dao.support.Condition;
import kaitou.ppp.dao.support.Pager;
import kaitou.ppp.domain.ts.TSDongle;

import java.util.List;

/**
 * dongle记录DAO事务控制层.
 * User: 赵立伟
 * Date: 2015/6/13
 * Time: 23:50
 */
public interface TsDongleManager {

    /**
     * 导入dongle记录
     *
     * @param tsDongles dongle记录列表
     * @return 成功执行个数
     */
    public int save(List<TSDongle> tsDongles);

    /**
     * 获取dongle记录
     *
     * @return dongle记录列表
     */
    public List<TSDongle> queryAll();

    /**
     * 删除
     *
     * @param tsDongles 待删除集合。支持一个或多个
     * @return 成功执行条数
     */
    public int delete(Object... tsDongles);

    /**
     * 分页查询
     *
     * @param currentPage 当前页码
     * @param conditions  查询条件列表
     * @return 封装结果集的分页对象
     */
    public Pager<TSDongle> queryPager(int currentPage, List<Condition> conditions);

    /**
     * 不分页查询
     *
     * @param conditions 查询条件列表
     * @return 结果集
     */
    public List<TSDongle> queryAll(List<Condition> conditions);
}
