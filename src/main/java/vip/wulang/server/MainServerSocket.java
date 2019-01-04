package vip.wulang.server;

import vip.wulang.server.exception.MainServerSocketException;
import vip.wulang.server.request.HttpRequest;
import vip.wulang.utils.StringUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author CoolerWu on 2019/1/5.
 * @version 1.0
 */
public class MainServerSocket {
    private static final String EMPTY_STRING = "";
    private static final String SPACE_STRING = " ";
    private static final String SLASH_STRING = "/";
    private static final String LINE_STRING = "\n\r";
    private static final String COLON_STRING = ":";
    private static final String NULL_STRING = null;
    private static final String UTF8_STRING = "UTF-8";

    private ServerSocket server;
    private HttpRequest request;

    public MainServerSocket(int port) throws MainServerSocketException {
        try {
            this.server = new ServerSocket(port);
            this.request = new HttpRequest();
        } catch (IOException e) {
            throw new MainServerSocketException();
        }
    }

    public void start() throws MainServerSocketException {
        try {
            Socket accept_server = server.accept();
            decodeInputStream(accept_server.getInputStream());
        } catch (IOException e) {
            throw new MainServerSocketException();
        }
    }

    private void decodeInputStream(InputStream is)
            throws MainServerSocketException, UnsupportedEncodingException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is, UTF8_STRING));
        try {
            String str;
            if (!Objects.equals(str = br.readLine(), EMPTY_STRING)) {
                String[] status = StringUtils.splitString(str, SPACE_STRING);
                if (Objects.isNull(status) || status.length != 3) {
                    throw new MainServerSocketException();
                }

                request.setMethod(status[0]);
                request.setUri(status[1]);
                request.setVersion(status[2]);
                Map<String, String> headers = new HashMap<>();
                while (!Objects.equals(str = br.readLine(), EMPTY_STRING)) {
                    String[] two_line = StringUtils.splitStringOnlyFirstTime(str, COLON_STRING);
                    if (Objects.isNull(two_line)) {
                        break;
                    }

                    headers.put(StringUtils.toLowerCase(two_line[0]), two_line[1]);
                }

                request.setHeaders(headers);
                int content_len = 0;
                try {
                    content_len = Integer.parseInt(
                            request.getHeaders().getOrDefault("Content-Length", "0"));
                } catch (Exception e) {
                    throw new MainServerSocketException();
                }

                if (content_len != 0) {
                    char[] content_char_arr = new char[content_len];
                    if (br.read(content_char_arr) == -1) {
                        return;
                    }

                    request.setExtra(new String(content_char_arr));
                }
            }
        } catch (Exception e) {
            throw new MainServerSocketException();
        }
    }
}
