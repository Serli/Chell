package com.serli.chell.framework.annotation;

import com.serli.chell.framework.constant.Constant;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
@Target(METHOD)
@Retention(RUNTIME)
@Documented
@Inherited
public @interface OnResource {
    // TODO : Ameliorer la gestion des ressources
    String name() default Constant.WILDCARD;
    String value() default Constant.WILDCARD;
}
