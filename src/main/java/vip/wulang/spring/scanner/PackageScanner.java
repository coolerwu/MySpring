package vip.wulang.spring.scanner;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to scan the Java files contained in the package.
 *
 * @author CoolerWu on 2018/12/9.
 * @version 1.0
 */
public class PackageScanner implements Scanner {
    private static final String EMPTY_STRING = "";
    private static final String POINT_STRING = ".";
    private static final char POINT_CHAR = '.';
    private static final char SLASH_CHAR = '/';
    private static final String POINT_CLASS_STRING = ".class";
    private static final String SLASH_STRING = "/";

    private static PackageScanner instance;

    /**
     * This private variable represents the root directory of the package to
     * be scanned and defaults to all files, for example, "vip.wulang.base".
     */
    private String basePackage;

    /**
     * Scans result storage.
     */
    private List<String> resultStorage = new ArrayList<>();

    public PackageScanner() {
        basePackage = EMPTY_STRING;
    }

    public PackageScanner(String basePackage) {
        this.basePackage = basePackage;
    }

    /**
     * Start scanning.
     * @return scanning results
     */
    @Override
    public List<String> startScan() {
        return (resultStorage = findClasses(replacePointToFileString()));
    }

    /**
     * Replace the dot with a slash.
     * @return object of {@link File}.
     */
    @SuppressWarnings("all")
    private File replacePointToFileString() {
        File file;
        URL rootURL = Thread.currentThread().getContextClassLoader().getResource(EMPTY_STRING);

        if (EMPTY_STRING.equals(basePackage)) {
            file = new File(
                    rootURL.getPath(),
                    SLASH_STRING
            );
        } else {
            file = new File(
                    rootURL.getPath(),
                    basePackage.replace(POINT_CHAR, SLASH_CHAR)
            );
        }

        return file;
    }

    /**
     * Look for the Java Class file.
     * @param file object of {@link File}
     * @return scanning results
     */
    private List<String> findClasses(File file) {
        List<String> classPathContainer = new ArrayList<>();
        String basePackageLocal;
        int index = basePackage.lastIndexOf(POINT_STRING);

        if (!EMPTY_STRING.equals(basePackage) && index != -1) {
            basePackageLocal = basePackage.substring(0, index);
        } else {
            basePackageLocal = EMPTY_STRING;
        }

        findAnyPathJava(file, classPathContainer, basePackageLocal);
        return classPathContainer;
    }

    /**
     * Search each folder.
     * @param file object of {@link File}
     * @param container scanning results
     * @param name hierarchical structure
     */
    @SuppressWarnings("all")
    private void findAnyPathJava(File file, List<String> container, String name) {
        if (file.isDirectory() && file.listFiles() != null) {
            for (File childFile : file.listFiles()) {
                if (EMPTY_STRING.equals(name)) {
                    findAnyPathJava(childFile, container, file.getName());
                    continue;
                }

                findAnyPathJava(childFile, container, name + POINT_STRING + file.getName());
            }
        } else if (file.isFile() && file.getName().endsWith(POINT_CLASS_STRING)) {
            container.add(name + POINT_STRING +
                    file.getName().substring(0, file.getName().lastIndexOf(POINT_STRING)));
        }
    }

    /**
     * Gets the result of scanning all files.
     * @return scanning results
     */
    public static List<String> getTheResultOfScanningAllFiles() {
        if (instance == null) {
            synchronized (PackageScanner.class) {
                if (instance == null) {
                    instance = new PackageScanner();
                    instance.startScan();
                }
            }
        }

        return instance.resultStorage;
    }
}
