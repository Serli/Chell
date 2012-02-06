
package com.serli.chell.framework.validation;

import com.serli.chell.framework.form.Form;
import java.lang.annotation.Annotation;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public abstract class CrossConstraint extends Check<String> {

    private String otherFieldName;

    public CrossConstraint(Annotation annotation) {
        super(annotation);
        otherFieldName = ""; // TODO : Ameliorer la lecture des annotation
    }

    public boolean validate(Form form, String fieldName, String fieldValue) {
        String fieldValue2 = form.getFieldValue(fieldName);
        return crossValidate(form, fieldName, otherFieldName, fieldValue, fieldValue2);
    }

    public abstract boolean crossValidate(Form form,
                                          String fieldName1, String fieldName2,
                                          String fieldValue1, String fieldValue2);
}
