package kaitou.ppp.manager.tech;

import kaitou.ppp.domain.tech.TechSupport;

import java.util.List;

/**
 * 技术支援管理DAO事务控制层.
 * User: 赵立伟
 * Date: 2015/5/19
 * Time: 18:23
 */
public interface TechSupportManager {

    /**
     * 导入技术支援
     *
     * @param techSupportList 技术支援记录列表
     * @return 成功执行个数
     */
    public int save(List<TechSupport> techSupportList);

    /**
     * 获取技术支援
     *
     * @param numberOfYear 查询年份，可以是单年，也可以是多年
     * @return 技术支援列表
     */
    public List<TechSupport> query(String... numberOfYear);

    /**
     * 删除
     *
     * @param techSupport 待删除集合。支持一个或多个
     * @return 成功执行条数
     */
    public int delete(Object... techSupport);
}
