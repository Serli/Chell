package com.serli.chell.framework.form;

import com.serli.chell.framework.form.FormFieldConfiguration.NoConfiguration;
import com.serli.chell.framework.form.converter.FieldConverter;
import com.serli.chell.framework.form.converter.IdentityFieldConverter;
import com.serli.chell.framework.validation.Constraint;
import com.serli.chell.framework.validation.NoConstraint;
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
public @interface FormFieldMetadata {
    Class<? extends Constraint<?>> constraint() default NoConstraint.class;
    Class<? extends FieldConverter<?, ?>> converter() default IdentityFieldConverter.class;
    Class<? extends FormFieldConfiguration<?>> configuration() default NoConfiguration.class;
}
