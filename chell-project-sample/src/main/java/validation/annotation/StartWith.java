package validation.annotation;

import com.serli.chell.framework.constant.Constant;
import com.serli.chell.framework.reflect.AnnotationMetaData;
import com.serli.chell.framework.reflect.AnnotationMetaData.Type;
import com.serli.chell.framework.form.FormFieldMetadata;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import validation.check.StartWithCheck;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
@Target(FIELD)
@Retention(RUNTIME)
@Documented
@Inherited
@FormFieldMetadata(constraint = StartWithCheck.class)
public @interface StartWith {
    
    @AnnotationMetaData(Type.MESSAGE_KEY)
    String key() default "validation.start.with";

    @AnnotationMetaData(Type.MESSAGE_TEXT)
    String message() default Constant.EMPTY;

    @AnnotationMetaData(Type.MESSAGE_BUNDLE)
    String bundle() default Constant.EMPTY;

    String value();
}
