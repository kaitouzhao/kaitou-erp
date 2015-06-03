package kaitou.ppp.dao.shop;

import kaitou.ppp.domain.shop.PartsLibrary;

import java.util.List;

/**
 * 零件备库管理DAO.
 * User: 赵立伟
 * Date: 2015/5/17
 * Time: 22:22
 */
public interface PartsLibraryDao {

    /**
     * 添加/更新
     *
     * @param partsLibrary 零件备库信息集合，可以是单个，也可是多个
     * @return 成功记录数
     */
    public int save(Object... partsLibrary);

    /**
     * 查询零件备库信息
     *
     * @return 零件备库信息
     */
    public List<PartsLibrary> queryAll();

    /**
     * 删除
     *
     * @param partsLibrary 待删除集合。支持一个或多个
     * @return 成功执行记录数
     */
    public int delete(Object... partsLibrary);
}
