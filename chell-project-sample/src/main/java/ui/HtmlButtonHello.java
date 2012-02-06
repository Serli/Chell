package ui;

import com.serli.chell.framework.constant.Constant;
import com.serli.chell.framework.constant.FormType;
import com.serli.chell.framework.form.FormButton.ButtonType;
import com.serli.chell.framework.reflect.AnnotationMetaData;
import com.serli.chell.framework.reflect.AnnotationMetaData.Type;
import com.serli.chell.framework.reflect.AnnotationConstants;
import com.serli.chell.framework.reflect.AnnotationDefaultValues;
import com.serli.chell.framework.reflect.AnnotationDefaultValues.DefaultValue;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import ui.HtmlButtonHello.HelloButtonConstants;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
@Target(TYPE)
@Retention(RUNTIME)
@Documented
@Inherited
@AnnotationConstants(HelloButtonConstants.class)
public @interface HtmlButtonHello {

    @AnnotationMetaData(Type.MESSAGE_KEY)
    String key() default "action.hello";

    @AnnotationMetaData(Type.MESSAGE_TEXT)
    String label() default Constant.EMPTY;

    @AnnotationMetaData(Type.MESSAGE_BUNDLE)
    String bundle() default Constant.EMPTY;

    @AnnotationMetaData(Type.HTML_CSS_CLASS)
    String cssClass() default Constant.EMPTY;

    @AnnotationMetaData(Type.HTML_ID)
    String id() default Constant.EMPTY;

    @AnnotationMetaData(Type.BUTTON_ORDER)
    int order() default Constant.DEFAULT_ORDER;

    @AnnotationMetaData(Type.BUTTON_NAME)
    String name() default "custom";

    public static class HelloButtonConstants implements AnnotationDefaultValues {
        public DefaultValue[] getDefaultValues() {
            return new DefaultValue[] {
                new DefaultValue(Type.FORM_TYPE, FormType.BUTTON),
                new DefaultValue(Type.BUTTON_TYPE, ButtonType.BUTTON),
                new DefaultValue(Type.BUTTON_ON_CLICK, "window.alert('Hello')")
            };
        }
    }
}
