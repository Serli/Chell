package com.serli.chell.framework;

import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.PortletHelper.ParameterizedModel;
import com.serli.chell.framework.message.MessageBundle;
import com.serli.chell.framework.message.MessageType;
import java.text.MessageFormat;

/**
 * @author Mathieu Ancelin (mathieu.ancelin@serli.com)
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class Render {

    protected static ModelRender modelRender = new ModelRender();

    protected Render() {
    }

    public static ParameterizedModel form(Form form) {
        return modelRender.form(form);
    }

    public static ParameterizedModel form(String name, Form form) {
        return modelRender.form(name, form);
    }

    public static ParameterizedModel msg(MessageType type, String message) {
        return modelRender.msg(type, message);
    }

    public static ParameterizedModel msgKey(MessageType type, String key, Object... arguments) {
        return modelRender.msgKey(type, key, arguments);
    }

    public static ParameterizedModel msgBundleKey(MessageType type, String bundleName, String key, Object... arguments) {
        return modelRender.msgBundleKey(type, bundleName, key, arguments);
    }

    public static ParameterizedModel rem(String name) {
        return modelRender.rem(name);
    }

    public static ParameterizedModel attr(String name, Object value) {
        return modelRender.attr(name, value);
    }

    protected static class ModelRender implements ParameterizedModel {

        private ModelRender() {
        }

        public ParameterizedModel attr(String name, Object value) {
            PortletHelper.getModel().put(name, value);
            return this;
        }

        public ParameterizedModel form(Form form) {
            attr(form.getNamespace(), form);
            return this;
        }

        public ParameterizedModel form(String name, Form form) {
            attr(name, form);
            return this;
        }

        public ParameterizedModel rem(String name) {
            PortletHelper.getModel().remove(name);
            return this;
        }

        public ParameterizedModel msg(MessageType type, String message) {
            PortletHelper.getModel().msg(type, message);
            return this;
        }

        public ParameterizedModel msgKey(MessageType type, String key, Object... arguments) {
            String message = getMessage(null, key, arguments);
            PortletHelper.getModel().msg(type, message);
            return this;
        }

        public ParameterizedModel msgBundleKey(MessageType type, String bundleName, String key, Object... arguments) {
            String message = getMessage(bundleName, key, arguments);
            PortletHelper.getModel().msg(type, message);
            return this;
        }

        protected String getMessage(String bundleName, String key, Object... arguments) {
            String message;
            if (bundleName != null && bundleName.length() > 0) {
                message = MessageBundle.getBundleMessage(bundleName, key);
            } else {
                message = MessageBundle.getMessage(key);
            }
            if (arguments.length > 0) {
                message = MessageFormat.format(message, arguments);
            }
            return message;
        }
    }
}
