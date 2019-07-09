package vip.wulang.spring;

import org.junit.Test;
import vip.wulang.spring.core.ApplicationContextStarter;
import vip.wulang.spring.exception.NewInstanceFailedException;

import java.io.IOException;

/**
 * @author coolerwu
 * @version 1.0
 */
public class SpringTest {
    @Test
    public void test01() throws IOException, NewInstanceFailedException {
        ApplicationContextStarter starter = new ApplicationContextStarter(MainConfig.class);
        starter.start();
        MainConfig bean = starter.getBean(MainConfig.class);
        System.out.println(bean);
    }
}
