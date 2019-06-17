package vip.wulang.spring.server.inherit.enclose;

import vip.wulang.spring.server.inherit.exception.ClosedAlreadyException;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Enclose outbound channel.
 *
 * @author coolerwu
 * @version 1.0
 */
public class OutboundChannel {
    private SocketChannel socketChannel;
    private int writableByte;
    private boolean closed = false;

    public OutboundChannel(SocketChannel socketChannel) {
        if (socketChannel == null) {
            throw new NullPointerException();
        }

        this.socketChannel = socketChannel;
        this.writableByte = 1024 * 1024;
    }

    public OutboundChannel(SocketChannel socketChannel, int writeableByte) {
        if (socketChannel == null) {
            throw new NullPointerException();
        }

        this.socketChannel = socketChannel;

        if (writeableByte <= 0) {
            writeableByte = 1024 * 1024;
        }

        this.writableByte = writeableByte;
    }

    public void write(byte[] bytes) throws IOException {
        if (closed) {
            throw new ClosedAlreadyException();
        }

        int count = bytes.length / writableByte;

        if (count == 0) {
            socketChannel.write(ByteBuffer.wrap(bytes));
        }

        int offset = 0;

        for (int i = 0; i < count; i++) {
            int len = bytes.length - offset > writableByte ? writableByte : bytes.length - offset;
            socketChannel.write(ByteBuffer.wrap(bytes, offset, len));
        }
    }

    public void write(String str) throws IOException {
        write(str.getBytes());
    }

    public void close() throws IOException {
        if (closed) {
            return;
        }

        socketChannel.close();
    }
}
