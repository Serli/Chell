
package com.serli.chell.framework.validation;

import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.message.MessageBundle;
import com.serli.chell.framework.message.Message;
import com.serli.chell.framework.message.MessageKey;
import java.text.MessageFormat;
import java.util.Locale;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public abstract class Test<T> {

    private Message message;

    public Test(Message message) {
        this.message = message;
    }

    /**
     * Validate a field value.
     * @param form The current form
     * @param fieldName The current field name
     * @param fieldValue The current field value (not null, and if value is String, not empty)
     * @return True is the value is valid, false otherwise
     */
    public abstract boolean validate(Form form, String fieldName, T fieldValue);

    protected void setMessage(Message message) {
        this.message = message;
    }

    public String getErrorMessage(Locale locale, String fieldLabel) {
        String messagePattern = getMessagePattern(locale, message);
        return formatErrorMessage(messagePattern, fieldLabel);
    }

    protected String formatErrorMessage(String pattern, String fieldLabel) {
        return MessageFormat.format(pattern, fieldLabel);
    }

    public static String getMessagePattern(Locale locale, Message message) {
        String result = message.get(locale);
        if (result == null || result.length() == 0) {
            result = MessageBundle.getMessage(locale, MessageKey.VALIDATION_DEFAULT);
        }
        return result;
    }
}
