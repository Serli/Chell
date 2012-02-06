package com.serli.chell.framework.taglib;

import javax.servlet.jsp.JspException;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class LabelTag extends AbstractFieldTag {

    @Override
    public int doStartTag() throws JspException {
        print(getFormInstance().getLabel().get(field));
        return SKIP_BODY;
    }
}
