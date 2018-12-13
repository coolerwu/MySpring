package vip.wulang.test;

import vip.wulang.spring.scanner.PackageScanner;

/**
 * @author CoolerWu on 2018/12/12.
 * @version 1.0
 */
public class PackageScannerTest {
    public static void main(String[] args) {
        System.out.println(new PackageScanner().startPackageScan());
    }
}
