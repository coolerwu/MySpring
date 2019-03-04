package vip.wulang.spring.core;

import vip.wulang.spring.exception.ConstructorOneMoreException;
import vip.wulang.spring.exception.DeadStartException;
import vip.wulang.spring.exception.NewInstanceFailedException;

import java.io.IOException;

/**
 * Context starter.
 *
 * @author CoolerWu on 2019/3/3.
 * @version 1.0
 */
public class ApplicationContextStarter implements ApplicationContext {
    private BeanApplicationContext beanApplicationContext;
    private ComponentRegistration componentRegistration;
    private Class<?> mainConfigClass;

    public ApplicationContextStarter(Class<?> mainConfigClass) {
        if (mainConfigClass == null) {
            throw new NullPointerException();
        }

        this.beanApplicationContext = new BeanApplicationContext();
        this.componentRegistration = new ComponentRegistration(beanApplicationContext);
        this.mainConfigClass = mainConfigClass;
    }

    public ApplicationContextStarter start()
            throws ConstructorOneMoreException, NewInstanceFailedException, IOException {
        componentRegistration.startComponentRegister(mainConfigClass);
        return this;
    }

    public void over() {
        try {
            componentRegistration.over();
        } catch (DeadStartException e) {
            e.printStackTrace();
            beanApplicationContext.stop();
        }
    }

    @Override
    public <T> T getBean(Class<T> cls) {
        return beanApplicationContext.getBean(cls);
    }

    @Override
    public Object getBean(String name) {
        return beanApplicationContext.getBean(name);
    }

    @Override
    public Class getSuperClassFromBean(String name) {
        return beanApplicationContext.getSuperClassFromBean(name);
    }

    @Override
    public Class getSuperClassFromBean(Class cls) {
        return beanApplicationContext.getSuperClassFromBean(cls);
    }

    @Override
    public Class[] getInterfacesFromBean(String name) {
        return beanApplicationContext.getInterfacesFromBean(name);
    }

    @Override
    public Class[] getInterfacesFromBean(Class cls) {
        return beanApplicationContext.getInterfacesFromBean(cls);
    }
}
