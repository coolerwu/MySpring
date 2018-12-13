package vip.wulang.test;

import vip.wulang.spring.core.ApplicationContext;

/**
 * @author CoolerWu on 2018/12/12.
 * @version 1.0
 */
public class ApplicationContextTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ApplicationContext("vip.wulang.test");
        Object user = applicationContext.getBeanByName("user");
        User user1 = applicationContext.getBeanByClass(User.class);

        System.out.println(user);
        System.out.println(user1);
    }

}
