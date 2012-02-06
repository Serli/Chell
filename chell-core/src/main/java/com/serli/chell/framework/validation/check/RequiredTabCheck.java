
package com.serli.chell.framework.validation.check;

import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.message.MessageAnnotationHelper;
import com.serli.chell.framework.validation.Check;
import com.serli.chell.framework.validation.annotation.Required;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class RequiredTabCheck extends Check<String[]> {

    public static final RequiredTabCheck DEFAULT_INSTANCE = new RequiredTabCheck();

    private RequiredTabCheck() {
        super(null);
        setMessage(MessageAnnotationHelper.getDefaultMessage(Required.class));
    }

    public RequiredTabCheck(Required annotation) {
        super(annotation);
    }

    public boolean validate(Form form, String fieldName, String[] fieldValue) {
        return (fieldValue != null && fieldValue.length > 0);
    }
}
