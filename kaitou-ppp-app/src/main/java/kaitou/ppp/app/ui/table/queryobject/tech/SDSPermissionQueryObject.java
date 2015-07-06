package kaitou.ppp.app.ui.table.queryobject.tech;

import com.womai.bsp.tool.utils.BeanCopyUtil;
import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.app.ui.dialog.InputHint;
import kaitou.ppp.app.ui.table.OPManager;
import kaitou.ppp.app.ui.table.queryobject.BaseQueryObject;
import kaitou.ppp.domain.BaseDomain;
import kaitou.ppp.domain.tech.TechSDSPermission;
import org.joda.time.DateTime;

import javax.swing.*;

import static kaitou.ppp.app.ui.table.OPManager.doRunWithWaiting;
import static kaitou.ppp.app.ui.table.OPManager.saveOrUpdate;

/**
 * SDS权限管理查询对象.
 * User: 赵立伟
 * Date: 2015/5/19
 * Time: 17:02
 */
public class SDSPermissionQueryObject extends BaseQueryObject<TechSDSPermission> {
    @Override
    public boolean tableBtnEvent(JFrame frame, BaseDomain domain) {
        TechSDSPermission outPermission = (TechSDSPermission) domain;
        InputHint inputHint = new InputHint(frame, new String[]{"到期时间"});
        if (!inputHint.isOk()) {
            return false;
        }
        final TechSDSPermission newPermission = new TechSDSPermission();
        BeanCopyUtil.copyBean(outPermission, newPermission);
        newPermission.setEndDate(inputHint.getInputResult()[0]);
        newPermission.setApplyDate(new DateTime().toString("yyyy/MM/dd"));
        doRunWithWaiting(frame, new OPManager.OpRunnable() {
            @Override
            public void run() {
                saveOrUpdate(domainType(), CollectionUtil.toArray(CollectionUtil.newList(newPermission), TechSDSPermission.class));
            }
        }, "");
        return true;
    }
}
