package kaitou.ppp.app.ui.table.queryobject;

import kaitou.ppp.domain.tech.TechSupport;

/**
 * 技术支援查询对象.
 * User: 赵立伟
 * Date: 2015/5/19
 * Time: 18:36
 */
public class TechSupportQueryObject extends BaseQueryObject<TechSupport> {
    @Override
    public boolean autoResizeMode() {
        return false;
    }
}
