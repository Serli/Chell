
package com.serli.chell.framework.resolver;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.WindowState;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class DefaultViewResolver extends ViewResolver {

    public static final String JSP_EXTENSION = "jsp";
    public static final String VIEW_KEY = "view";
    public static final String MAXIMIZED_VIEW_KEY = "view-maximized";
    public static final String HELP_KEY = "help";
    public static final String EDIT_KEY = "edit";

    protected Map<String, ViewDispatcher> viewDispatcherByKey;

    public DefaultViewResolver(PortletContext context, String baseViewPath, String viewFolderName) {
        super(context, baseViewPath, viewFolderName);
        viewDispatcherByKey = new HashMap<String, ViewDispatcher>();
        ViewDispatcher prdNormal = addViewWithKey(context, VIEW_KEY);
        ViewDispatcher prdMax = addViewWithKey(context, MAXIMIZED_VIEW_KEY);
        addViewWithKey(context, HELP_KEY);
        addViewWithKey(context, EDIT_KEY);
        if (prdMax == null && prdNormal != null) {
            viewDispatcherByKey.put(MAXIMIZED_VIEW_KEY, prdNormal);
        }
    }

    public PortletRequestDispatcher getDispatcher(String viewKey, PortletRequest request) throws PortletException {
        if (viewKey == null) {
            viewKey = getViewKeyFromMode(request);
        }
        return retrieveViewDispatcher(viewKey);
    }

    public File getViewFile(String viewKey, PortletRequest request) throws PortletException {
        ViewDispatcher dispatcher = (ViewDispatcher) getDispatcher(viewKey, request);
        if (dispatcher != null) {
            return dispatcher.getViewFile();
        }
        return null;
    }

    protected String getViewKeyFromMode(PortletRequest request) throws PortletException {
        PortletMode mode = request.getPortletMode();
        WindowState windowState = request.getWindowState();
        if (!WindowState.MINIMIZED.equals(windowState)) {
            if (PortletMode.VIEW.equals(mode)) {
                if (WindowState.MAXIMIZED.equals(windowState)) {
                    return MAXIMIZED_VIEW_KEY;
                } else {
                    return VIEW_KEY;
                }
            } else if (PortletMode.EDIT.equals(mode)) {
                return EDIT_KEY;
            } else if (PortletMode.HELP.equals(mode)) {
                return HELP_KEY;
            }
        }
        return null;
    }

    protected ViewDispatcher retrieveViewDispatcher(String viewKey) throws PortletException {
        if (viewKey != null) {
            ViewDispatcher viewDispatcher = viewDispatcherByKey.get(viewKey);
            if (viewDispatcher == null) {
                throw new PortletException("No file found for view '" + viewKey + "' in folder : " + getViewFolderPath());
            }
            return viewDispatcher;
        }
        return null;
    }

    protected ViewDispatcher addViewWithKey(PortletContext context, String viewKey) {
        ViewDispatcher result = null;
        String viewPath = resolveViewKey(viewKey);
        File viewFile = new File(context.getRealPath(viewPath));
        if (viewFile.exists()) {
            result = createViewDispatcher(context, viewPath, viewFile);
            viewDispatcherByKey.put(viewKey, result);
        }
        return result;
    }

    protected ViewDispatcher createViewDispatcher(PortletContext context, String viewPath, File viewFile) {
        PortletRequestDispatcher dispatcher = context.getRequestDispatcher(viewPath);
        return new ViewDispatcher(dispatcher, viewFile);
    }

    @Override
    public String resolveViewKey(String viewKey) {
        StringBuilder b = new StringBuilder();
        b.append(super.resolveViewKey(viewKey));
        b.append('.').append(getViewExtension());
        return b.toString();
    }

    protected String getViewExtension() {
        return JSP_EXTENSION;
    }
}
