package vip.wulang.spring;

import org.junit.Test;
import vip.wulang.spring.core.scanner.UrlScanner;

import java.io.IOException;
import java.util.List;

/**
 * @author coolerwu
 * @version 1.0
 */
public class UrlScannerTest {
    @Test
    public void test01() throws IOException {
        UrlScanner u = new UrlScanner("vip.wulang");
        List<String> strings = u.startScan();
        System.out.println(strings);
    }
}
