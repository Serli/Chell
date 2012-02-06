package com.serli.chell.framework;

import com.serli.chell.framework.ChellPortlet;
import java.io.File;
import java.io.IOException;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class ChellGroovyPortlet extends ChellPortlet {

    protected GroovyTemplate template;

    @Override
    protected void initPortlet(PortletConfig config) {
        template = new GroovyTemplate(viewResolver);
    }

    @Override
    protected void doRender(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        String viewKey = invokeRenderMethods(request);
        renderTemplate(viewKey, request, response);
    }

    protected void renderTemplate(String viewKey, RenderRequest request, RenderResponse response) throws PortletException, IOException {
        File file = viewResolver.getViewFile(viewKey, request);
        response.setContentType("text/html");
        try {
            template.render(file, currentModels.get(), response.getPortletOutputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
