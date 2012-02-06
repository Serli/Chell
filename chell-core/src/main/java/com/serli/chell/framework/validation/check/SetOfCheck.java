
package com.serli.chell.framework.validation.check;

import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.validation.Check;
import com.serli.chell.framework.validation.annotation.SetOf;
import java.util.HashSet;
import java.util.Set;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class SetOfCheck extends Check<String> {

    private Set<String> availableValues;
    private boolean ignoreCase;

    public SetOfCheck(SetOf annotation) {
        super(annotation);
        this.availableValues = new HashSet<String>();
        this.ignoreCase = annotation.ignoreCase();
        if (this.ignoreCase) {
            for (String availableValue : annotation.value()) {
                this.availableValues.add(availableValue.toLowerCase());
            }
        } else {
            for (String availableValue : annotation.value()) {
                this.availableValues.add(availableValue);
            }
        }
    }

    public boolean validate(Form form, String fieldName, String fieldValue) {
        if (ignoreCase) {
            fieldValue = fieldValue.toLowerCase();
        }
        return availableValues.contains(fieldValue);
    }
}
