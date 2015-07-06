package kaitou.ppp.dao.ts;

import kaitou.ppp.domain.ts.EngineerTS;

import java.util.List;

/**
 * TS工程师DAO.
 * User: 赵立伟
 * Date: 2015/6/23
 * Time: 14:51
 */
public interface TSEngineerDao {
    /**
     * 添加/更新
     *
     * @param tsEngineers 工程师记录集合，可以是单个，也可是多个
     * @return 成功记录数
     */
    public int save(Object... tsEngineers);

    /**
     * 查询工程师记录
     *
     * @return 工程师记录
     */
    public List<EngineerTS> queryAll();

    /**
     * 删除
     *
     * @param tsEngineers 待删除集合。支持一个或多个
     * @return 成功执行记录数
     */
    public int delete(Object... tsEngineers);
}
