package vip.wulang.spring.core;

/**
 * @author CoolerWu on 2019/3/3.
 * @version 1.0
 */
public interface ApplicationContext {
    <T> T getBean(Class<T> cls);

    Object getBean(String name);

    Class getSuperClassFromBean(String name);

    Class getSuperClassFromBean(Class cls);

    Class[] getInterfacesFromBean(String name);

    Class[] getInterfacesFromBean(Class cls);
}
