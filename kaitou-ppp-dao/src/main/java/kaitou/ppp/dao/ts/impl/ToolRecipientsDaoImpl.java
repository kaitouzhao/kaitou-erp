package kaitou.ppp.dao.ts.impl;

import kaitou.ppp.dao.BaseDao;
import kaitou.ppp.dao.ts.ToolRecipientsDao;
import kaitou.ppp.domain.ts.ToolRecipients;

/**
 * 工具领用记录DAO实现.
 * User: 赵立伟
 * Date: 2015/5/21
 * Time: 15:00
 */
public class ToolRecipientsDaoImpl extends BaseDao<ToolRecipients> implements ToolRecipientsDao {
    @Override
    public Class<ToolRecipients> getDomainClass() {
        return ToolRecipients.class;
    }
}
