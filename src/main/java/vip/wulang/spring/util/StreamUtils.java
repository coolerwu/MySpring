package vip.wulang.spring.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author coolerwu
 * @version 1.0
 */
public class StreamUtils {

    private StreamUtils() {
    }

    public static String transformOneLine(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        return br.readLine();
    }
}
