package com.serli.chell.framework.form.annotation;

import com.serli.chell.framework.constant.Constant;
import com.serli.chell.framework.constant.FormType;
import com.serli.chell.framework.form.converter.FieldConverter;
import com.serli.chell.framework.form.converter.IdentityFieldConverter;
import com.serli.chell.framework.loader.DataLoader;
import com.serli.chell.framework.loader.DataLoader.EmptyDataLoader;
import com.serli.chell.framework.reflect.AnnotationMetaData;
import com.serli.chell.framework.reflect.AnnotationMetaData.Type;
import com.serli.chell.framework.form.FormInputMetadata;
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
@FormInputMetadata(type = FormType.CHECKBOX)
public @interface HtmlInputCheckbox {

    @AnnotationMetaData(Type.MESSAGE_KEY)
    String key() default Constant.EMPTY;

    @AnnotationMetaData(Type.MESSAGE_TEXT)
    String label() default Constant.EMPTY;

    @AnnotationMetaData(Type.MESSAGE_BUNDLE)
    String bundle() default Constant.EMPTY;

    @AnnotationMetaData(Type.PREFERENCE_NAME)
    String prefName() default Constant.EMPTY;

    @AnnotationMetaData(Type.FIELD_ORDER)
    int order() default Constant.DEFAULT_ORDER;

    @AnnotationMetaData(Type.HTML_CSS_CLASS)
    String cssClass() default Constant.EMPTY;

    @AnnotationMetaData(Type.HTML_ID)
    String id() default Constant.EMPTY;

    @AnnotationMetaData(Type.HTML_COLS)
    int cols() default 1;

    @AnnotationMetaData(Type.FIELD_CONVERTER)
    Class<? extends FieldConverter> converter() default IdentityFieldConverter.class;

    @AnnotationMetaData(Type.FIELD_DATA_LOADER)
    Class<? extends DataLoader> loader() default EmptyDataLoader.class;
}
