package vip.wulang.spring.core.annotation;

import java.lang.annotation.*;

/**
 * @author CoolerWu on 2019/3/3.
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Component {
}
