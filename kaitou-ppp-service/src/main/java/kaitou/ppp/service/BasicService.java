package kaitou.ppp.service;

import kaitou.ppp.dao.support.Condition;
import kaitou.ppp.dao.support.Pager;
import kaitou.ppp.domain.basic.Models;

import java.io.File;
import java.util.List;

/**
 * 基础数据服务.
 * User: 赵立伟
 * Date: 2015/6/22
 * Time: 19:24
 */
public interface BasicService {
    /**
     * 导入机型基础数据
     *
     * @param srcFile 源文件
     */
    public void importBasicModels(File srcFile);

    /**
     * 导出机型基础数据
     *
     * @param targetFile 目标文件
     */
    public void exportBasicModels(File targetFile);

    /**
     * 查询机型基础数据
     *
     * @return 机型基础数据列表
     */
    public List<Models> queryBasicModels();

    /**
     * 保存/更新机型基础数据
     *
     * @param models 机型基础数据
     */
    public void saveOrUpdateBasicModels(Models... models);

    /**
     * 删除机型基础数据
     *
     * @param models 机型基础数据
     */
    public void deleteBasicModels(Object... models);

    /**
     * 分页查询
     *
     * @param currentPage 当前页码
     * @param conditions  查询条件列表
     * @return 封装结果集的分页对象
     */
    public Pager<Models> queryBasicModels(int currentPage, List<Condition> conditions);

    /**
     * 不分页查询
     *
     * @param conditions 查询条件列表
     * @return 结果集
     */
    public List<Models> queryBasicModels(List<Condition> conditions);

    /**
     * 缓存机型分类基础数据
     */
    public void cacheModels();
}
