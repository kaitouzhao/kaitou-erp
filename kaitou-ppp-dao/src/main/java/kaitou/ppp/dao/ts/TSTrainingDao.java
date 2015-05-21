package kaitou.ppp.dao.ts;

import kaitou.ppp.domain.ts.TSTraining;

import java.util.List;

/**
 * TS培训记录DAO.
 * User: 赵立伟
 * Date: 2015/5/20
 * Time: 10:56
 */
public interface TSTrainingDao {

    /**
     * 添加/更新
     *
     * @param tsTrainings TS培训记录集合，可以是单个，也可是多个
     * @return 成功记录数
     */
    public int save(Object... tsTrainings);

    /**
     * 查询TS培训记录
     *
     * @param numberOfYear 查询年份。为空则为全部
     * @return TS培训记录
     */
    public List<TSTraining> query(String... numberOfYear);

    /**
     * 删除
     *
     * @param tsTrainings 待删除集合。支持一个或多个
     * @return 成功执行记录数
     */
    public int delete(Object... tsTrainings);
}
