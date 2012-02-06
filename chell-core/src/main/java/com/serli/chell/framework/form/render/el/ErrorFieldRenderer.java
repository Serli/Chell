package com.serli.chell.framework.form.render.el;

import com.serli.chell.framework.constant.Constant;
import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.form.FormField;
import com.serli.chell.framework.message.MessageType;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class ErrorFieldRenderer extends AbstractFieldRenderer {

    private String all = null;

    public ErrorFieldRenderer(Form form) {
        super(form);
    }

    protected String render(FormField formField, String fieldName) {
        String errorMsg = form.getError().get(fieldName);
        if (errorMsg != null) {
            return structure.getRenderer().renderError(form, errorMsg);
        }
        return Constant.EMPTY;
    }

    @Override
    public boolean isEmpty() {
        return form.getError().isEmpty();
    }

    @Override
    public int size() {
        return form.getError().size();
    }

    @Override
    public void clear() {
        super.clear();
        all = null;
    }

    @Override
    public String toString() {
        if (all == null) {
            if (form.hasErrors()) {
                String errorSection = structure.getRenderer().renderErrorSection(form);
                all = MessageType.ERROR.buildMessage(errorSection);
            } else {
                all = Constant.EMPTY;
            }
        }
        return all;
    }
}
