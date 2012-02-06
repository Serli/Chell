package com.serli.chell.framework.form.render.el;

import com.serli.chell.framework.constant.Constant;
import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.form.FormField;
import com.serli.chell.framework.form.FormStructure;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public abstract class AbstractFieldRenderer extends AbstractFieldAccessor<String> {

    protected FormStructure structure;

    public AbstractFieldRenderer(Form form) {
        super(form);
        this.structure = form.getStructure();
    }

    @Override
    public String get(Object key) {
        String fieldName = (String) key;
        String value = super.get(fieldName);
        if (value == null) {
            FormField formField = form.getField(fieldName);
            value = render(formField, fieldName);
            if (value == null) {
                value = Constant.EMPTY;
            }
            put(fieldName, value);
        }
        return value;
    }

    protected abstract String render(FormField formField, String fieldName);
}
