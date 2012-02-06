package com.serli.chell.framework.form.render.el;

import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.form.FormField;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class UploadFilenameAccessor extends AbstractFieldAccessor<String> {

    public UploadFilenameAccessor(Form form) {
        super(form);
    }

    @Override
    public String get(Object key) {
        String fieldName = (String) key;
        String displayFileName = super.get(fieldName);
        if (displayFileName == null && !containsKey(fieldName)) {
            FormField formField = form.getField(fieldName);
            if (formField.isUploadField()) {
                displayFileName = formField.getUploadHandler().onDisplay(form.get(fieldName));
                super.put(fieldName, displayFileName);
            } else {
                super.put(fieldName, null);
            }
        }
        return displayFileName;
    }

    @Override
    public String put(String key, String convertedValue) {
        String fieldName = (String) key;
        FormField formField = form.getField(fieldName);
        formField.setConvertedValue(form, convertedValue);
        return super.put(fieldName, convertedValue);
    }
}
