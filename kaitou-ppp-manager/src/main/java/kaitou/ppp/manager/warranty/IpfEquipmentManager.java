package kaitou.ppp.manager.warranty;

import kaitou.ppp.dao.support.Condition;
import kaitou.ppp.dao.support.Pager;
import kaitou.ppp.domain.warranty.IpfEquipment;

import java.util.List;

/**
 * iPF设备DAO事务控制层.
 * User: 赵立伟
 * Date: 2015/6/28
 * Time: 11:47
 */
public interface IpfEquipmentManager {

    /**
     * 导入iPF设备记录
     *
     * @param equipments iPF设备记录列表
     * @return 成功执行个数
     */
    public int save(List<IpfEquipment> equipments);

    /**
     * 获取iPF设备记录
     *
     * @return iPF设备记录列表
     */
    public List<IpfEquipment> queryAll();

    /**
     * 删除
     *
     * @param equipments 待删除集合。支持一个或多个
     * @return 成功执行条数
     */
    public int delete(Object... equipments);

    /**
     * 分页查询
     *
     * @param currentPage 当前页码
     * @param conditions  查询条件列表
     * @return 封装结果集的分页对象
     */
    public Pager<IpfEquipment> queryPager(int currentPage, List<Condition> conditions);

    /**
     * 不分页查询
     *
     * @param conditions 查询条件列表
     * @return 结果集
     */
    public List<IpfEquipment> queryAll(List<Condition> conditions);
}
