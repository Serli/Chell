package com.serli.chell.framework.validation.annotation;

import com.serli.chell.framework.constant.Constant;
import com.serli.chell.framework.form.FormFieldConfiguration.LengthConfiguration;
import com.serli.chell.framework.form.FormFieldMetadata;
import com.serli.chell.framework.reflect.AnnotationMetaData;
import com.serli.chell.framework.reflect.AnnotationMetaData.Type;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
@Target(FIELD)
@Retention(RUNTIME)
@Documented
@Inherited
@FormFieldMetadata(configuration = LengthConfiguration.class)
public @interface Length {
    
    @AnnotationMetaData(Type.MESSAGE_KEY)
    String key() default Constant.EMPTY;

    @AnnotationMetaData(Type.MESSAGE_TEXT)
    String message() default Constant.EMPTY;

    @AnnotationMetaData(Type.MESSAGE_BUNDLE)
    String bundle() default Constant.EMPTY;
    
    int value() default Constant.UNSPECIFIED;

    int min() default Constant.UNSPECIFIED;

    int max() default Constant.UNSPECIFIED;
}
