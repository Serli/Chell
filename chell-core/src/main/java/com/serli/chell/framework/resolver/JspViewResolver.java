
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
