package vip.wulang.netty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author coolerwu
 * @version 1.0
 */
public class ServerSocketDemo {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(80);

        while (true) {
            Socket accept = server.accept();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(accept.getInputStream())
            );
            String text;
            while ((text = br.readLine()) != null) {
                System.out.println("=========");
                System.out.println(text);
                System.out.println("=========");
            }
        }
    }
}
