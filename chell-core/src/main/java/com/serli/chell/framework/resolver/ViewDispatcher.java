
package com.serli.chell.framework.resolver;

import java.io.File;
import java.io.IOException;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class ViewDispatcher implements PortletRequestDispatcher {
    
    private File viewFile;
    private PortletRequestDispatcher dispatcher;

    public ViewDispatcher(PortletRequestDispatcher dispatcher, File viewFile) {
        this.dispatcher = dispatcher;
        this.viewFile = viewFile;
    }

    public void include(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        dispatcher.include(request, response);
    }

    public void include(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        dispatcher.include(request, response);
    }

    public void forward(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        dispatcher.forward(request, response);
    }

    public File getViewFile() {
        return viewFile;
    }
}
