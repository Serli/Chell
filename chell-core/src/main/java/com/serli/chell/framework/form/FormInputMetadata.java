package com.serli.chell.framework.form;

import com.serli.chell.framework.constant.FormType;
import com.serli.chell.framework.form.FormFieldConfiguration.InputConfiguration;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
@Target(TYPE)
@Retention(RUNTIME)
@Documented
@Inherited
public @interface FormInputMetadata {
    FormType type();
    Class<? extends InputConfiguration> configuration() default InputConfiguration.class;
}
