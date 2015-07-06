package kaitou.ppp.app.ui.table.queryobject.ts;

import com.womai.bsp.tool.utils.BeanCopyUtil;
import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.app.ui.dialog.InputHint;
import kaitou.ppp.app.ui.table.queryobject.BaseQueryObject;
import kaitou.ppp.domain.BaseDomain;
import kaitou.ppp.domain.ts.TSSDSPermission;
import org.joda.time.DateTime;

import javax.swing.*;

import static kaitou.ppp.app.ui.table.OPManager.*;

/**
 * TS SDS权限查询对象.
 * User: 赵立伟
 * Date: 2015/5/20
 * Time: 17:28
 */
public class TSSDSPermissionQueryObject extends BaseQueryObject<TSSDSPermission> {
    @Override
    public boolean tableBtnEvent(JFrame frame, BaseDomain domain) {
        TSSDSPermission outPermission = (TSSDSPermission) domain;
        InputHint inputHint = new InputHint(frame, new String[]{"到期时间"});
        if (!inputHint.isOk()) {
            return false;
        }
        final TSSDSPermission newPermission = new TSSDSPermission();
        BeanCopyUtil.copyBean(outPermission, newPermission);
        newPermission.setEndDate(inputHint.getInputResult()[0]);
        newPermission.setApplyDate(new DateTime().toString("yyyy/MM/dd"));
        doRunWithWaiting(frame, new OpRunnable() {
            @Override
            public void run() {
                saveOrUpdate(domainType(), CollectionUtil.toArray(CollectionUtil.newList(newPermission), TSSDSPermission.class));
            }
        }, "");
        return true;
    }
}
