package vip.wulang.spring.spring;

import vip.wulang.spring.annotation.Component;

/**
 * @author CoolerWu on 2019/3/3.
 * @version 1.0
 */
@Component
public class B {
    private A a;

    public B(A a) {
        this.a = a;
    }
}
