package kaitou.ppp.dao.tech;

import kaitou.ppp.domain.tech.TechSupport;

import java.util.List;

/**
 * 技术支援管理DAO.
 * User: 赵立伟
 * Date: 2015/5/19
 * Time: 18:15
 */
public interface TechSupportDao {

    /**
     * 添加/更新
     *
     * @param techSupport 技术支援集合，可以是单个，也可是多个
     * @return 成功记录数
     */
    public int save(Object... techSupport);

    /**
     * 查询技术支援
     *
     * @param numberOfYear 查询年份。为空则为全部
     * @return 技术支援
     */
    public List<TechSupport> query(String... numberOfYear);

    /**
     * 删除
     *
     * @param techSupport 待删除集合。支持一个或多个
     * @return 成功执行记录数
     */
    public int delete(Object... techSupport);
}
