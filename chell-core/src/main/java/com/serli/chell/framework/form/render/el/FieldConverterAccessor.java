package com.serli.chell.framework.form.render.el;

import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.form.FormField;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class FieldConverterAccessor extends AbstractFieldAccessor<Object> {

    public FieldConverterAccessor(Form form) {
        super(form);
    }

    @Override
    public Object get(Object key) {
        String fieldName = (String) key;
        Object convertedValue = super.get(fieldName);
        if (convertedValue == null && !containsKey(fieldName)) {
            FormField formField = form.getField(fieldName);
            convertedValue = formField.getConvertedValue(form);
            super.put(fieldName, convertedValue);
        }
        return convertedValue;
    }

    @Override
    public Object put(String key, Object convertedValue) {
        String fieldName = (String) key;
        FormField formField = form.getField(fieldName);
        formField.setConvertedValue(form, convertedValue);
        return super.put(fieldName, convertedValue);
    }
}
