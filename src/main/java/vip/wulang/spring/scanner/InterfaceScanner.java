package vip.wulang.spring.scanner;

import java.util.List;

/**
 * This class is used to scan all classes that implement a particular interface in a Java file.
 *
 * @author CoolerWu on 2018/12/13.
 * @version 1.0
 */
public class InterfaceScanner implements Scanner {
    private Class interfaceClass;
    private String className;

    public InterfaceScanner() {}

    public InterfaceScanner(Class interfaceClass) {
        this.interfaceClass = interfaceClass;
        this.className = interfaceClass.getName();
    }

    public void init() {

    }

    @Override
    public List startScan() {
        return null;
    }
}
