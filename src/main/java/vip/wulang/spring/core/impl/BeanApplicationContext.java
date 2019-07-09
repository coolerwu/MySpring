package vip.wulang.spring.core.impl;

import vip.wulang.spring.core.ApplicationContext;
import vip.wulang.spring.core.ServiceProperty;
import vip.wulang.spring.exception.ConstructorOneMoreException;
import vip.wulang.spring.exception.NewInstanceFailedException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Java core class.
 *
 * @author CoolerWu on 2019/3/2.
 * @version 1.0
 */
public class BeanApplicationContext implements ApplicationContext {
    private static final int ZERO = 0;

    public BeanApplicationContext() {
    }

    private ConcurrentMap<Class, ServiceProperty> originStorage = new ConcurrentHashMap<>();
    private ConcurrentMap<String, Class> nameStorage = new ConcurrentHashMap<>();

    ConcurrentMap<Class, ServiceProperty> getOriginStorage() {
        return originStorage;
    }

    ConcurrentMap<String, Class> getNameStorage() {
        return nameStorage;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> cls) {
        return (T) originStorage.get(cls).getOwner();
    }

    public Object getBean(String name) {
        return originStorage.get(nameStorage.get(name)).getOwner();
    }

    public Class getSuperClassFromBean(String name) {
        return getSuperClassFromBean(nameStorage.get(name));
    }

    public Class getSuperClassFromBean(Class cls) {
        return originStorage.get(cls).getSuperClass();
    }

    public Class[] getInterfacesFromBean(String name) {
        return getInterfacesFromBean(nameStorage.get(name));
    }

    public Class[] getInterfacesFromBean(Class cls) {
        return originStorage.get(cls).getInterfaces();
    }

    public void stopAll() {
        System.out.println("=================================");
        System.out.println("=                               =");
        System.out.println("=                               =");
        System.out.println("=             over              =");
        System.out.println("=                               =");
        System.out.println("=                               =");
        System.out.println("=================================");
        stop();
    }

    private void stop() {
        System.exit(ZERO);
    }
}
