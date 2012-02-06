package com.serli.chell.framework.taglib;

import com.serli.chell.framework.exception.ChellException;
import com.serli.chell.framework.PortletHelper;
import com.serli.chell.framework.form.Form;
import javax.servlet.jsp.tagext.Tag;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public abstract class AbstractFormTag extends AbstractTag {

    protected String form;

    public Form getFormInstance() {
        Form result = (Form) PortletHelper.getModel().get(getForm());
        if (result == null) {
            throw new ChellException("Can not find form '" + form + "' in the model.");
        }
        return result;
    }

    public String getForm() {
        if (form == null) {
            form = getFormAncestor(this);
            if (form == null) {
                throw new ChellException("Can not find the parent form.");
            }
        }
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    protected String getFormAncestor(Tag tag) {
        while (tag != null) {
            tag = tag.getParent();
            if (tag instanceof FormTag) {
                return ((FormTag) tag).getForm();
            } else if (tag instanceof DefineTag) {
                return ((DefineTag) tag).getForm();
            }
        }
        return null;
    }
}
