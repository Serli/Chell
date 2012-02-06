
package com.serli.chell.framework.validation;

import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.form.FormField;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public final class NoConstraint implements Constraint<Object> {
    public void validateField(Form form, FormField field, Object fieldValue) throws ValidationException {
    }
}
