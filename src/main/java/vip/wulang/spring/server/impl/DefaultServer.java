package vip.wulang.spring.server.impl;

import vip.wulang.spring.exception.ServerAlreadyInitException;
import vip.wulang.spring.exception.SocketStreamNullException;
import vip.wulang.spring.server.IHandleStream;
import vip.wulang.spring.server.IServer;
import vip.wulang.spring.server.StartStatus;
import vip.wulang.spring.server.request.EncloseStream;
import vip.wulang.spring.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author coolerwu
 * @version 1.0
 */
public class DefaultServer implements IServer {
    private int port;
    private StartStatus status = StartStatus.START_NONE;
    private ServerSocket server;
    private ConcurrentMap<String, IHandleStream> handleStreamContainer;

    public DefaultServer() {
        port = 80;
    }

    private void init() throws IOException {
        if (status != StartStatus.START_NONE) {
            throw new ServerAlreadyInitException();
        }

        status = StartStatus.START_PRE;
        server = new ServerSocket(port);
        handleStreamContainer = new ConcurrentHashMap<>();
        IHandleStream handleStream = new Http11HandleStream();
        handleStreamContainer.put(handleStream.protocol(), handleStream);
    }

    private void choose(InputStream is, OutputStream os) throws IOException {
        String headerLine = StreamUtils.transformOneLine(is);

        if (headerLine == null) {
            throw new SocketStreamNullException();
        }

        headerLine = headerLine.toLowerCase();

        for (Map.Entry<String, IHandleStream> entry : handleStreamContainer.entrySet()) {
            if (headerLine.endsWith(entry.getKey())) {
                IHandleStream value = entry.getValue();

                if (value != null) {
                    System.out.println("==========");
                    EncloseStream encloseStream = value.handle(is, os);
                    System.out.println(encloseStream);
                }
            }
        }

        os.write("ok".getBytes());
        os.close();
        System.out.println("end");
    }

    private void execute() throws IOException {
        while (status == StartStatus.START_POST) {
            Socket socket = server.accept();
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            choose(is, os);
        }
    }

    @Override
    public void start() throws IOException {
        init();
        status = StartStatus.START_POST;
        execute();
    }

    @Override
    public void stop() throws IOException {
        if (StartStatus.START_NONE == status) {
            return;
        }

        if (server != null) {
            server.close();
        }

        status = StartStatus.START_DONE;
    }

    @Override
    public boolean registerHandleStream(IHandleStream handleStream) {
        handleStreamContainer.put(handleStream.protocol(), handleStream);
        return handleStreamContainer.get(handleStream.protocol()) != null;
    }
}
