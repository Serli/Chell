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

import com.serli.chell.framework.util.FileUtils;
import java.io.File;
import java.io.FilenameFilter;
import javax.portlet.PortletContext;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class JspViewResolver extends DefaultViewResolver  {

    public JspViewResolver(PortletContext context, String baseViewPath, String viewFolderName) {
        super(context, baseViewPath, viewFolderName);
        File[] viewFiles = getViewFolder().listFiles(getExtensionFilter());
        if (viewFiles != null) {
            for (File viewFile : viewFiles) {
                if (!viewFile.isDirectory()) {
                    addViewWithFile(context, viewFile);
                }
            }
        }
    }

    protected ViewDispatcher addViewWithFile(PortletContext context, File file) {
        String filename = file.getName();
        String viewKey = getViewKeyFromFilename(filename);
        String viewPath = resolveViewFilename(filename);
        ViewDispatcher result = createViewDispatcher(context, viewPath, file);
        viewDispatcherByKey.put(viewKey, result);
        return result;
    }

    public String resolveViewFilename(String filename) {
        StringBuilder b = new StringBuilder();
        b.append(getViewFolderPath());
        b.append('/').append(filename);
        return b.toString();
    }

    protected String getViewKeyFromFilename(String filename) {
        return FileUtils.withoutExtension(filename);
    }

    protected FilenameFilter getExtensionFilter() {
        return new FilenameFilter() {
            public boolean accept(File dir, String name) {
                String extension = FileUtils.extension(name);
                return getViewExtension().equalsIgnoreCase(extension);
            }
        };
    }
}
