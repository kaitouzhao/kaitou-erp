package kaitou.ppp.manager.card;

import kaitou.ppp.dao.support.Condition;
import kaitou.ppp.dao.support.Pager;
import kaitou.ppp.domain.card.CardApplicationRecord;

import java.util.List;

/**
 * 保修卡生成记录DAO事务管理层.
 * User: 赵立伟
 * Date: 2015/3/7
 * Time: 21:38
 */
public interface CardApplicationRecordManager {

    /**
     * 导入保修卡生成记录
     *
     * @param cardApplicationRecords 保修卡生成记录列表
     * @return 成功执行个数
     */
    public int save(List<CardApplicationRecord> cardApplicationRecords);

    /**
     * 获取保修卡生成记录
     *
     * @return 保修卡生成记录列表
     */
    public List<CardApplicationRecord> query();

    /**
     * 删除
     *
     * @param cardApplicationRecords 待删除集合。支持一个或多个
     * @return 成功执行条数
     */
    public int delete(Object... cardApplicationRecords);

    /**
     * 分页查询
     *
     * @param currentPage 当前页码
     * @param conditions  查询条件列表
     * @return 封装结果集的分页对象
     */
    public Pager<CardApplicationRecord> queryPager(int currentPage, List<Condition> conditions);

    /**
     * 不分页查询
     *
     * @param conditions 查询条件列表
     * @return 结果集
     */
    public List<CardApplicationRecord> queryAll(List<Condition> conditions);
}
