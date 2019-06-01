package vip.wulang.spring.server;

import vip.wulang.spring.server.request.EncloseStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author coolerwu
 * @version 1.0
 */
public interface IHandleStream {
    /**
     * Protocol of IHandleStream.
     * @return protocol of IHandleStream.
     */
    String protocol();

    /**
     * Handles input stream and output stream.
     * @param is {@link InputStream}.
     * @param os {@link OutputStream}.
     * @return Return {@link EncloseStream}.
     */
    EncloseStream handle(InputStream is, OutputStream os) throws IOException;
}
