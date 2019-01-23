package vip.wulang.spring.server;

import org.junit.Test;
import vip.wulang.spring.exception.MainServerSocketException;
import vip.wulang.spring.exception.NotDefExecutorServiceType;

import java.util.concurrent.ExecutionException;

/**
 * @author CoolerWu on 2019/1/5.
 * @version 1.0
 */
public class MainServerSocketTest {

    @Test
    public void testConnect() throws MainServerSocketException {
        MainServerSocket serverSocket = new MainServerSocket(9000);
        serverSocket.start();
    }

    @Test
    public void testConnectA() throws MainServerSocketException, NotDefExecutorServiceType,
            ExecutionException, InterruptedException {
        MainServerThreadExecutor.EncapsulationResult<Boolean> re = MainServerThreadExecutor.initAsync(9000);

        Thread.sleep(30000);

        re.getExecutor().shutdownNow();
        System.out.println(re.getTask().get());
    }

    @Test
    public void testSPI() {
        System.out.println(MainServerSocketTest.class.getClassLoader());
        System.out.println(Integer.class.getClassLoader());
        System.out.println(MainServerSocketTest.class.getClassLoader().getParent());
        System.out.println(Thread.currentThread().getContextClassLoader());
    }

}
