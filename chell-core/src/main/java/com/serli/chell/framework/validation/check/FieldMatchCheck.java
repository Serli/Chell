
package com.serli.chell.framework.validation.check;

import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.validation.Check;
import com.serli.chell.framework.validation.annotation.FieldMatch;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class FieldMatchCheck extends Check<String> {

    public FieldMatchCheck(FieldMatch annotation) {
        super(annotation);
    }

    public boolean validate(Form form, String fieldName, String fieldValue) {
        try {
            String[] parts = fieldValue.split("[.]");
            if (parts.length == 4) {
                for (int i = 0, p; i< parts.length; i++) {
                    p = Integer.parseInt(parts[i]);
                    if (p < 0 || p > 255) {
                        return false;
                    }
                }
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }
}
