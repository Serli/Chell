package com.serli.chell.framework.taglib;

import com.serli.chell.framework.PortletHelper;
import com.serli.chell.framework.PortletHelper.Model;
import javax.servlet.jsp.JspException;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class MessageAreaTag extends AbstractTag {

    @Override
    public int doStartTag() throws JspException {
        String messageVariableName = Model.getMessageVariableName();
        String message = (String) PortletHelper.getModel().get(messageVariableName);
        if (message != null) {
            print(message);
        }
        return SKIP_BODY;
    }
}
