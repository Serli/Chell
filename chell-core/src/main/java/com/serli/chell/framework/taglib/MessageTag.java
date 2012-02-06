package com.serli.chell.framework.taglib;

import com.serli.chell.framework.constant.Constant;
import com.serli.chell.framework.message.MessageBundle;
import com.serli.chell.framework.message.MessageType;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class MessageTag extends AbstractTag {

    private String key = null;
    private String bundle = null;
    private String text = Constant.EMPTY;
    private String var = null;
    private MessageType type;

    @Override
    public int doStartTag() throws JspException {
        String result;
        if (key != null) {
            if (bundle != null) {
                result = MessageBundle.getBundleMessage(bundle, key);
            } else {
                result = MessageBundle.getMessage(key);
            }
        } else {
            result = text;
        }
        if (type != null) {
            result = type.buildMessage(result);
        }
        if (var == null) {
            print(result);
        } else {
            pageContext.setAttribute(var, result, PageContext.PAGE_SCOPE);
        }
        return SKIP_BODY;
    }

    public String getBundle() {
        return bundle;
    }

    public void setBundle(String bundle) {
        this.bundle = bundle;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setType(String type) {
        this.type = MessageType.find(type);
    }
}
