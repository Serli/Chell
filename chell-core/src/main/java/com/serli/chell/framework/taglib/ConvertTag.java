package com.serli.chell.framework.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class ConvertTag extends AbstractFieldTag {

    private String var;

    @Override
    public int doStartTag() throws JspException {
        Object convertedValue = getFormInstance().get(field);
        if (convertedValue != null) {
            if (var == null) {
                print(convertedValue.toString());
            } else {
                pageContext.setAttribute(var, convertedValue, PageContext.PAGE_SCOPE);
            }
        }
        return SKIP_BODY;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }
}
