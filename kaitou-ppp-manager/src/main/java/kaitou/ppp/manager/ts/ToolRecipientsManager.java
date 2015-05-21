package kaitou.ppp.manager.ts;

import kaitou.ppp.domain.ts.ToolRecipients;

import java.util.List;

/**
 * 工具领用记录DAO事务控制层.
 * User: 赵立伟
 * Date: 2015/5/21
 * Time: 15:02
 */
public interface ToolRecipientsManager {

    /**
     * 导入工具领用记录
     *
     * @param toolRecipientsList 工具领用记录列表
     * @return 成功执行个数
     */
    public int save(List<ToolRecipients> toolRecipientsList);

    /**
     * 获取工具领用记录
     *
     * @param numberOfYear 查询年份，可以是单年，也可以是多年
     * @return 工具领用记录
     */
    public List<ToolRecipients> query(String... numberOfYear);

    /**
     * 删除
     *
     * @param toolRecipients 待删除集合。支持一个或多个
     * @return 成功执行条数
     */
    public int delete(Object... toolRecipients);
}
