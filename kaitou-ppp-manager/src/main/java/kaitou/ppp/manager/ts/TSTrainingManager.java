package kaitou.ppp.manager.ts;

import kaitou.ppp.domain.ts.TSTraining;

import java.util.List;

/**
 * TS培训记录DAO事务控制层.
 * User: 赵立伟
 * Date: 2015/5/20
 * Time: 11:12
 */
public interface TSTrainingManager {

    /**
     * 导入TS培训记录
     *
     * @param tsTrainingList TS培训记录列表
     * @return 成功执行个数
     */
    public int save(List<TSTraining> tsTrainingList);

    /**
     * 获取TS培训记录
     *
     * @param numberOfYear 查询年份，可以是单年，也可以是多年
     * @return TS培训记录
     */
    public List<TSTraining> query(String... numberOfYear);

    /**
     * 删除
     *
     * @param tsTraining 待删除集合。支持一个或多个
     * @return 成功执行条数
     */
    public int delete(Object... tsTraining);
}
