package vip.wulang.spring.server;

import java.io.IOException;

/**
 * @author coolerwu
 * @version 1.0
 */
public interface IServer {
    /**
     * Start server.
     */
    void start() throws IOException;

    /**
     * Stop server running.
     */
    void stop() throws IOException;

    /**
     * Deal with input stream.
     * @param handleStream {@link IHandleStream}.
     * @return registered success or failure.
     */
    boolean registerHandleStream(IHandleStream handleStream);
}
