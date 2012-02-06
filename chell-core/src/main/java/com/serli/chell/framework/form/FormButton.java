
package com.serli.chell.framework.form;

import com.serli.chell.framework.constant.Constant;
import com.serli.chell.framework.form.FormButton.OnClickBuilder;
import com.serli.chell.framework.message.Message;
import com.serli.chell.framework.message.MessageAnnotationHelper;
import com.serli.chell.framework.reflect.AnnotationMetaData.Type;
import com.serli.chell.framework.reflect.AnnotationMetaDataInfos;
import com.serli.chell.framework.util.ClassUtils;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class FormButton implements Comparable<FormButton> {

    private static final String UNDEFINED_NAME = "undefined";
    private static final String NAME_PREFIX = "-button";

    public static enum ButtonType {
        SUBMIT, BUTTON;
    }

    public interface OnClickBuilder {
        String getOnClickLink(Form form, FormButton button);
    }

    private Message message = Message.empty();
    private String cssClass;
    private String htmlId;
    private String name;
    private String htmlType;
    private int order = Constant.DEFAULT_ORDER;
    private OnClickBuilder onClickBuilder = ConstantOnClickBuilder.NULL;
    private Map<Type, Object> definitions;

    protected FormButton() {
    }

    public FormButton(Message message, String cssClass, String htmlId, String name, ButtonType type, String onClick) {
        this(message, cssClass, htmlId, name, type, new ConstantOnClickBuilder(onClick));
    }

    public FormButton(Message message, String cssClass, String htmlId, String name, ButtonType type, OnClickBuilder onClickBuilder) {
        setName(name);
        setType(type);
        this.message = message;
        this.cssClass = cssClass;
        this.htmlId = htmlId;
        this.onClickBuilder = onClickBuilder;
    }

    public static FormButton createFromAnnotation(Annotation annotation) {
        AnnotationMetaDataInfos annotationInfos = AnnotationMetaDataInfos.get(annotation.annotationType());
        return createFromAnnotation(annotationInfos, annotation);
    }

    public static FormButton createFromAnnotation(AnnotationMetaDataInfos annotationInfos, Annotation annotation) {
        FormButton button = new FormButton();
        button.message = MessageAnnotationHelper.getMessage(annotation);
        for (Type type : annotationInfos.getTypes()) {
            switch (type) {
                case FORM_TYPE: case MESSAGE_BUNDLE: case MESSAGE_KEY: case MESSAGE_TEXT: break;
                case BUTTON_NAME: button.setName((String) annotationInfos.getValue(type, annotation)); break;
                case HTML_CSS_CLASS: button.cssClass = (String) annotationInfos.getValue(type, annotation); break;
                case HTML_ID: button.htmlId = (String) annotationInfos.getValue(type, annotation); break;
                case BUTTON_ORDER:
                    Integer order = (Integer) annotationInfos.getValue(type, annotation);
                    if (order != null) {
                        button.order = order.intValue();
                    }
                    break;
                case BUTTON_TYPE: button.setType((ButtonType) annotationInfos.getValue(type, annotation)); break;
                case BUTTON_ON_CLICK_BUILDER:
                    Class<? extends OnClickBuilder> builderClass = (Class<? extends OnClickBuilder>) annotationInfos.getValue(type, annotation);
                    if (builderClass != null) {
                        button.onClickBuilder = ClassUtils.newInstance(builderClass);
                    }
                    break;
                case BUTTON_ON_CLICK:
                    String onClick = (String) annotationInfos.getValue(type, annotation);
                    button.onClickBuilder = new ConstantOnClickBuilder(onClick); break;
                default: button.addDefinition(type, annotationInfos.getValue(type, annotation)); break;
            }
        }
        return button;
    }

    public Object getDefinition(Type type) {
        if (definitions != null) {
            return definitions.get(type);
        }
        return null;
    }

    public String getOnClick(Form form) {
        return onClickBuilder.getOnClickLink(form, this);
    }

    public String getHtmlType() {
        return htmlType;
    }

    public String getName() {
        return name;
    }

    public String getCssClass() {
        return cssClass;
    }

    public String getHtmlId() {
        return htmlId;
    }

    public String getValue() {
        return message.get();
    }

    public String getValue(Locale locale) {
        return message.get(locale);
    }

    private void setName(String name) {
        if (name == null || name.length() == 0) {
            name = UNDEFINED_NAME;
        }
        this.name = name + NAME_PREFIX;
    }

    private void setType(ButtonType type) {
        if (type == null) {
            type = ButtonType.BUTTON;
        }
        this.htmlType = type.name().toLowerCase();
    }

    private void addDefinition(Type type, Object value) {
        if (definitions == null) {
            definitions = new HashMap<Type, Object>();
        }
        definitions.put(type, value);
    }

    public boolean hasDefinedOrder() {
        return (order != Constant.DEFAULT_ORDER);
    }

    public int getOrder() {
        return order;
    }

    public int compareTo(FormButton other) {
        return order - other.order;
    }

    private static class ConstantOnClickBuilder implements OnClickBuilder {

        private String onClick;

        public ConstantOnClickBuilder(String onClick) {
            this.onClick = onClick;
        }

        public String getOnClickLink(Form form, FormButton button) {
            return onClick;
        }

        private static final ConstantOnClickBuilder NULL = new ConstantOnClickBuilder(null);
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append(name).append('[');
        b.append("type = ").append(htmlType).append(']');
        return b.toString();
    }
}
