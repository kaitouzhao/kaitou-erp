package kaitou.ppp.manager.tech;

import kaitou.ppp.dao.support.Condition;
import kaitou.ppp.dao.support.Pager;
import kaitou.ppp.domain.tech.TechDongle;

import java.util.List;

/**
 * dongle记录DAO事务控制层.
 * User: 赵立伟
 * Date: 2015/6/13
 * Time: 23:46
 */
public interface TechDongleManager {

    /**
     * 导入dongle记录
     *
     * @param techDongles dongle记录列表
     * @return 成功执行个数
     */
    public int save(List<TechDongle> techDongles);

    /**
     * 获取dongle记录
     *
     * @return dongle记录列表
     */
    public List<TechDongle> queryAll();

    /**
     * 删除
     *
     * @param techDongles 待删除集合。支持一个或多个
     * @return 成功执行条数
     */
    public int delete(Object... techDongles);

    /**
     * 分页查询
     *
     * @param currentPage 当前页码
     * @param conditions  查询条件列表
     * @return 封装结果集的分页对象
     */
    public Pager<TechDongle> queryPager(int currentPage, List<Condition> conditions);

    /**
     * 不分页查询
     *
     * @param conditions 查询条件列表
     * @return 结果集
     */
    public List<TechDongle> queryAll(List<Condition> conditions);
}
