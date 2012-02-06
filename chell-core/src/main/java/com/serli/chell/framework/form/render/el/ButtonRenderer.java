package com.serli.chell.framework.form.render.el;

import com.serli.chell.framework.exception.ChellException;
import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.form.FormButton;
import com.serli.chell.framework.form.FormStructure;
import com.serli.chell.framework.form.render.FormRenderer;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class ButtonRenderer extends AbstractFieldAccessor<String> {

    public ButtonRenderer(Form form) {
        super(form);
    }

    @Override
    public String get(Object key) {
        String buttonName = (String) key;
        String value = super.get(buttonName);
        if (value == null) {
            FormStructure structure = form.getStructure();
            FormButton button = structure.getButton(buttonName);
            if (button != null) {
                FormRenderer fr = structure.getRenderer();
                value = fr.renderFormButton(form, button);
                put(buttonName, value);
            } else {
                throw new ChellException("No button '" + buttonName + "' found in form type : " + form.getClass().getName());
            }
        }
        return value;
    }
}
