package vip.wulang.spring.file;

import org.junit.Test;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

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

    @Test
    public void test03() throws InterruptedException {
        new Thread(new MyRunnable()).start();
        new MyThread().start();
        new Thread(new FutureTask<>(new MyCallable())).start();
    }

    public static
    class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("I am MyRunnable.");
        }
    }

    public static
    class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("I am MyThread.");
        }
    }

    public static
    class MyCallable implements Callable<Object> {
        @Override
        public Object call() throws Exception {
            System.out.println("I am MyCallable.");
            return null;
        }
    }


    @Test
    public void test04() {
        ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = mxBean.getThreadInfo(mxBean.getAllThreadIds());
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println(threadInfo.getThreadName());
        }
    }

}