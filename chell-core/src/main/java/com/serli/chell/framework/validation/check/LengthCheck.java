
package com.serli.chell.framework.validation.check;

import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.message.MessageKey;
import com.serli.chell.framework.validation.MultipleConstraint;
import com.serli.chell.framework.validation.annotation.Length;
import java.text.MessageFormat;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class LengthCheck extends MultipleConstraint<String> {

    private int minLength;
    private int maxLength;

    public LengthCheck(Length annotation) {
        super(annotation);
        int value = annotation.value();
        minLength = annotation.min();
        maxLength = annotation.max();
        if (value > 0) {
            minLength = value;
            maxLength = value;
            addTest(new KeyTest(MessageKey.VALIDATION_LENGTH) {
                public boolean validate(Form form, String fieldName, String fieldValue) {
                    return (fieldValue.length() == minLength);
                }
            });
        } else if (minLength > 0 && maxLength > 0) {
            addTest(new KeyTest(MessageKey.VALIDATION_LENGTH_RANGE) {
                public boolean validate(Form form, String fieldName, String fieldValue) {
                    int length = fieldValue.length();
                    return (length >= minLength && length <= maxLength);
                }
            });
        } else if (minLength > 0) {
            addTest(new KeyTest(MessageKey.VALIDATION_LENGTH_MIN) {
                public boolean validate(Form form, String fieldName, String fieldValue) {
                    return (fieldValue.length() >= minLength);
                }
            });
        } else if (maxLength > 0) {
            addTest(new KeyTest(MessageKey.VALIDATION_LENGTH_MAX) {
                public boolean validate(Form form, String fieldName, String fieldValue) {
                    return (fieldValue.length() <= maxLength);
                }
            });
        }
    }

    protected String formatDefaultErrorMessage(String pattern, String fieldLabel) {
        return MessageFormat.format(pattern, fieldLabel, minLength, maxLength);
    }
}
