package vip.wulang.spring.scanner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * The class is used for scanning packages.
 *
 * @author CoolerWu on 2018/12/9.
 * @version 1.0
 */
@SuppressWarnings("all")
public class PackageScanner {
    private static final String EMPTY_STRING = "";
    private static final String POINT_STRING = ".";
    private static final char POINT_CHAR = '.';
    private static final char SPRIT_CHAR = '/';
    private static final String POINT_CLASS_STRING = ".class";
    private static final String SPRIT_STRING = "/";

    private String basePackage;

    public PackageScanner() {
        basePackage = EMPTY_STRING;
    }

    public PackageScanner(String basePackage) {
        this.basePackage = basePackage;
    }

    public List<String> startPackageScan() {
        File rootFile = replacePointToFileString();
        List<String> container = findClasses(rootFile);

        return container;
    }

    private File replacePointToFileString() {
        File file;
        if (EMPTY_STRING.equals(basePackage)) {
            file = new File(
                    Thread.currentThread().getContextClassLoader().getResource(EMPTY_STRING).getPath(),
                    SPRIT_STRING
            );
        } else {
            file = new File(
                    Thread.currentThread().getContextClassLoader().getResource(EMPTY_STRING).getPath(),
                    basePackage.replace(POINT_CHAR, SPRIT_CHAR)
            );
        }

        return file;
    }

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
}
