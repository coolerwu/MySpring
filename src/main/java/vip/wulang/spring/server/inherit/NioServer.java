package vip.wulang.spring.server.inherit;

import vip.wulang.spring.server.inherit.chain.InboundChain;
import vip.wulang.spring.server.inherit.chain.OutboundChain;
import vip.wulang.spring.server.inherit.enclose.InboundChannel;
import vip.wulang.spring.server.inherit.enclose.OutboundChannel;
import vip.wulang.spring.server.inherit.factory.impl.InboundChainFactory;
import vip.wulang.spring.server.inherit.factory.impl.OutboundChainFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 * Java nio server.
 *
 * @author coolerwu
 * @version 1.0
 */
public class NioServer {
    private final int port;
    private Selector selector;

    public NioServer(int port) throws IOException {
        if (port == 0) {
            port = 80;
        }

        this.port = port;
        init();
    }

    private void init() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void start() throws IOException {
        for (; ; ) {
            try {
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();

                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                }

                if (selectionKey.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    InboundChannel inboundChannel = new InboundChannel(socketChannel);
                    LinkedList<InboundChain> container = InboundChainFactory.getInstance().getContainer();

                    for (InboundChain chain : container) {
                        chain.read(inboundChannel);
                    }
                }

                if (selectionKey.isWritable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    OutboundChannel outboundChannel = new OutboundChannel(socketChannel);
                    LinkedList<OutboundChain> container = OutboundChainFactory.getInstance().getContainer();

                    for (OutboundChain chain : container) {
                        chain.write(outboundChannel);
                    }

                    socketChannel.close();
                }
            }
        }
    }

    public void close() throws IOException {
        selector.close();
    }
}
