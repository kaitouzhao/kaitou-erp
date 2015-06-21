package kaitou.ppp.dao.tech;

import kaitou.ppp.dao.support.Condition;
import kaitou.ppp.dao.support.Pager;
import kaitou.ppp.domain.tech.TechDongle;

import java.util.List;

/**
 * dongle记录DAO.
 * User: 赵立伟
 * Date: 2015/6/13
 * Time: 23:35
 */
public interface TechDongleDao {

    /**
     * 添加/更新
     *
     * @param techDongles dongle记录集合，可以是单个，也可是多个
     * @return 成功记录数
     */
    public int save(Object... techDongles);

    /**
     * 查询dongle记录
     *
     * @return dongle记录
     */
    public List<TechDongle> queryAll();

    /**
     * 删除
     *
     * @param techDongles 待删除集合。支持一个或多个
     * @return 成功执行记录数
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
}
