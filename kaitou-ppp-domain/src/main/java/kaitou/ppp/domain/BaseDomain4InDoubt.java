package kaitou.ppp.domain;

/**
 * 允许数据存疑的domain父类.
 * User: 赵立伟
 * Date: 2015/4/17
 * Time: 9:13
 */
public abstract class BaseDomain4InDoubt extends BaseDomain {

    private static final String NO_DOUBT = "false";
    private static final String IN_DOUBT = "true";
    /**
     * 存疑。默认为false，即不存疑
     */
    protected String inDoubt = NO_DOUBT;

    public String getInDoubt() {
        return inDoubt;
    }

    /**
     * 是否存疑
     *
     * @return 是为真
     */
    public boolean isInDoubt() {
        return IN_DOUBT.equals(inDoubt);
    }

    public void setInDoubt(String inDoubt) {
        this.inDoubt = inDoubt;
    }

    /**
     * 确实存疑
     */
    public void doInDoubt() {
        inDoubt = IN_DOUBT;
    }

    /**
     * 不存疑
     */
    public void noDoubt() {
        inDoubt = NO_DOUBT;
    }
}
