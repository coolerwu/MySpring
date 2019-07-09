package vip.wulang.netty.nio;

import org.junit.Test;
import vip.wulang.spring.server.inherit.NioServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author coolerwu
 * @version 1.0
 */
public class NioServerSocket {
    private final int port;

    public NioServerSocket(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        Selector selector = Selector.open();
        ssChannel.bind(new InetSocketAddress(port));
        ssChannel.configureBlocking(false);
        ssChannel.register(selector, SelectionKey.OP_ACCEPT);
        final ByteBuffer msg = ByteBuffer.wrap("Hello".getBytes());

        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();

                if (selectionKey.isConnectable()) {
                    System.out.println("Connect: " + selectionKey.channel().getClass());
                }

                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel client = channel.accept();
                    client.configureBlocking(false);
                    client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, msg.duplicate());
                    System.out.println("Accept: " + client);
                }

                if (selectionKey.isReadable()) {

                }

                if (selectionKey.isWritable()) {
                    SocketChannel client = (SocketChannel) selectionKey.channel();
                    ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                    while (byteBuffer.hasRemaining()) {
                        if (client.write(byteBuffer) == 0) {
                            break;
                        }
                    }
                    client.close();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new NioServerSocket(80).start();
    }
}
