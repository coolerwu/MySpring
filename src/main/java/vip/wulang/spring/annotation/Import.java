package vip.wulang.spring.annotation;

import java.lang.annotation.*;

/**
 * @author CoolerWu on 2018/12/14.
 * @version 1.0
 */
@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Import {

    String[] value() default "";
}
