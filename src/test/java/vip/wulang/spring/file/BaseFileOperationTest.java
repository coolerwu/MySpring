package vip.wulang.spring.file;

import org.junit.Test;

import java.io.IOException;

/**
 * @author CoolerWu on 2019/1/23.
 * @version 1.0
 */
public class BaseFileOperationTest {

    @Test
    public void test01() throws IOException {
        BaseFileOperation b = new BaseFileOperation("/test.log", 1024);
        for (int i = 0; i < 1024 * 2; i++) {
            b.write(new String(i + "").getBytes(), Boolean.FALSE);
        }
    }

    @Test
    public void test02() throws IOException {
        BaseFileOperation b = new BaseFileOperation("/test.log", 1024);
        byte[] read = b.read();
        System.out.println(new String(read));
        System.out.println("==============");
        read = b.read();
        System.out.println(new String(read));
    }
}