package vip.wulang.spring.server.inherit;

import vip.wulang.spring.server.inherit.chain.InboundChain;
import vip.wulang.spring.server.inherit.chain.OutboundChain;

import java.io.IOException;

/**
 * Server bootstrap.
 *
 * @author coolerwu
 * @version 1.0
 */
public class ServerBootstrap {
    private final NioServer nioServer;

    public ServerBootstrap(int port) throws IOException {
        nioServer = new NioServer(port);
    }

    public ServerBootstrap() throws IOException {
        nioServer = new NioServer(80);
    }

    public ServerBootstrap inbound(InboundChain chain) {
        chain.register();
        return this;
    }

    public ServerBootstrap outbound(OutboundChain chain) {
        chain.register();
        return this;
    }

    public NioServer nioServer() {
        return nioServer;
    }

    public void start() throws IOException {
        nioServer.start();
    }

    public void close() throws IOException {
        nioServer.close();
    }
}
