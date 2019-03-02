package vip.wulang.spring.core;

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
 * @author CoolerWu on 2019/3/2.
 * @version 1.0
 */
public class JavaUsb {
    private static final int ZERO = 0;
    private static JavaUsb instance;

    public static JavaUsb getInstance() {
        if (instance == null) {
            synchronized (JavaUsb.class) {
                if (instance == null) {
                    instance = new JavaUsb();
                }
            }
        }

        return instance;
    }

    private JavaUsb() {
    }

    private ConcurrentMap<Class, ServiceProperty> originStorage = new ConcurrentHashMap<>();
    private ConcurrentMap<String, Class> nameStorage = new ConcurrentHashMap<>();
    private volatile boolean exception = false;
    private List<DependencyClass> dependencyClasses = new LinkedList<>();

    public void addClassIntoStorage(Class... classes)
            throws ConstructorOneMoreException, NewInstanceFailedException {
        try {
            for (Class cls : classes) {
                parseClassToServiceProperty(cls);
            }
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new NewInstanceFailedException();
        }

        if (exception) {
            stopAll();
        }
    }

    private void parseClassToServiceProperty(Class cls)
            throws ConstructorOneMoreException, IllegalAccessException,
            InstantiationException, InvocationTargetException {
        if (Objects.isNull(cls)) {
            throw new NullPointerException();
        }

        if (Objects.nonNull(originStorage.get(cls))) {
            return;
        }

        String ownerName = cls.getSimpleName();
        Class[] interfaces = cls.getInterfaces();
        Class superclass = cls.getSuperclass();
        Constructor[] constructors = cls.getConstructors();

        if (constructors.length > 1) {
            throw new ConstructorOneMoreException("one more constructors.");
        }

        Class[] parameterTypes = constructors[0].getParameterTypes();
        Object owner;

        if (parameterTypes.length == 0) {
            owner = cls.newInstance();
        } else {
            for (Class cl1 : parameterTypes) {
                if (Objects.nonNull(originStorage.get(cl1))) {
                    continue;
                }

                if (checkWhetherDependency(cls, cl1)) {
                    stopAll();
                }

                dependencyClasses.add(new DependencyClass(cls, cl1));

                try {
                    parseClassToServiceProperty(cl1);
                } catch (Exception e) {
                    exception = true;
                    e.printStackTrace();
                }
            }

            Object[] parameters = new Object[parameterTypes.length];
            int index = 0;

            for (Class cl1 : parameterTypes) {
                parameters[index] = originStorage.get(cl1).getOwner();
                index++;
            }

            owner = constructors[0].newInstance(parameters);
        }

        ServiceProperty serviceProperty = new ServiceProperty(
                owner, cls, interfaces, superclass, ownerName
        );
        originStorage.put(cls, serviceProperty);
        nameStorage.put(ownerName, cls);
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> cls) {
        return (T) originStorage.get(cls).getOwner();
    }

    public Object getBean(String name) {
        return originStorage.get(nameStorage.get(name)).getOwner();
    }

    private static class DependencyClass {
        private Class preClass;
        private Class nextClass;

        DependencyClass(Class preClass, Class nextClass) {
            this.preClass = preClass;
            this.nextClass = nextClass;
        }
    }

    private boolean checkWhetherDependency(Class preClass, Class nextClass) {
        for (DependencyClass dependencyClass : dependencyClasses) {
            if (dependencyClass.nextClass == preClass &&
                    dependencyClass.preClass == nextClass) {
                return true;
            }
        }

        return false;
    }

    private void stopAll() {
        System.out.println("=================================");
        System.out.println("=                               =");
        System.out.println("=                               =");
        System.out.println("=             over              =");
        System.out.println("=                               =");
        System.out.println("=                               =");
        System.out.println("=================================");
        System.exit(ZERO);
    }
}
