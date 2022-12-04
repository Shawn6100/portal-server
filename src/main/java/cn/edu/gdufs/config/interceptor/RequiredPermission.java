package cn.edu.gdufs.config.interceptor;

import java.lang.annotation.*;

/**
 * Description: 自定义权限校验注解
 * Author: 严仕鹏
 * Date: 2022/12/2
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RequiredPermission {
    int[] value();
}
