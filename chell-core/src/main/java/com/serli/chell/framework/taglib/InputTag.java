package com.serli.chell.framework.taglib;

import javax.servlet.jsp.JspException;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class InputTag extends AbstractFieldTag {

    @Override
    public int doStartTag() throws JspException {
        print(getFormInstance().getInput().get(field));
        return SKIP_BODY;
    }
}
