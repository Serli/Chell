package com.serli.chell.framework.form.annotation;

import com.serli.chell.framework.PortletHelper;
import com.serli.chell.framework.constant.Constant;
import com.serli.chell.framework.constant.FormType;
import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.form.FormButton;
import com.serli.chell.framework.form.annotation.HtmlButtonAction.ActionButtonConstants;
import com.serli.chell.framework.form.FormButton.OnClickBuilder;
import com.serli.chell.framework.form.FormButton.ButtonType;
import com.serli.chell.framework.message.MessageKey;
import com.serli.chell.framework.reflect.AnnotationMetaData;
import com.serli.chell.framework.reflect.AnnotationMetaData.Type;
import com.serli.chell.framework.reflect.AnnotationConstants;
import com.serli.chell.framework.reflect.AnnotationDefaultValues;
import com.serli.chell.framework.reflect.AnnotationDefaultValues.DefaultValue;
import com.serli.chell.framework.resource.Resource;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
@Target(TYPE)
@Retention(RUNTIME)
@Documented
@Inherited
@AnnotationConstants(ActionButtonConstants.class)
@Resource(path = "/resources/button/button.js")
public @interface HtmlButtonAction {

    @AnnotationMetaData(Type.MESSAGE_KEY)
    String key() default MessageKey.ACTION_BUTTON;

    @AnnotationMetaData(Type.MESSAGE_TEXT)
    String label() default Constant.EMPTY;

    @AnnotationMetaData(Type.MESSAGE_BUNDLE)
    String bundle() default Constant.EMPTY;

    @AnnotationMetaData(Type.HTML_CSS_CLASS)
    String cssClass() default Constant.EMPTY;

    @AnnotationMetaData(Type.HTML_ID)
    String id() default Constant.EMPTY;

    @AnnotationMetaData(Type.BUTTON_NAME)
    String name() default "action";

    @AnnotationMetaData(Type.BUTTON_ORDER)
    int order() default Constant.DEFAULT_ORDER;

    @AnnotationMetaData(Type.BUTTON_PORTLET_ACTION)
    String action();

    public static class ActionButtonConstants implements AnnotationDefaultValues {
        public DefaultValue[] getDefaultValues() {
            return new DefaultValue[] {
                new DefaultValue(Type.FORM_TYPE, FormType.BUTTON),
                new DefaultValue(Type.BUTTON_TYPE, ButtonType.BUTTON),
                new DefaultValue(Type.BUTTON_ON_CLICK_BUILDER, ActionButtonOnClickBuilder.class)
            };
        }
    }

    public static class ActionButtonOnClickBuilder implements OnClickBuilder {
        public String getOnClickLink(Form form, FormButton button) {
            try {
                String action = (String) button.getDefinition(Type.BUTTON_PORTLET_ACTION);
                PortletURL portletUrl = PortletHelper.getActionURL(PortletHelper.getResponse(), action);
                portletUrl.setParameter(Constant.PARAMETER_BUTTON_CLICKED, button.getName());
                portletUrl.setParameter(Constant.PARAMETER_FORM_CLASS, form.getClass().getName());
                StringBuilder b = new StringBuilder();
                b.append("chell.button.submitAction(this, '");
                b.append(portletUrl.toString()).append("')");
                return b.toString();
            } catch (PortletException ex) {
                return Constant.EMPTY;
            }
        }
    }
}
