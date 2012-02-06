
package com.serli.chell.framework.validation.check;

import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.form.FormField;
import com.serli.chell.framework.validation.Constraint;
import com.serli.chell.framework.validation.ValidationException;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public final class NotRequiredTabCheck implements Constraint<String[]> {

    public static final NotRequiredTabCheck DEFAULT_INSTANCE = new NotRequiredTabCheck();

    private NotRequiredTabCheck() {
    }

    public void validateField(Form form, FormField field, String[] fieldValues) throws ValidationException {
        if (fieldValues == null || fieldValues.length == 0) {
            throw new ValidationException();
        }
    }

    public boolean isTabCheck() {
        return true;
    }
}
