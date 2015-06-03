package kaitou.ppp.common.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;

/**
 * 日志操作管理基类.
 * User: 赵立伟
 * Date: 2015/1/30
 * Time: 14:40
 */
public abstract class BaseLogManager {
    /**
     * 操作记录日志
     */
    protected static final Log OPERATION_LOG = LogFactory.getLog("kaitou.operation");
    /**
     * 系统记录日志
     */
    protected static final Log SYSTEM_LOG = LogFactory.getLog("kaitou.ppp");
    /**
     * 调试时间格式
     */
    private static final String DATE_FORMAT_MM_SS_SSS = "mm:ss SSS";
    /**
     * 调试时间分隔符
     */
    private static final char DEBUG_TIME_SPLIT = ':';

    /**
     * 记录系统日志
     * <p>
     * 根据不同的日志级别输出
     * </p>
     *
     * @param e 异常
     */
    public static void logSystemEx(Throwable e) {
        if (SYSTEM_LOG.isDebugEnabled()) {
            SYSTEM_LOG.debug("系统调试", e);
            return;
        }
        if (SYSTEM_LOG.isInfoEnabled()) {
            SYSTEM_LOG.info("系统日志", e);
            return;
        }
        if (SYSTEM_LOG.isWarnEnabled()) {
            SYSTEM_LOG.warn("系统警告", e);
            return;
        }
        SYSTEM_LOG.error("系统出错", e);
    }

    /**
     * 记录系统日志
     * <p>
     * 只输出DEBUG和INFO级别的日志
     * </p>
     *
     * @param info 日志
     */
    public static void logSystemInfo(String info) {
        if (SYSTEM_LOG.isDebugEnabled()) {
            SYSTEM_LOG.debug(info);
            return;
        }
        if (SYSTEM_LOG.isInfoEnabled()) {
            SYSTEM_LOG.info(info);
        }
    }

    /**
     * 调试时间
     *
     * @param info 步骤名称
     */
    public static void debugTime(String info) {
        logSystemInfo(info + DEBUG_TIME_SPLIT + new DateTime().toString(DATE_FORMAT_MM_SS_SSS));
    }

    /**
     * 记录操作日志
     *
     * @param info 日志内容
     */
    protected static void logOperation(String info) {
        OPERATION_LOG.info(info);
    }

}
