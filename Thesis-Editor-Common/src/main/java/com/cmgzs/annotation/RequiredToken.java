package com.cmgzs.annotation;

import java.lang.annotation.*;

/**
 * @author huangzhenyu
 * @date 2022/9/21
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredToken {

    boolean value() default true;

}
