package com.serli.chell.framework.annotation;

import com.serli.chell.framework.constant.Constant;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
@Target(PARAMETER)
@Retention(RUNTIME)
@Documented
@Inherited
// TODO : Ajouter la fonction de @PreferenceAttribute
public @interface PreferenceAttribute {
    String value();
    String defaultValue() default Constant.EMPTY;
    String[] defaultValues() default {};
}
