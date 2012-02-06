
package com.serli.chell.framework.validation;

import com.serli.chell.framework.PortletHelper;
import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.form.FormField;
import com.serli.chell.framework.message.MessageAnnotationHelper;
import java.lang.annotation.Annotation;
import java.util.Locale;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public abstract class Check<T> extends Test<T> implements Constraint<T> {

    public Check(Annotation annotation) {
        super(annotation == null ? null : MessageAnnotationHelper.getMessage(annotation));
    }

    public void validateField(Form form, FormField field, T fieldValue) throws ValidationException {
        String fieldName = field.getName();
        if (!validate(form, fieldName, fieldValue)) {
            Locale locale = PortletHelper.getLocale();
            String errorMsg = getErrorMessage(locale, field.getLabel(locale));
            form.addError(fieldName, errorMsg);
            throw new ValidationException();
        }
    }
}
