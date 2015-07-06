package kaitou.ppp.dao.basic;

import kaitou.ppp.domain.basic.Models;

import java.util.List;

/**
 * 机型基础数据DAO.
 * User: 赵立伟
 * Date: 2015/6/22
 * Time: 19:10
 */
public interface ModelsDao {

    /**
     * 添加/更新
     *
     * @param models 机型基础数据集合，可以是单个，也可是多个
     * @return 成功记录数
     */
    public int save(Object... models);

    /**
     * 查询机型基础数据
     *
     * @return 机型基础数据
     */
    public List<Models> queryAll();

    /**
     * 删除
     *
     * @param models 待删除集合。支持一个或多个
     * @return 成功执行记录数
     */
    public int delete(Object... models);

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
