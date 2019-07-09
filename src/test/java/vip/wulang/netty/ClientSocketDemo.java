package vip.wulang.netty;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author coolerwu
 * @version 1.0
 */
public class ClientSocketDemo {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 80);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("woaini\nadsad".getBytes());
        outputStream.close();
    }
}
