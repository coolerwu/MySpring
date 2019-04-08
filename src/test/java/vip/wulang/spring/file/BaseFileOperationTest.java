package vip.wulang.spring.file;

import org.junit.Test;
import vip.wulang.spring.core.ApplicationContextStarter;
import vip.wulang.spring.exception.ConstructorOneMoreException;
import vip.wulang.spring.exception.NewInstanceFailedException;
import vip.wulang.spring.core.scanner.Scanner;
import vip.wulang.spring.spring.B;
import vip.wulang.spring.spring.Config;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.LockSupport;

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

    @Test
    public void test05() throws IOException, ClassNotFoundException {
        ServiceLoader<Scanner> load = ServiceLoader.load(Scanner.class);

        for (Scanner scanner : load) {
            List list = scanner.startScan();
            System.out.println(list);
        }
    }

    @Test
    public void test06() {
        Thread thread = new Thread(new MyParkRunnable());
        thread.start();
        LockSupport.unpark(thread);
    }

    public static
    class MyParkRunnable implements Runnable {
        @Override
        public void run() {
            LockSupport.park();
            System.out.println("I am MyRunnable.");
        }
    }

    @Test
    public void test07() throws InterruptedException {
        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                LockSupport.park("线程a的blocker数据");
                System.out.println("我是被线程b唤醒后的操作");
            }
        });
        a.start();

        //让当前主线程睡眠1秒，保证线程a在线程b之前执行
        Thread.sleep(1000);
        Thread b = new Thread(new Runnable() {
            @Override
            public void run() {

                String before = (String) LockSupport.getBlocker(a);
                System.out.println("阻塞时从线程a中获取的blocker------>" + before);
                LockSupport.unpark(a);

                //这里睡眠是，保证线程a已经被唤醒了
                try {
                    Thread.sleep(1000);
                    String after = (String) LockSupport.getBlocker(a);
                    System.out.println("唤醒时从线程a中获取的blocker------>" + after);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        b.start();

        Thread.sleep(1000);
    }

    @Test
    public void test08() throws ConstructorOneMoreException, NewInstanceFailedException, IOException {
        ApplicationContextStarter a = new ApplicationContextStarter(Config.class);
        a.start();
        a.over();
        B bean = a.getBean(B.class);
        System.out.println(bean);
        bean = (B) a.getBean("B");
        System.out.println(bean);
    }

    @Test
    public void test09() {
        System.out.println(new File(System.getProperty("user.dir"), "head/address/default/default_head_img.png"));
    }

    @Test
    public void test10() {
        System.out.println(Long.toHexString(0x123AB));
    }
}