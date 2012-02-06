package com.serli.chell.framework.taglib;


import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;



/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public abstract class AbstractTag extends TagSupport {

    protected void print(String content) throws JspException {
        try {
            pageContext.getOut().print(content);
        } catch (IOException ex) {
            throw new JspException(ex);
        }
    }
}
