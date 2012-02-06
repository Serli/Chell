package com.serli.chell.framework.form.render.el;

import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.form.FormField;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class InputFieldRenderer extends AbstractFieldRenderer {

    public InputFieldRenderer(Form form) {
        super(form);
    }

    protected String render(FormField formField, String fieldName) {
        return structure.getRenderer().renderField(form, formField);
    }
}
