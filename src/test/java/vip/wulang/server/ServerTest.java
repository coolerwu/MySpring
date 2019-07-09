package vip.wulang.server;

import org.junit.Test;
import vip.wulang.spring.server.netty.Server;
import vip.wulang.spring.server.netty.handler.InboundInitHandler;
import vip.wulang.spring.server.netty.handler.OutboundInitHandler;
import vip.wulang.spring.server.netty.impl.MainServer;

/**
 * @author coolerwu
 * @version 1.0
 */
public class ServerTest {

    @Test
    public void test01() throws InterruptedException {
        Server server = new MainServer(80);
        server.add(new InboundInitHandler());
        server.add(new OutboundInitHandler());
        server.start();
    }
}
