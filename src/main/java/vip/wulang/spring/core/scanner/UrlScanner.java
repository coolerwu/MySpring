package vip.wulang.spring.core.scanner;

import vip.wulang.spring.core.Platform;
import vip.wulang.spring.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * This is used for scanning all classes, not jar.
 *
 * @author CoolerWu on 2019/3/3.
 * @version 1.0
 */
public class UrlScanner implements Scanner {
    private String basePackage;
    private String basePackage_origin;
    private URL resource;

    public UrlScanner() {
        basePackage = File.separator;
    }

    // ps: vip.wulang
    public UrlScanner(String basePackage) {
        if (basePackage == null) {
            throw new NullPointerException();
        }

        this.basePackage_origin = basePackage;
        this.basePackage = "/" + basePackage.replaceAll("\\.", "/");
    }

    private void init() {
        resource = this.getClass().getResource(basePackage);

        if (Objects.isNull(resource)) {
            throw new NullPointerException("URL is null.");
        }
    }

    private List<String> doScan() throws IOException {
        init();
        URLConnection urlConnection = resource.openConnection();
        List<String> result = new LinkedList<>();

        if (ResourceUtils.isJarURL(resource)) {
            JarURLConnection jarURLConnection = (JarURLConnection) urlConnection;
            JarFile jarFile = jarURLConnection.getJarFile();
            Enumeration<JarEntry> entries = jarFile.entries();

            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();

                if (jarEntry.getName().endsWith(".class")) {
                    String os_name = System.getProperty("os.name");
                    String name = jarEntry.getName().replaceAll("/", ".");

                    if (os_name.startsWith(Platform.Windows.toString())) {
                        name = jarEntry.getName().replaceAll(File.separator, ".");
                    } else {
                        basePackage = basePackage.replaceAll(File.separator, ".");
                    }

                    name = name.substring(0, name.lastIndexOf(".class"));
                    result.add(name);
                }

                if (jarEntry.getName().endsWith(".jar")) {
                    result.add(jarEntry.getName());
                }
            }
        } else if (ResourceUtils.isFileURL(resource)) {
            PackageScanner packageScanner = new PackageScanner(basePackage_origin);
            result.addAll(packageScanner.startScan());
        }

        return result;
    }

    @Override
    public List<String> startScan() throws IOException{
        return doScan();
    }
}
