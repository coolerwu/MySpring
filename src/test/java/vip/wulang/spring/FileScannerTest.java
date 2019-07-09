package vip.wulang.spring;

import org.junit.Test;
import vip.wulang.spring.core.annotation.Component;
import vip.wulang.spring.core.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author coolerwu
 * @version 1.0
 */
@Component
public class FileScannerTest {
    @Resource
    private MainConfig mainConfig;

    @Test
    public void test01() {
        int[] ints = new int[10];
        System.out.println(ints[9]);
        Map map = null;
        System.out.println(map.equals(null));
    }
}
