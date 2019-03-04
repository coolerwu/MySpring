package vip.wulang.spring.util;

import java.net.URL;

/**
 * @author CoolerWu on 2019/3/3.
 * @version 1.0
 */
public class ResourceUtils {
    private static final String URL_PROTOCOL_FILE = "file";
    private static final String URL_PROTOCOL_JAR = "jar";
    private static final String URL_PROTOCOL_WAR = "war";
    private static final String URL_PROTOCOL_ZIP = "zip";
    private static final String URL_PROTOCOL_WSJAR = "wsjar";
    private static final String URL_PROTOCOL_VFSZIP = "vfszip";
    private static final String URL_PROTOCOL_VFSFILE = "vfsfile";
    private static final String URL_PROTOCOL_VFS = "vfs";

    private ResourceUtils() {
    }

    public static boolean isJarURL(URL url) {
        String protocol = url.getProtocol();
        return (URL_PROTOCOL_JAR.equals(protocol) || URL_PROTOCOL_WAR.equals(protocol) ||
                URL_PROTOCOL_ZIP.equals(protocol) || URL_PROTOCOL_VFSZIP.equals(protocol) ||
                URL_PROTOCOL_WSJAR.equals(protocol));
    }

    public static boolean isFileURL(URL url) {
        String protocol = url.getProtocol();
        return (URL_PROTOCOL_FILE.equals(protocol) || URL_PROTOCOL_VFSFILE.equals(protocol) ||
                URL_PROTOCOL_VFS.equals(protocol));
    }
}
