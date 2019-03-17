package vip.wulang.spring.core;

import vip.wulang.spring.annotation.ComponenetScan;
import vip.wulang.spring.annotation.Component;
import vip.wulang.spring.annotation.Configuration;
import vip.wulang.spring.annotation.Import;
import vip.wulang.spring.core.scanner.UrlScanner;
import vip.wulang.spring.exception.ConstructorOneMoreException;
import vip.wulang.spring.exception.DeadStartException;
import vip.wulang.spring.exception.NewInstanceFailedException;
import vip.wulang.spring.util.StringUtils;

import java.util.LinkedList;
import java.util.List;

/**
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
            throw new NullPointerException();
        }

        this.beanApplicationContext = beanApplicationContext;
        this.exception = false;
    }

    void startComponentRegister(Class<?> mainConfigClass)
            throws ConstructorOneMoreException, NewInstanceFailedException {
        if (exception) {
            return;
        }

        operateConfiguration(mainConfigClass);
        List<String> result = scanAll();
        doExecuteBeanApplicationContextMethod(result);
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

        ComponenetScan componenetScan = configClass.getAnnotation(ComponenetScan.class);

        if (componenetScan == null) {
            return;
        }

        String value = componenetScan.value();

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
            e.printStackTrace();
        }

        return result;
    }

    private void doExecuteBeanApplicationContextMethod(List<String> result)
            throws ConstructorOneMoreException, NewInstanceFailedException {
        if (exception) {
            return;
        }

        List<Class> classes = new LinkedList<>();

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

        beanApplicationContext.addClassIntoStorage(classes);
    }

    private void checkComponentAnnotation(Class<?> cls, List<Class> classes) {
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
