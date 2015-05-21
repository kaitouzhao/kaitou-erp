package kaitou.ppp.dao.ts;

import kaitou.ppp.domain.ts.ToolRecipients;

import java.util.List;

/**
 * 工具领用记录DAO.
 * User: 赵立伟
 * Date: 2015/5/21
 * Time: 14:57
 */
public interface ToolRecipientsDao {

    /**
     * 添加/更新
     *
     * @param toolRecipients 工具领用记录集合，可以是单个，也可是多个
     * @return 成功记录数
     */
    public int save(Object... toolRecipients);

    /**
     * 查询工具领用记录
     *
     * @param numberOfYear 查询年份。为空则为全部
     * @return 工具领用记录
     */
    public List<ToolRecipients> query(String... numberOfYear);

    /**
     * 删除
     *
     * @param toolRecipient 待删除集合。支持一个或多个
     * @return 成功执行记录数
     */
    public int delete(Object... toolRecipient);
}
