
package com.serli.chell.framework.validation.check;

import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.form.FormField;
import com.serli.chell.framework.validation.Constraint;
import com.serli.chell.framework.validation.ValidationException;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public final class NotRequiredCheck implements Constraint<String> {

    public static final NotRequiredCheck DEFAULT_INSTANCE = new NotRequiredCheck();

    private NotRequiredCheck() {
    }

    public void validateField(Form form, FormField field, String fieldValue) throws ValidationException {
        if (fieldValue == null || fieldValue.length() == 0) {
            throw new ValidationException();
        }
    }

    public boolean isTabCheck() {
        return false;
    }
}
