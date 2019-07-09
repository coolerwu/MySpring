package vip.wulang.thread;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author coolerwu
 * @version 1.0
 */
public class ExecutorTest {

    @Test
    public void test01() {
        ExecutorService service = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            service.execute(() -> {
                System.out.println("hello");
                System.out.println("hello");
            });
        }
    }
}
