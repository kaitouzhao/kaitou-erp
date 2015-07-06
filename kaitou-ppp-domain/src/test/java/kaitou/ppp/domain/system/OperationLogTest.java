package kaitou.ppp.domain.system;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * 单元测试.
 * User: 赵立伟
 * Date: 2015/7/3
 * Time: 14:46
 */
public class OperationLogTest {
    @Test
    public void testDisplay() {
        assertNull(OperationLog.display(""));
        String oneLog = "2015-06-26 10:22:01,560 | 导入文件：C:\\Users\\zhao\\Desktop\\PPP\\需求\\认定店认定级别数据2015.6.22.xlsx";
        OperationLog operationLog = OperationLog.display(oneLog);
        assertEquals(oneLog, operationLog.getOpLog());
    }
}
