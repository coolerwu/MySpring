//package vip.wulang.spring.core.impl;
//
//import vip.wulang.spring.core.AutowiredClass;
//import vip.wulang.spring.core.ServiceProperty;
//import vip.wulang.spring.exception.BeanApplicationContextNullException;
//import vip.wulang.spring.exception.ConstructorOneMoreException;
//
//import java.lang.reflect.Constructor;
//import java.util.Objects;
//
///**
// * Abstract autowired class.
// *
// * @author coolerwu
// * @version 1.0
// */
//public class BaseAutowiredClass implements AutowiredClass {
//    private BeanApplicationContext beanApplicationContext;
//
//    public BaseAutowiredClass(BeanApplicationContext beanApplicationContext) {
//        if (beanApplicationContext == null) {
//            throw new BeanApplicationContextNullException();
//        }
//
//        this.beanApplicationContext = beanApplicationContext;
//    }
//
//    @Override
//    public void autowired(Class<?> cls) {
//        if (Objects.isNull(cls)) {
//            throw new NullPointerException();
//        }
//
//        if (Objects.nonNull(originStorage.get(cls))) {
//            return;
//        }
//
//        String ownerName = cls.getSimpleName();
//        Class[] interfaces = cls.getInterfaces();
//        Class superclass = cls.getSuperclass();
//        Constructor[] constructors = cls.getConstructors();
//
//        if (constructors.length > 1) {
//            throw new ConstructorOneMoreException("one more constructors.");
//        }
//
//        Class[] parameterTypes = constructors[0].getParameterTypes();
//        Object owner;
//
//        if (parameterTypes.length == 0) {
//            owner = cls.newInstance();
//        } else {
//            for (Class cl1 : parameterTypes) {
//                if (Objects.nonNull(originStorage.get(cl1))) {
//                    continue;
//                }
//
//                if (!readyLoadClass.contains(cl1)) {
//                    continue;
//                }
//
//                if (checkWhetherDependency(cls, cl1)) {
//                    stopAll();
//                }
//
//                dependencyClasses.add(new BeanApplicationContext.DependencyClass(cls, cl1));
//
//                try {
//                    parseClassToServiceProperty(cl1);
//                } catch (Exception e) {
//                    exception = true;
//                    e.printStackTrace();
//                }
//            }
//
//            Object[] parameters = new Object[parameterTypes.length];
//            int index = 0;
//
//            for (Class cl1 : parameterTypes) {
//                ServiceProperty serviceProperty = originStorage.get(cl1);
//
//                if (serviceProperty == null) {
//                    throw new NullPointerException();
//                }
//
//                parameters[index] = serviceProperty.getOwner();
//                index++;
//            }
//
//            owner = constructors[0].newInstance(parameters);
//        }
//
//        ServiceProperty serviceProperty = new ServiceProperty(
//                owner, cls, interfaces, superclass, ownerName
//        );
//        originStorage.put(cls, serviceProperty);
//        nameStorage.put(ownerName, cls);
//    }
//}
