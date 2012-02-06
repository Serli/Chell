package com.serli.chell.framework.taglib;

import com.serli.chell.framework.PortletHelper;
import com.serli.chell.framework.constant.Constant;
import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.form.FormStructure;
import com.serli.chell.framework.form.render.FormRenderer;
import javax.portlet.PortletException;
import javax.servlet.jsp.JspException;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class FormTag extends AbstractFormTag {

    private static final String POST = "POST";
    private static final String END_FORM = "</form>";
    private static final String UPLOAD_ENCTYPE = "multipart/form-data";
    private static final String FORM_MAX_FILE_SIZE = "MAX_FILE_SIZE";

    private String action;
    private String className;
    private String accept;
    private String acceptCharset;
    private String style;

    @Override
    public int doStartTag() throws JspException {
        try {
            FormRenderer renderer = FormRenderer.DEFAULT;
            Form instance = getFormInstance();
            String maxUploadSize = Constant.EMPTY;
            String enctype = null;
            if (instance != null) {
                renderer = instance.getRenderer();
                FormStructure structure = instance.getStructure();
                if (structure.isUploadForm()) {
                    enctype = UPLOAD_ENCTYPE;
                    long size = structure.getMaxUploadFileSize();
                    if (size > 0) {
                        maxUploadSize = renderer.renderInput("hidden", FORM_MAX_FILE_SIZE, 0, 0, String.valueOf(size), false, null, null, null);
                    }
                }
            }
            String actionUrl = PortletHelper.getActionURL(action);
            String content = renderer.renderForm(Constant.EMPTY, actionUrl, POST, accept, enctype, acceptCharset, className, style, id);
            print(content.substring(0, content.length() - END_FORM.length()) + maxUploadSize);
            return EVAL_BODY_INCLUDE;
        } catch (PortletException ex) {
            throw new JspException(ex);
        }
    }

    @Override
    public int doEndTag() throws JspException {
        print(END_FORM);
        return EVAL_PAGE;
    }

    @Override
    public Form getFormInstance() {
        if (form != null) {
            return super.getFormInstance();
        }
        return null;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getAcceptCharset() {
        return acceptCharset;
    }

    public void setAcceptCharset(String acceptCharset) {
        this.acceptCharset = acceptCharset;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
}
