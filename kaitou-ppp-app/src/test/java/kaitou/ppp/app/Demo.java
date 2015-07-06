package kaitou.ppp.app;

/**
 * 测试小例子.
 * User: 赵立伟
 * Date: 2015/7/2
 * Time: 10:34
 */
public class Demo {

    public static void main(String[] args) {
        System.out.println(getSerialNoValue("{\"addOrUpdate\":\"更新\",\"applyDate\":\"2014/05/15\",\"city\":\"\",\"email\":\"Weidong_Chen@canon.com.cn\",\"endDate\":\"2014/05/29\",\"engineerName\":\"陈伟栋\",\"mac\":\"\",\"note\":\"\",\"saleRegion\":\"华东\",\"serialNo\":789456789,\"updated\":\"未更新\"}"));
    }

    private static String getSerialNoValue(String db) {
        String substring = db.substring(db.indexOf("\"serialNo\":") + 11);
        return substring.substring(0, substring.indexOf(","));
    }
}
