package kaitou.ppp.manager.ts.impl;

import kaitou.ppp.domain.ts.EngineerTS;
import kaitou.ppp.domain.ts.TSDongle;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.listener.EngineerTSUpdateListener;
import kaitou.ppp.manager.ts.TsDongleManager;

import java.util.List;

/**
 * dongle记录DAO事务控制层实现.
 * User: 赵立伟
 * Date: 2015/6/13
 * Time: 23:51
 */
public class TsDongleManagerImpl extends BaseFileDaoManager<TSDongle> implements TsDongleManager, EngineerTSUpdateListener {
    @Override
    public Class<TSDongle> domainClass() {
        return TSDongle.class;
    }

    @Override
    public void updateEngineerEvent(EngineerTS... engineers) {
        List<TSDongle> tsDongleList = queryAll();
        for (TSDongle dongle : tsDongleList) {
            for (EngineerTS engineerTS : engineers) {
                if (engineerTS.getEmployeeNo().equals(dongle.getEmployeeNo())) {
                    dongle.setName(engineerTS.getEngineerName());
                    dongle.setEmail(engineerTS.getEmail());
                }
            }
        }
        save(tsDongleList);
    }
}
