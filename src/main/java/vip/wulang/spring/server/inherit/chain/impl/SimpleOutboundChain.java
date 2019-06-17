package vip.wulang.spring.server.inherit.chain.impl;

import vip.wulang.spring.server.inherit.chain.OutboundChain;
import vip.wulang.spring.server.inherit.enclose.OutboundChannel;

import java.io.IOException;

/**
 * @author coolerwu
 * @version 1.0
 */
public class SimpleOutboundChain implements OutboundChain {
    public SimpleOutboundChain() {
    }

    @Override
    public void write(OutboundChannel channel) throws IOException {
        String str = "HTTP/1.1 200 OK\n" +
                "Content-Type: application/json\n" +
                "\n" +
                "{\"map\": \"hah\"}";
        channel.write(str);
    }
}
