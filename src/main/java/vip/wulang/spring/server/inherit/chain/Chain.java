package vip.wulang.spring.server.inherit.chain;

import vip.wulang.spring.server.inherit.enclose.InboundChannel;
import vip.wulang.spring.server.inherit.enclose.OutboundChannel;

import java.io.IOException;

/**
 * Chain interface.
 *
 * @author coolerwu
 * @version 1.0
 */
public interface Chain {
    /**
     * Write data.
     *
     * @param channel OutboundChannel Object.
     */
    void write(OutboundChannel channel) throws IOException;

    /**
     * Read data.
     *
     * @param channel InboundChannel Object.
     */
    void read(InboundChannel channel) throws IOException;
}
