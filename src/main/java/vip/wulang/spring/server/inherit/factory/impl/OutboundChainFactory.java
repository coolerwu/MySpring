package vip.wulang.spring.server.inherit.factory.impl;

import vip.wulang.spring.server.inherit.chain.OutboundChain;
import vip.wulang.spring.server.inherit.factory.IChainFactory;

import java.util.LinkedList;

/**
 * Outbound chain factory.
 *
 * @author coolerwu
 * @version 1.0
 */
public class OutboundChainFactory implements IChainFactory<OutboundChain> {
    // static
    private static volatile OutboundChainFactory instance = null;

    // field
    private LinkedList<OutboundChain> container;

    private OutboundChainFactory() {
        container = new LinkedList<>();
    }

    public static OutboundChainFactory getInstance() {
        if (instance == null) {
            synchronized (InboundChainFactory.class) {
                if (instance == null) {
                    instance = new OutboundChainFactory();
                }
            }
        }

        return instance;
    }

    @Override
    public void addLast(OutboundChain chain) {
        this.container.addLast(chain);
    }

    @Override
    public void addFirst(OutboundChain chain) {
        this.container.addFirst(chain);
    }

    @Override
    public void remove(OutboundChain chain) {
        this.container.remove(chain);
    }

    public LinkedList<OutboundChain> getContainer() {
        return container;
    }
}
