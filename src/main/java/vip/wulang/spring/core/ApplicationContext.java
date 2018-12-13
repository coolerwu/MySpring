package vip.wulang.spring.core;

import vip.wulang.spring.annotation.Component;
import vip.wulang.spring.scanner.PackageScanner;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The context is used for instance which contains {@link Component}
 *
 * @author CoolerWu on 2018/12/12.
 * @version 1.0
 */
public class ApplicationContext implements BeanFactory {
    private static final String EMPTY_STRING = "";

    private Map<String, BeanDefinition> containerByName = new ConcurrentHashMap<>();
    private String basePackage;
    private boolean initialization = false;

    public ApplicationContext(String basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public void createAllBean() {
        List<String> packageScan = new PackageScanner(basePackage).startPackageScan();

        try {
            for (String beanStr : packageScan) {
                Class<?> name = Class.forName(beanStr);
                Component componentAnnotation = name.getAnnotation(Component.class);

                if (Objects.nonNull(componentAnnotation)) {
                    String nick_name;
                    if (EMPTY_STRING.equals(componentAnnotation.value())) {
                        nick_name = name.getSimpleName().toLowerCase();
                    } else {
                        nick_name = componentAnnotation.value();
                    }

                    Object instance = name.newInstance();
                    BeanDefinition beanDefinition = new BeanDefinition();

                    beanDefinition.setClassName(name.getSimpleName());
                    beanDefinition.setClassType(name.getTypeName());
                    beanDefinition.setSingleton(true);
                    beanDefinition.getInstances().add(instance);

                    if (Objects.nonNull(name.getSuperclass())) {
                        beanDefinition.getClassSuper().add(name.getSuperclass());
                    }

                    if (Objects.nonNull(name.getInterfaces())) {
                        beanDefinition.getClassInterfaces().add(name.getSuperclass());
                    }

                    containerByName.put(nick_name, beanDefinition);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        initialization = true;
    }

    @Override
    public Object getBeanByName(String name) {
        if (!initialization) {
            createAllBean();
        }

        return Objects.isNull(name) ? null : containerByName.get(name.toLowerCase()).getInstances().get(0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getBeanByClass(Class<T> type) {
        if (!initialization) {
            createAllBean();
        }

        if (Objects.isNull(type)) {
            return null;
        }

        BeanDefinition beanDefinition;
        if (Objects.nonNull(beanDefinition = containerByName.get(type.getSimpleName().toLowerCase()))) {
            return beanDefinition.getInstances().get(0).getClass().equals(type) ?
                    (T) beanDefinition.getInstances().get(0) : null;
        } else {
            return null;
        }
    }
}
