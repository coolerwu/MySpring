package vip.wulang.spring.annotation;

import java.lang.annotation.*;

/**
 * @author CoolerWu on 2018/12/12.
 * @version 1.0
 */
@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {

    String value() default "";

}
