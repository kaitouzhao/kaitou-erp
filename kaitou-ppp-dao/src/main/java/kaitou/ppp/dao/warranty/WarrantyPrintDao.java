package kaitou.ppp.dao.warranty;

import kaitou.ppp.domain.warranty.WarrantyPrint;

import java.util.List;

/**
 * 打印头保修DAO.
 * User: 赵立伟
 * Date: 2015/5/18
 * Time: 11:43
 */
public interface WarrantyPrintDao {

    /**
     * 添加/更新
     *
     * @param warrantyPrint 打印头保修记录集合，可以是单个，也可是多个
     * @return 成功记录数
     */
    public int save(Object... warrantyPrint);

    /**
     * 查询打印头保修
     *
     * @return 打印头保修列表
     */
    public List<WarrantyPrint> query(String... numberOfYear);

    /**
     * 删除
     *
     * @param warrantyPrint 待删除集合。支持一个或多个
     * @return 成功执行记录数
     */
    public int delete(Object... warrantyPrint);
}
