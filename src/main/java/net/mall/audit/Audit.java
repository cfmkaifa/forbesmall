/*
 *
 *
 *
 *
 */
package net.mall.audit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Audit - 审计注解
 *
 * @author huanghy
 * @version 6.1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface Audit {

    /**
     * 动作
     */
    String action();

}