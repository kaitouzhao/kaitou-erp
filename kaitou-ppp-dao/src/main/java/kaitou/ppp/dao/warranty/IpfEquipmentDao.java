package kaitou.ppp.dao.warranty;

import kaitou.ppp.dao.support.Condition;
import kaitou.ppp.dao.support.Pager;
import kaitou.ppp.domain.warranty.IpfEquipment;

import java.util.List;

/**
 * iPF设备DAO.
 * User: 赵立伟
 * Date: 2015/6/28
 * Time: 11:43
 */
public interface IpfEquipmentDao {

    /**
     * 添加/更新
     *
     * @param equipments iPF设备记录集合，可以是单个，也可是多个
     * @return 成功记录数
     */
    public int save(Object... equipments);

    /**
     * 查询iPF设备记录
     *
     * @return iPF设备记录
     */
    public List<IpfEquipment> queryAll();

    /**
     * 删除
     *
     * @param equipments 待删除集合。支持一个或多个
     * @return 成功执行记录数
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
}
