
package com.serli.chell.framework.resolver;

import javax.portlet.PortletContext;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class HtmlViewResolver extends JspViewResolver {

    public static final String HTML_EXTENSION = "html";

    public HtmlViewResolver(PortletContext context, String baseViewPath, String viewFolderName) {
        super(context, baseViewPath, viewFolderName);
    }

    @Override
    protected String getViewExtension() {
        return HTML_EXTENSION;
    }
}
