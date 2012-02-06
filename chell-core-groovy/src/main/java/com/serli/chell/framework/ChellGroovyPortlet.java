/*
 *  Copyright 2011-2012 SERLI (www.serli.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

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
