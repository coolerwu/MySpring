package vip.wulang.spring.core.impl;

import vip.wulang.spring.core.AutowiredClass;
import vip.wulang.spring.core.ServiceProperty;
import vip.wulang.spring.core.annotation.Component;
import vip.wulang.spring.core.annotation.Configuration;
import vip.wulang.spring.core.annotation.Resource;
import vip.wulang.spring.exception.BeanApplicationContextNullException;
import vip.wulang.spring.exception.ConstructParameterNotNullException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author coolerwu
 * @version 1.0
 */
public class PropertyAutowiredClass implements AutowiredClass {
    private BeanApplicationContext beanApplicationContext;
    private List<Class<?>> classes;
    private Map<Class<?>, Object> storage;

    public PropertyAutowiredClass(BeanApplicationContext beanApplicationContext, List<Class<?>> classes) {
        if (beanApplicationContext == null) {
            throw new BeanApplicationContextNullException();
        }
        if (classes == null) {
            throw new NullPointerException();
        }
        this.classes = classes;
        this.beanApplicationContext = beanApplicationContext;
    }

    @Override
    public void start() {
        checkStorageIsNull();
        autowired();
        clean();
    }

    private void checkStorageIsNull() {
        if (storage == null) {
            createInstance();
        }
    }

    private void createInstance() {
        storage = new HashMap<>();
        try {
            for (Class<?> cls : classes) {
                if (storage.containsKey(cls)) {
                    continue;
                }
                Constructor<?>[] constructors = cls.getConstructors();
                boolean throwException = true;
                for (Constructor<?> constructor : constructors) {
                    if (constructor.getParameterCount() == 0) {
                        throwException = false;
                    }
                }
                if (throwException) {
                    throw new ConstructParameterNotNullException();
                }
                Object instance = cls.newInstance();
                storage.put(cls, instance);
            }
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void autowired() {
        for (Class<?> cls : classes) {
            Object instance = storage.get(cls);
            if (instance == null) {
                continue;
            }
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                setPropertyOrNull(field, instance);
            }
            String ownerName = getComponentName(cls);
            ServiceProperty serviceProperty = new ServiceProperty(
                    instance, cls, cls.getInterfaces(), cls.getSuperclass(), ownerName
            );
            beanApplicationContext.getOriginStorage().put(cls, serviceProperty);
            beanApplicationContext.getNameStorage().put(ownerName, cls);
        }
    }

    private void setPropertyOrNull(Field field, Object instance) {
        field.setAccessible(true);
        Class<Resource> resourceClass = Resource.class;
        Resource resource = field.getAnnotation(resourceClass);
        if (resource != null) {
            if (checkValue(resource) && checkName(resource)) {
                Class<?> cls = field.getType();
                Object value = storage.get(cls);
                try {
                    field.set(instance, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean checkValue(Resource resource) {
        return resource.value() == Object.class;
    }

    private boolean checkName(Resource resource) {
        return "".equals(resource.name());
    }

    private String getComponentName(Class<?> cls) {
        Annotation[] annotations = cls.getAnnotations();
        Component component = null;
        for (Annotation annotation : annotations) {
            if (annotation instanceof Component) {
                component = (Component) annotation;
            } else if (annotation instanceof Configuration) {
                component = annotation.annotationType().getAnnotation(Component.class);
            }
        }
        if (component == null) {
            throw new NullPointerException();
        }
        if ("".equals(component.value())) {
            return cls.getSimpleName();
        }
        return component.value();
    }

    private void clean() {
        storage = null;
        classes = null;
        beanApplicationContext = null;
        System.gc();
    }
}
