
package com.serli.chell.framework.validation;

import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.form.FormField;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public interface Constraint<T> {

    /**
     * Test the constraint on the field.
     * @param form The current form
     * @param field The current field
     * @param fieldValue The current field value (not null, and if value is String, not empty)
     * @throws ValidationException When validation must be stopped.
     */
    void validateField(Form form, FormField field, T fieldValue) throws ValidationException;
}
