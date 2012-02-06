package com.serli.chell.framework.annotation;

import com.serli.chell.framework.exception.handler.ExceptionHandler;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
@Target(ANNOTATION_TYPE)
@Retention(RUNTIME)
@Documented
@Inherited
public @interface Catcher {
    Class<? extends Throwable> exception();
    Class<? extends ExceptionHandler> handler();
}
