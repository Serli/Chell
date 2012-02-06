package com.serli.chell.framework.validation.check;

import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.message.MessageKey;
import com.serli.chell.framework.validation.MultipleConstraint;
import java.text.MessageFormat;

import com.serli.chell.framework.validation.annotation.Size;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class SizeCheck extends MultipleConstraint<String[]> {

    private int minSize;
    private int maxSize;

    public SizeCheck(Size annotation) {
        super(annotation);
        int value = annotation.value();
        minSize = annotation.min();
        maxSize = annotation.max();
        if (value > 0) {
            minSize = value;
            maxSize = value;
            addTest(new KeyMultipleTest(MessageKey.VALIDATION_SIZE) {
                public boolean validate(Form form, String fieldName, String[] fieldValue) {
                    return (fieldValue.length == minSize);
                }
            });
        } else if (minSize > 0 && maxSize > 0) {
            addTest(new KeyMultipleTest(MessageKey.VALIDATION_SIZE_RANGE) {
                public boolean validate(Form form, String fieldName, String[] fieldValue) {
                    return (fieldValue.length >= minSize && fieldValue.length <= maxSize);
                }
            });
        } else if (minSize > 0) {
            addTest(new KeyMultipleTest(MessageKey.VALIDATION_SIZE_MIN) {
                public boolean validate(Form form, String fieldName, String[] fieldValue) {
                    return (fieldValue.length >= minSize);
                }
            });
        } else if (maxSize > 0) {
            addTest(new KeyMultipleTest(MessageKey.VALIDATION_SIZE_MAX) {
                public boolean validate(Form form, String fieldName, String[] fieldValue) {
                    return (fieldValue.length <= maxSize);
                }
            });
        }
    }

    protected String formatDefaultErrorMessage(String pattern, String fieldLabel) {
        return MessageFormat.format(pattern, fieldLabel, minSize, maxSize);
    }
}
