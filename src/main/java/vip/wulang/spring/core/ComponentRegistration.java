package vip.wulang.spring.core;

import vip.wulang.spring.core.annotation.ComponentScan;
import vip.wulang.spring.core.annotation.Component;
import vip.wulang.spring.core.annotation.Configuration;
import vip.wulang.spring.core.annotation.Import;
import vip.wulang.spring.core.impl.BeanApplicationContext;
import vip.wulang.spring.core.impl.PropertyAutowiredClass;
import vip.wulang.spring.core.scanner.UrlScanner;
import vip.wulang.spring.exception.*;
import vip.wulang.spring.util.StringUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * It just is as registration that component is registered.
 *
 * @author CoolerWu on 2019/3/3.
 * @version 1.0
 */
public class ComponentRegistration {
    private BeanApplicationContext beanApplicationContext;
    private volatile boolean exception;
    private List<String> packages = new LinkedList<>();

    ComponentRegistration(BeanApplicationContext beanApplicationContext) {
        if (beanApplicationContext == null) {
            exception = true;
            throw new BeanApplicationContextNullException();
        }

        this.beanApplicationContext = beanApplicationContext;
        this.exception = false;
    }

    void startComponentRegister(Class<?> mainConfigClass)
            throws ConstructorOneMoreException {
        if (exception) {
            return;
        }

        // scan info of main config class.
        operateConfiguration(mainConfigClass);

        // register component.
        List<String> result = scanAll();

        //
        doExecuteBeanApplicationContextMethod(result, mainConfigClass);
    }

    private void operateConfiguration(Class<?> configClass) {
        if (exception) {
            return;
        }
        Configuration configuration = configClass.getAnnotation(Configuration.class);
        if (configuration == null) {
            exception = true;
            return;
        }
        Import importAnnotation = configClass.getAnnotation(Import.class);
        if (importAnnotation != null) {
            Class[] classes = importAnnotation.value();

            for (Class cls : classes) {
                operateConfiguration(cls);
            }
        }
        ComponentScan componentScan = configClass.getAnnotation(ComponentScan.class);
        if (componentScan == null) {
            return;
        }
        String value = componentScan.value();

        if (StringUtils.isEmpty(value)) {
            packages.add(configClass.getPackage().getName());
        } else {
            packages.add(value);
        }
    }

    private List<String> scanAll() {
        if (exception) {
            return null;
        }
        List<String> result = new LinkedList<>();
        try {
            for (String packageName : packages) {
                result.addAll(new UrlScanner(packageName).startScan());
            }
        } catch (Exception e) {
            exception = true;
            throw new ScanAllException();
        }
        return result;
    }

    private void doExecuteBeanApplicationContextMethod(List<String> result, Class<?> mainConfigClass)
            throws ConstructorOneMoreException {
        if (exception) {
            return;
        }
        List<Class<?>> classes = new LinkedList<>();
        classes.add(mainConfigClass);
        try {
            for (String clsStr : result) {
                Class<?> forName =
                        Class.forName(clsStr, Boolean.TRUE, Thread.currentThread().getContextClassLoader());
                checkComponentAnnotation(forName, classes);
            }
        } catch (Exception e) {
            e.printStackTrace();
            exception = true;
            return;
        }
        AutowiredClass autowiredClass = new PropertyAutowiredClass(beanApplicationContext, classes);
        autowiredClass.start();
    }

    private void checkComponentAnnotation(Class<?> cls, List<Class<?>> classes) {
        if (exception) {
            return;
        }
        Component componentAnnotation = cls.getAnnotation(Component.class);
        if (componentAnnotation != null) {
            classes.add(cls);
        }
    }

    void over() throws DeadStartException {
        if (exception) {
            throw new DeadStartException();
        }
    }
}
