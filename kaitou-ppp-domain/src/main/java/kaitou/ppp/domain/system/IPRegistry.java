package kaitou.ppp.domain.system;

import kaitou.ppp.domain.BaseDomain;

/**
 * IP注册表管理.
 * User: 赵立伟
 * Date: 2015/6/30
 * Time: 16:24
 */
public class IPRegistry extends BaseDomain {
    /**
     * IP地址
     */
    private String ip;

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
        return "IPRegistry{" +
                "ip='" + ip + '\'' +
                '}';
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
