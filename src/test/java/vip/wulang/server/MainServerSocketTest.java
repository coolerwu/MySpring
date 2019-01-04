package vip.wulang.server;

import org.junit.Test;
import vip.wulang.server.exception.MainServerSocketException;

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

}
