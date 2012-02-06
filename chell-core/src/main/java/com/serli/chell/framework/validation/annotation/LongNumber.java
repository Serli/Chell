package com.serli.chell.framework.validation.annotation;

import com.serli.chell.framework.constant.Constant;
import com.serli.chell.framework.form.converter.LongFieldConverter;
import com.serli.chell.framework.reflect.AnnotationMetaData;
import com.serli.chell.framework.reflect.AnnotationMetaData.Type;
import com.serli.chell.framework.form.FormFieldMetadata;
import com.serli.chell.framework.validation.check.LongNumberCheck;
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
@FormFieldMetadata(constraint = LongNumberCheck.class, converter = LongFieldConverter.class)
public @interface LongNumber {

    @AnnotationMetaData(Type.MESSAGE_KEY)
    String key() default Constant.EMPTY;

    @AnnotationMetaData(Type.MESSAGE_TEXT)
    String message() default Constant.EMPTY;

    @AnnotationMetaData(Type.MESSAGE_BUNDLE)
    String bundle() default Constant.EMPTY;
    
    long min() default Long.MIN_VALUE;

    long max() default Long.MAX_VALUE;
}
