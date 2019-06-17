package vip.wulang.spring.server.inherit.chain;

import vip.wulang.spring.server.inherit.enclose.OutboundChannel;
import vip.wulang.spring.server.inherit.exception.ForbidUseException;
import vip.wulang.spring.server.inherit.factory.impl.InboundChainFactory;

/**
 * Inbound chain.
 *
 * @author coolerwu
 * @version 1.0
 */
public interface InboundChain extends Chain {
    @Override
    default void write(OutboundChannel channel) {
        throw new ForbidUseException();
    }

    /**
     * Register.
     */
    default void register() {
        InboundChainFactory.getInstance().addLast(this);
    }
}
