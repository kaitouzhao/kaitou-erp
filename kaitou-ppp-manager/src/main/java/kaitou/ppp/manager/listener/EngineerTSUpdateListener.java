package kaitou.ppp.manager.listener;

import kaitou.ppp.domain.ts.EngineerTS;

/**
 * TS工程师更新监听对象接口.
 * User: 赵立伟
 * Date: 2015/7/5
 * Time: 10:13
 */
public interface EngineerTSUpdateListener {
    /**
     * 更新工程师基本信息事件
     *
     * @param engineers 工程师集合
     */
    public void updateEngineerEvent(EngineerTS... engineers);
}
