package vip.wulang.spring.server.inherit.chain;

import vip.wulang.spring.server.inherit.enclose.InboundChannel;
import vip.wulang.spring.server.inherit.exception.ForbidUseException;
import vip.wulang.spring.server.inherit.factory.impl.OutboundChainFactory;

/**
 * Outbound chain.
 *
 * @author coolerwu
 * @version 1.0
 */
public interface OutboundChain extends Chain {
    @Override
    default void read(InboundChannel channel) {
        throw new ForbidUseException();
    }

    /**
     * Register.
     */
    default void register() {
        OutboundChainFactory.getInstance().addLast(this);
    }
}
