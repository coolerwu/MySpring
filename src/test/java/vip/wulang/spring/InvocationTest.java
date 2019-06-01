package vip.wulang.spring;

import org.junit.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author coolerwu
 * @version 1.0
 */
public class InvocationTest {
    static class MyInvocation implements InvocationHandler {
        private Object target;

        public MyInvocation(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if ("toString".equals(method.getName())) {
                return executeToString();
            }

            if ("hello".equals(method.getName())) {
                System.out.println("I am proxy.");
                return method.invoke(target, args);
            }

            return null;
        }

        private String executeToString() {
            Class<?> targetClass = target.getClass();
            Field[] declaredFields = targetClass.getDeclaredFields();
            StringBuilder sb = new StringBuilder();
            sb.append("[");

            for (Field declaredField : declaredFields) {
                if (declaredField.isAnnotationPresent(Secret.class)) {
                    continue;
                }

                sb.append(declaredField.getName());
                sb.append(" ");
            }

            sb.append("]");

            return sb.toString();
        }
    }

    static class DynamicClass {
        private MyInterface proxy;

        public DynamicClass(Object proxy) {
            this.proxy = init(proxy);
        }

        private MyInterface init(Object proxy) {
            return (MyInterface) Proxy.newProxyInstance(proxy.getClass().getClassLoader(),
                    proxy.getClass().getInterfaces(), new MyInvocation(proxy));
        }

        public MyInterface getProxy() {
            return proxy;
        }
    }

    interface MyInterface {
        void hello();
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Secret {
    }

    static class MyImpl implements MyInterface {
        private int A;

        @Secret
        private int B;

        @Override
        public void hello() {
            System.out.println("hello");
        }
    }

    @Test
    public void test01() {
        DynamicClass dynamicClass = new DynamicClass(new MyImpl());
        MyInterface proxy = dynamicClass.getProxy();
        proxy.hello();
        System.out.println(proxy);
    }
}
