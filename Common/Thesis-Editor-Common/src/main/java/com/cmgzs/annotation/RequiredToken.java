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

    /**
     * 是否需要解析token对应的用户信息
     */
    boolean value() default true;

    /**
     * 用户需要拥有的角色
     */
    String role() default "";

    /**
     * 用户必须拥有的权限
     */
    String permissions() default "";


}
