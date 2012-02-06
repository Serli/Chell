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

import com.serli.chell.framework.exception.ChellException;
import java.io.File;
import java.io.IOException;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletResponse;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public abstract class ViewResolver {

    private PortletContext context;
    private String baseViewPath;
    private String viewFolderName;
    private String rootPath;
    private File viewFolder;

    public ViewResolver(PortletContext context, String baseViewPath, String viewFolderName) {
        this.context = context;
        this.baseViewPath = baseViewPath;
        this.viewFolderName = viewFolderName;
        this.viewFolder = createViewFolder(context);
        this.rootPath = context.getRealPath("/");
    }

    public abstract PortletRequestDispatcher getDispatcher(String viewKey, PortletRequest request) throws PortletException;
    public abstract File getViewFile(String viewKey, PortletRequest request) throws PortletException;

    public void include(String viewKey, PortletRequest request, PortletResponse response) throws PortletException, IOException {
        PortletRequestDispatcher dispatcher = getDispatcher(viewKey, request);
        if (dispatcher != null) {
            dispatcher.include(request, response);
        }
    }

    public void forward(String viewKey, PortletRequest request, PortletResponse response) throws PortletException, IOException {
        PortletRequestDispatcher dispatcher = getDispatcher(viewKey, request);
        if (dispatcher != null) {
            dispatcher.forward(request, response);
        }
    }

    public String resolveViewKey(String viewKey) {
        StringBuilder b = new StringBuilder();
        b.append(getViewFolderPath());
        b.append('/').append(viewKey);
        return b.toString();
    }

    public String getRootPath() {
        return rootPath;
    }

    public File getViewFile(String filename) {
        return new File(context.getRealPath(resolveViewKey(filename)));
    }

    public String getViewFolderPath() {
        StringBuilder b = new StringBuilder();
        if (baseViewPath != null && baseViewPath.length() > 0) {
            b.append(baseViewPath);
        }
        if (viewFolderName != null && viewFolderName.length() > 0) {
            b.append('/').append(viewFolderName);
        }
        return b.toString();
    }

    protected File createViewFolder(PortletContext context) {
        String viewFolderPath = getViewFolderPath();
        File file = new File(context.getRealPath(viewFolderPath));
        if (!file.exists()) {
            throw new ChellException("Can not find view folder : " + viewFolderPath + " for portlet " + context.getPortletContextName());
        }
        return file;
    }

    public File getViewFolder() {
        return viewFolder;
    }

    public String getBaseViewPath() {
        return baseViewPath;
    }

    public void setBaseViewPath(String baseViewPath) {
        this.baseViewPath = baseViewPath;
    }

    public String getViewFolderName() {
        return viewFolderName;
    }

    public void setViewFolderName(String viewFolderName) {
        this.viewFolderName = viewFolderName;
    }
}
