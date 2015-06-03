package kaitou.ppp.dao.tech;

import kaitou.ppp.domain.tech.SOIDCode;

import java.util.List;

/**
 * SOID识别码DAO.
 * User: 赵立伟
 * Date: 2015/5/19
 * Time: 11:22
 */
public interface SOIDCodeDao {

    /**
     * 添加/更新
     *
     * @param soidCodes SOID识别码集合，可以是单个，也可是多个
     * @return 成功记录数
     */
    public int save(Object... soidCodes);

    /**
     * 查询SOID识别码
     *
     * @return SOID识别码
     */
    public List<SOIDCode> queryAll();

    /**
     * 删除
     *
     * @param soidCodes 待删除集合。支持一个或多个
     * @return 成功执行记录数
     */
    public int delete(Object... soidCodes);
}
