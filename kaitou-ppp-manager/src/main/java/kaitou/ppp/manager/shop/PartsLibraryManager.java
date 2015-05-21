package kaitou.ppp.manager.shop;

import kaitou.ppp.domain.shop.PartsLibrary;

import java.util.List;

/**
 * 零件备库管理DAO事务控制层.
 * User: 赵立伟
 * Date: 2015/5/17
 * Time: 22:27
 */
public interface PartsLibraryManager {

    /**
     * 保存零件备库信息
     *
     * @param partsLibraries 零件备库信息列表
     * @return 成功执行个数
     */
    public int save(List<PartsLibrary> partsLibraries);

    /**
     * 获取零件备库信息
     *
     * @return 零件备库信息列表
     */
    public List<PartsLibrary> query();

    /**
     * 删除
     *
     * @param partsLibraries 待删除集合。支持一个或多个
     * @return 成功执行条数
     */
    public int delete(Object... partsLibraries);
}
