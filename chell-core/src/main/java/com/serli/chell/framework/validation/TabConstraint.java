
package com.serli.chell.framework.validation;

import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.form.FormField;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class TabConstraint implements Constraint<String[]> {

    private Constraint<String> constraint;

    public TabConstraint(Constraint<String> contraint) {
        this.constraint = contraint;
    }

    public void validateField(Form form, FormField field, String[] fieldValues) throws ValidationException {
        for (String fieldValue : fieldValues) {
            constraint.validateField(form, field, fieldValue);
        }
    }
}
