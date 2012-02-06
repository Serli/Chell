
package com.serli.chell.framework.validation.check;

import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.message.MessageAnnotationHelper;
import com.serli.chell.framework.validation.Check;
import com.serli.chell.framework.validation.annotation.Required;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class RequiredCheck extends Check<String> {

    public static final RequiredCheck DEFAULT_INSTANCE = new RequiredCheck();

    private RequiredCheck() {
        super(null);
        setMessage(MessageAnnotationHelper.getDefaultMessage(Required.class));
    }

    public RequiredCheck(Required annotation) {
        super(annotation);
    }

    public boolean validate(Form form, String fieldName, String fieldValue) {
        return (fieldValue != null && fieldValue.length() > 0);
    }
}
