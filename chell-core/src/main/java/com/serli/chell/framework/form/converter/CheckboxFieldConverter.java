
package com.serli.chell.framework.form.converter;

import com.serli.chell.framework.form.Form;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class CheckboxFieldConverter implements FieldConverter<Boolean, String> {

    public String fromObject(Boolean objectValue) {
        return Form.ON;
    }

    public Boolean toObject(String fieldValue) {
        return Boolean.TRUE;
    }
}
