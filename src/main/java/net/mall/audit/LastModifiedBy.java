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
 * Audit - 最后修改者注解
 * 
 * @author huanghy
 * @version 6.1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD, ElementType.METHOD })
public @interface LastModifiedBy {

}