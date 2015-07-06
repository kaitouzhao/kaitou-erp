package kaitou.ppp.manager.ts.impl;

import kaitou.ppp.domain.ts.EngineerTS;
import kaitou.ppp.domain.ts.ToolRecipients;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.listener.EngineerTSUpdateListener;
import kaitou.ppp.manager.ts.ToolRecipientsManager;

import java.util.List;

/**
 * 工具领用记录DAO事务控制层实现.
 * User: 赵立伟
 * Date: 2015/5/21
 * Time: 15:06
 */
public class ToolRecipientsManagerImpl extends BaseFileDaoManager<ToolRecipients> implements ToolRecipientsManager, EngineerTSUpdateListener {
    @Override
    public Class<ToolRecipients> domainClass() {
        return ToolRecipients.class;
    }

    @Override
    public void updateEngineerEvent(EngineerTS... engineers) {
        List<ToolRecipients> toolRecipientsList = queryAll();
        for (ToolRecipients tooRecipient : toolRecipientsList) {
            for (EngineerTS engineerTS : engineers) {
                if (engineerTS.getEmployeeNo().equals(tooRecipient.getEmployeeNo())) {
                    tooRecipient.setUseEngineerName(engineerTS.getEngineerName());
                    tooRecipient.setSaleRegion(engineerTS.getSaleRegion());
                }
            }
        }
        save(toolRecipientsList);
    }
}
