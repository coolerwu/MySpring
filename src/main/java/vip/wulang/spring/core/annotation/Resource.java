package vip.wulang.spring.core.annotation;

import java.lang.annotation.*;

/**
 * @author coolerwu
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.FIELD})
public @interface Resource {
    Class<?> value() default Object.class;

    String name() default "";
}
