package kaitou.ppp.domain.system;

import kaitou.ppp.domain.BaseDomain;
import org.apache.commons.lang.StringUtils;

/**
 * 操作日志.
 * User: 赵立伟
 * Date: 2015/7/3
 * Time: 14:38
 */
public class OperationLog extends BaseDomain {
    /**
     * 操作日志
     */
    private String opLog;

    @Override
    public String dbFileName() {
        return null;
    }

    @Override
    public String dbFileSuffix() {
        return null;
    }

    @Override
    public String toString() {
        return "OperationLog{" +
                "opLog='" + opLog + '\'' +
                '}';
    }

    /**
     * 将日志文件中一条操作记录显示出来
     *
     * @param oneLog 一条操作记录
     * @return 操作日志对象
     */
    public static OperationLog display(String oneLog) {
        if (StringUtils.isEmpty(oneLog)) {
            return null;
        }
        return new OperationLog().setOpLog(oneLog);
    }

    public String getOpLog() {
        return opLog;
    }

    public OperationLog setOpLog(String opLog) {
        this.opLog = opLog;
        return this;
    }
}
