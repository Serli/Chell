
package com.serli.chell.framework.validation.check;

import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.message.MessageKey;
import com.serli.chell.framework.validation.MultipleConstraint;
import com.serli.chell.framework.validation.annotation.IntegerNumber;
import java.text.MessageFormat;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class IntegerNumberCheck extends MultipleConstraint<String> {

    private int minValue;
    private int maxValue;

    public IntegerNumberCheck(IntegerNumber annotation) {
        super(annotation);
        minValue = annotation.min();
        maxValue = annotation.max();
        addTest(new KeyTest(MessageKey.VALIDATION_INTEGER) {
            public boolean validate(Form form, String fieldName, String fieldValue) {
                try {
                    form.get(fieldName);
                    return true;
                } catch (NumberFormatException ex) {
                    return false;
                }
            }
        });
        if (minValue != Integer.MIN_VALUE && maxValue != Integer.MAX_VALUE) {
            addTest(new KeyTest(MessageKey.VALIDATION_INTEGER_RANGE) {
                public boolean validate(Form form, String fieldName, String fieldValue) {
                    int value = (Integer) form.get(fieldName);
                    return (value >= minValue && value <= maxValue);
                }
            });
        } else if (minValue != Integer.MIN_VALUE) {
            addTest(new KeyTest(MessageKey.VALIDATION_INTEGER_MIN) {
                public boolean validate(Form form, String fieldName, String fieldValue) {
                    return ((Integer) form.get(fieldName) >= minValue);
                }
            });
        } else if (maxValue != Integer.MAX_VALUE) {
            addTest(new KeyTest(MessageKey.VALIDATION_INTEGER_MAX) {
                public boolean validate(Form form, String fieldName, String fieldValue) {
                    return ((Integer) form.get(fieldName) <= maxValue);
                }
            });
        }
    }

    protected String formatDefaultErrorMessage(String pattern, String fieldLabel) {
        return MessageFormat.format(pattern, fieldLabel, minValue, maxValue);
    }
}
