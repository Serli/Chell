
package com.serli.chell.framework.validation.check;

import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.message.MessageKey;
import com.serli.chell.framework.validation.MultipleConstraint;
import com.serli.chell.framework.validation.annotation.DoubleNumber;
import java.text.MessageFormat;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class DoubleNumberCheck extends MultipleConstraint<String> {

    private double minValue;
    private double maxValue;

    public DoubleNumberCheck(DoubleNumber annotation) {
        super(annotation);
        minValue = annotation.min();
        maxValue = annotation.max();
        addTest(new KeyTest(MessageKey.VALIDATION_DECIMAL) {
            public boolean validate(Form form, String fieldName, String fieldValue) {
                try {
                    form.get(fieldName);
                    return true;
                } catch (NumberFormatException ex) {
                    return false;
                }
            }
        });
        if (minValue != Double.MIN_VALUE && maxValue != Double.MAX_VALUE) {
            addTest(new KeyTest(MessageKey.VALIDATION_DECIMAL_RANGE) {
                public boolean validate(Form form, String fieldName, String fieldValue) {
                    double value = (Double) form.get(fieldName);
                    return (value >= minValue && value <= maxValue);
                }
            });
        } else if (minValue != Double.MIN_VALUE) {
            addTest(new KeyTest(MessageKey.VALIDATION_DECIMAL_MIN) {
                public boolean validate(Form form, String fieldName, String fieldValue) {
                    return ((Double) form.get(fieldName) >= minValue);
                }
            });
        } else if (maxValue != Double.MAX_VALUE) {
            addTest(new KeyTest(MessageKey.VALIDATION_DECIMAL_MAX) {
                public boolean validate(Form form, String fieldName, String fieldValue) {
                    return ((Double) form.get(fieldName) <= maxValue);
                }
            });
        }
    }

    protected String formatDefaultErrorMessage(String pattern, String fieldLabel) {
        return MessageFormat.format(pattern, fieldLabel, String.valueOf(minValue), String.valueOf(maxValue));
    }
}
