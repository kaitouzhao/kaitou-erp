package kaitou.ppp.manager.basic;

import kaitou.ppp.dao.support.Condition;
import kaitou.ppp.dao.support.Pager;
import kaitou.ppp.domain.basic.Models;

import java.util.List;

/**
 * 机型基础数据DAO事务控制层.
 * User: 赵立伟
 * Date: 2015/6/22
 * Time: 19:15
 */
public interface ModelsManager {

    /**
     * 导入机型基础数据
     *
     * @param modelsList 机型基础数据列表
     * @return 成功执行个数
     */
    public int save(List<Models> modelsList);

    /**
     * 获取机型基础数据
     *
     * @return 机型基础数据列表
     */
    public List<Models> queryAll();

    /**
     * 删除
     *
     * @param models 待删除集合。支持一个或多个
     * @return 成功执行条数
     */
    public int delete(Object... models);

    /**
     * 分页查询
     *
     * @param currentPage 当前页码
     * @param conditions  查询条件列表
     * @return 封装结果集的分页对象
     */
    public Pager<Models> queryPager(int currentPage, List<Condition> conditions);

    /**
     * 不分页查询
     *
     * @param conditions 查询条件列表
     * @return 结果集
     */
    public List<Models> queryAll(List<Condition> conditions);

    /**
     * 根据机型获取机型分类基础数据
     *
     * @param model 机型
     * @return 机型分类基础数据对象
     */
    public Models getByModel(String model);

    /**
     * 缓存机型分类基础数据
     */
    public void cacheModels();
}
