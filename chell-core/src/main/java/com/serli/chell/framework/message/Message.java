
package com.serli.chell.framework.message;

import com.serli.chell.framework.PortletHelper;
import com.serli.chell.framework.constant.Constant;
import com.serli.chell.framework.reflect.AnnotationMetaData.Type;
import java.io.Serializable;
import java.util.Locale;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class Message implements Serializable {

    private String key;
    private String bundle;
    private String text;

    private Message() {
        this.text = null;
        this.key = null;
        this.bundle = null;
    }

    private Message(String text, String key, String bundle) {
        this.text = (Constant.EMPTY.equals(text) ? null : text);
        this.key = (Constant.EMPTY.equals(key) ? null : key);
        this.bundle = (Constant.EMPTY.equals(bundle) ? null : bundle);
    }

    private Message(Message source) {
        this.key = source.key;
        this.bundle = source.bundle;
        this.text = source.text;
    }

    public void set(Type type, String value, String defaultValue) {
        if (value != null && value.length() > 0 && !value.equals(defaultValue)) {
            switch (type) {
                case MESSAGE_TEXT: text = value; key = null; break;
                case MESSAGE_KEY: key = value; text = null; break;
                case MESSAGE_BUNDLE: bundle = value; break;
            }
        }
    }

    public void set(Type type, String value) {
        if (value != null && value.length() > 0) {
            switch (type) {
                case MESSAGE_TEXT: text = value; key = null; break;
                case MESSAGE_KEY: key = value; text = null; break;
                case MESSAGE_BUNDLE: bundle = value; break;
            }
        }
    }

    public String getBundle() {
        return bundle;
    }

    public String getKey() {
        return key;
    }
    
    public String getText() {
        return text;
    }

    public boolean isEmpty() {
        return (key == null && text == null);
    }
    
    public String get() {
        return get(PortletHelper.getLocale(), text, key, bundle);
    }

    public String get(Locale locale) {
        return get(locale, text, key, bundle);
    }

    public static Message empty() {
        return new Message();
    }

    public static Message copy(Message source) {
        return new Message(source);
    }
    
    public static Message withText(String message) {
        return new Message(message, null, null);
    }

    public static Message withKey(String key) {
        return new Message(null, key, null);
    }

    public static Message withKey(String key, String bundle) {
        return new Message(null, key, bundle);
    }

    public static String get(Locale locale, String text, String key, String bundle) {
        String result = null;
        if (key != null) {
            if (bundle != null) {
                result = MessageBundle.getBundleMessage(bundle, locale, key);
            } else {
                result = MessageBundle.getMessage(locale, key);
            }
        } else {
            result = text;
        }
        return result;
    }
}
