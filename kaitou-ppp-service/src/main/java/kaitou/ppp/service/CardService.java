package kaitou.ppp.service;

import kaitou.ppp.dao.support.Condition;
import kaitou.ppp.dao.support.Pager;
import kaitou.ppp.domain.card.CardApplicationRecord;

import java.io.File;
import java.util.List;

/**
 * 保修卡业务处理层.
 * User: 赵立伟
 * Date: 2015/1/25
 * Time: 22:45
 */
public interface CardService {
    /**
     * 生成保修卡
     */
    public void generateCards();

    /**
     * 导入保修卡生成记录
     *
     * @param srcFile 源文件
     */
    public void importCardApplicationRecords(File srcFile);

    /**
     * 导出保修卡生成记录
     *
     * @param targetFile 目标文件
     */
    public void exportCardApplicationRecords(File targetFile);

    /**
     * 查询保修卡生成记录
     *
     * @return 保修卡生成记录列表
     */
    public List<CardApplicationRecord> queryCardApplicationRecords();

    /**
     * 保存/更新保修卡生成记录
     *
     * @param cardApplicationRecords 保修卡生成记录
     */
    public void saveOrUpdateCardApplicationRecord(CardApplicationRecord... cardApplicationRecords);

    /**
     * 删除保修卡生成记录
     *
     * @param cardApplicationRecords 保修卡生成记录
     */
    public void deleteCardApplicationRecords(Object... cardApplicationRecords);

    /**
     * 分页查询
     *
     * @param currentPage 当前页码
     * @param conditions  查询条件列表
     * @return 封装结果集的分页对象
     */
    public Pager<CardApplicationRecord> queryCardApplicationRecordPager(int currentPage, List<Condition> conditions);

    /**
     * 不分页查询
     *
     * @param conditions 查询条件列表
     * @return 结果集
     */
    public List<CardApplicationRecord> queryCardApplicationRecord(List<Condition> conditions);

}
