package kaitou.ppp.manager.ts.impl;

import kaitou.ppp.domain.ts.ToolRecipients;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.ts.ToolRecipientsManager;

/**
 * 工具领用记录DAO事务控制层实现.
 * User: 赵立伟
 * Date: 2015/5/21
 * Time: 15:06
 */
public class ToolRecipientsManagerImpl extends BaseFileDaoManager<ToolRecipients> implements ToolRecipientsManager {
    @Override
    public Class<ToolRecipients> domainClass() {
        return ToolRecipients.class;
    }
}
