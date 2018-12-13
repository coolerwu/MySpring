package vip.wulang.spring.core;

import java.util.ArrayList;
import java.util.List;

/**
 * The class is about for bean definition.
 *
 * @author CoolerWu on 2018/12/11.
 * @version 1.0
 */
public class BeanDefinition {
    private String className;
    private String classType;
    private List<Class> classSuper = new ArrayList<>();
    private List<Class> classInterfaces = new ArrayList<>();
    private boolean singleton;
    private List<Object> instances = new ArrayList<>();

    public BeanDefinition() {}

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public List<Class> getClassSuper() {
        return classSuper;
    }

    public List<Class> getClassInterfaces() {
        return classInterfaces;
    }

    public boolean isSingleton() {
        return singleton;
    }

    public void setSingleton(boolean singleton) {
        this.singleton = singleton;
    }

    public List<Object> getInstances() {
        return instances;
    }
}
