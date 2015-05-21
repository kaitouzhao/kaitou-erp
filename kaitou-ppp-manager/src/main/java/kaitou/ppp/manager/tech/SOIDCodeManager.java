package kaitou.ppp.manager.tech;

import kaitou.ppp.domain.tech.SOIDCode;

import java.util.List;

/**
 * SOID识别码DAO事务控制层.
 * User: 赵立伟
 * Date: 2015/5/19
 * Time: 11:25
 */
public interface SOIDCodeManager {

    /**
     * 导入SOID识别码
     *
     * @param soidCodes SOID识别码列表
     * @return 成功执行个数
     */
    public int save(List<SOIDCode> soidCodes);

    /**
     * 获取SOID识别码
     *
     * @return SOID识别码列表
     */
    public List<SOIDCode> query();

    /**
     * 删除
     *
     * @param soidCodes 待删除集合。支持一个或多个
     * @return 成功执行条数
     */
    public int delete(Object... soidCodes);
}
