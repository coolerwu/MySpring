package vip.wulang.spring.server.inherit.enclose;

import vip.wulang.spring.server.inherit.exception.ClosedAlreadyException;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Enclose inbound channel.
 *
 * @author coolerwu
 * @version 1.0
 */
public class InboundChannel {
    private SocketChannel socketChannel;
    private int readableByte;
    private ByteBuffer buffer;
    private boolean loadReady;
    private boolean closed = false;

    public InboundChannel(SocketChannel socketChannel) {
        if (socketChannel == null) {
            throw new NullPointerException();
        }

        this.socketChannel = socketChannel;
        this.readableByte = 1024 * 1024;
        this.buffer = ByteBuffer.allocate(readableByte);
        this.loadReady = false;
    }

    public InboundChannel(SocketChannel socketChannel, int readableByte) {
        if (socketChannel == null) {
            throw new NullPointerException();
        }

        this.socketChannel = socketChannel;

        if (readableByte <= 0) {
            readableByte = 1024 * 1024;
        }

        this.readableByte = readableByte;
        this.buffer = ByteBuffer.allocate(readableByte);
        this.loadReady = false;
    }

    public byte[] readAll() throws IOException {
        byte[] bytes = new byte[readableByte];

        for (int i = 0; i < readableByte; i++) {
            byte b = readOne();

            if (b == -128) {
                byte[] new_bytes = new byte[i];

                if (i == 0) {
                    return null;
                }

                System.arraycopy(bytes, 0, new_bytes, 0, i);
                return new_bytes;
            }

            bytes[i] = b;
        }

        return bytes;
    }

    public byte readOne() throws IOException {
        if (closed) {
            throw new ClosedAlreadyException();
        }

        if (!loadReady) {
            int read = socketChannel.read(buffer);

            if (read <= 0) {
                return -128;
            }

            buffer.flip();
            loadReady = true;
        }

        if (buffer.remaining() > 0) {
            return buffer.get();
        }

        loadReady = false;
        return readOne();
    }

    public void close() throws IOException {
        if (closed) {
            return;
        }

        socketChannel.close();
    }
}
