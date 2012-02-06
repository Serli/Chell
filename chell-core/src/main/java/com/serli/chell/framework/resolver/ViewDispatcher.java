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
