package vip.wulang.spring.server.inherit.factory.impl;

import vip.wulang.spring.server.inherit.chain.InboundChain;
import vip.wulang.spring.server.inherit.factory.IChainFactory;

import java.util.LinkedList;

/**
 * Inbound chain factory.
 *
 * @author coolerwu
 * @version 1.0
 */
public class InboundChainFactory implements IChainFactory<InboundChain> {
    // static
    private static volatile InboundChainFactory instance = null;

    // field
    private LinkedList<InboundChain> container;

    private InboundChainFactory() {
        container = new LinkedList<>();
    }

    public static InboundChainFactory getInstance() {
        if (instance == null) {
            synchronized (InboundChainFactory.class) {
                if (instance == null) {
                    instance = new InboundChainFactory();
                }
            }
        }

        return instance;
    }

    @Override
    public void addLast(InboundChain chain) {
        this.container.addLast(chain);
    }

    @Override
    public void addFirst(InboundChain chain) {
        this.container.addFirst(chain);
    }

    @Override
    public void remove(InboundChain chain) {
        this.container.remove(chain);
    }

    public LinkedList<InboundChain> getContainer() {
        return container;
    }
}
