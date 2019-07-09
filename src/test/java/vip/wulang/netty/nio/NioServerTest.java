package vip.wulang.netty.nio;

import org.junit.Test;
import vip.wulang.spring.server.inherit.ServerBootstrap;
import vip.wulang.spring.server.inherit.chain.impl.SimpleOutboundChain;

import java.io.IOException;

/**
 * @author coolerwu
 * @version 1.0
 */
public class NioServerTest {
    @Test
    public void test01() throws IOException {
        new ServerBootstrap().outbound(new SimpleOutboundChain()).start();
    }
}
