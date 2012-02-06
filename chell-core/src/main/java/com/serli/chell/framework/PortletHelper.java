package com.serli.chell.framework;

import com.serli.chell.framework.exception.ChellException;
import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.constant.Constant;
import com.serli.chell.framework.constant.PhaseType;
import com.serli.chell.framework.form.FormHelper;
import com.serli.chell.framework.message.MessageType;
import com.serli.chell.framework.reflect.InjectionType;
import com.serli.chell.framework.util.F.Maybe;
import com.serli.chell.framework.util.F.Option;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.MimeResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceResponse;
import javax.portlet.ResourceURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;
import org.w3c.dom.Element;

/**
 * @author Mathieu Ancelin (mathieu.ancelin@serli.com)
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class PortletHelper {

    protected PortletHelper() {
    }

    public static Model getModel() {
        return ChellPortlet.currentModels.get();
    }

    public static PortletConfig getPortletConfig() {
        return ChellPortlet.currentConfig.get();
    }

    public static PortletContext getPortletContext() {
        return ChellPortlet.currentConfig.get().getPortletContext();
    }

    public static PortletRequest getRequest() {
        return ChellPortlet.currentRequests.get();
    }

    public static PortletResponse getResponse() {
        return ChellPortlet.currentResponses.get();
    }

    public static PortletPreferences getPreferences() {
        return ChellPortlet.currentRequests.get().getPreferences();
    }

    public static PortletSession getSession() {
        return ChellPortlet.currentRequests.get().getPortletSession();
    }

    public static PhaseType getPhase() {
        return ChellPortlet.currentPhases.get();
    }

    public static <T extends Form> T getForm(Class<T> formClass) {
        return (T) InjectionType.PORTLET_FORM.getInstance((Class<Form>) formClass);
    }

    public static void setTitle(String title) {
        ChellPortlet.currentTitle.set(title);
    }

    public static Locale getLocale() {
        return getRequest().getLocale();
    }

    public static PortletMode getMode() {
        return getRequest().getPortletMode();
    }

    public static String getNamespace() {
        return getResponse().getNamespace();
    }

    public static String getWindowId() {
        return getRequest().getWindowID();
    }

    public static WindowState getState() {
        return getRequest().getWindowState();
    }

    public static WindowState getState(PortletRequest request) {
        return request.getWindowState();
    }

    public static void setMode(PortletMode mode) throws PortletModeException {
        setMode(getResponse(), mode);
    }

    public static void setMode(PortletResponse response, PortletMode mode) throws PortletModeException {
        if (response instanceof ActionResponse) {
            ((ActionResponse) response).setPortletMode(mode);
        } else {
            throw new PortletModeException("Can not change portlet mode during " + getPhase() + " phase.", mode);
        }
    }

    public static String getActionName(PortletRequest request) {
        return request.getParameter(ActionRequest.ACTION_NAME);
    }

    public static String getActionName() {
        return getRequest().getParameter(ActionRequest.ACTION_NAME);
    }

    public static String getRemoteUser() {
        return getRemoteUser(getRequest());
    }

    public static String getRemoteUser(PortletRequest request) {
        return request.getRemoteUser();
    }

    @SuppressWarnings("unchecked")
    public static <E> E getBean(String bean, Class<E> modelClass) {
        return (E) ChellPortlet.currentModels.get().get(bean);
    }

    public static boolean isMaximized() {
        return isMaximized(getRequest());
    }

    public static boolean isMaximized(PortletRequest request) {
        return request.getWindowState().equals(WindowState.MAXIMIZED);
    }

    public static void setWindowState(WindowState state) throws WindowStateException {
        setWindowState(getResponse(), state);
    }

    public static void setWindowState(PortletResponse response, WindowState state) throws WindowStateException {
        if (response instanceof ActionResponse) {
            ((ActionResponse) response).setWindowState(state);
        } else {
            throw new WindowStateException("Can not change window state during " + getPhase() + " phase.", state);
        }
    }

    public static void addHeaderElement(Element element) {
        addHeaderElement(getResponse(), element);
    }

    public static void addHeaderElement(PortletResponse response, Element element) {
        if (response instanceof RenderResponse) {
            response.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, element);
        } else {
            List<Element> elements = ChellPortlet.currentHeadersElements.get();
            if (elements == null) {
                elements = new ArrayList<Element>();
                ChellPortlet.currentHeadersElements.set(elements);
            }
            elements.add(element);
        }
    }

    public static void resetAllPreferences() {
        PortletPreferences preferences = getPreferences();
        Enumeration<String> names = preferences.getNames();
        try {
            while (names.hasMoreElements()) {
                preferences.reset(names.nextElement());
            }
            preferences.store();
        } catch (Exception ex) {
            throw new ChellException(ex);
        }
    }

    public static TimeZone getTimeZone() {
        return TimeZone.getDefault();
    }

    public static PortletURL getActionURL(PortletResponse response, String actionName) throws PortletException {
        PortletURL actionURL = null;
        if (response instanceof RenderResponse) {
            actionURL = ((RenderResponse) response).createActionURL();
        } else if (response instanceof ResourceResponse) {
            actionURL = ((ResourceResponse) response).createActionURL();
        } else {
            throw new PortletException("Can not create action URL during " + getPhase() + " phase.");
        }
        if (actionName != null) {
            actionURL.setParameter(ActionRequest.ACTION_NAME, actionName);
        }
        return actionURL;
    }

    public static String getActionURL(String actionName) throws PortletException {
        return getActionURL(getResponse(), actionName).toString();
    }

    public static ResourceURL getResourceURL(PortletResponse response) throws PortletException {
        ResourceURL resourceURL = null;
        if (response instanceof RenderResponse) {
            resourceURL = ((RenderResponse) response).createResourceURL();
        } else if (response instanceof ResourceResponse) {
            resourceURL = ((ResourceResponse) response).createResourceURL();
        } else {
            throw new PortletException("Can not create resource URL during " + getPhase() + " phase.");
        }
        return resourceURL;
    }

    public static String getResourceURL() throws PortletException {
        return getResourceURL(getResponse()).toString();
    }

    public static PortletURL getRenderURL(PortletResponse response, PortletMode mode, WindowState state) throws PortletException {
        PortletURL renderURL = null;
        if (response instanceof RenderResponse) {
            renderURL = ((RenderResponse) response).createRenderURL();
        } else if (response instanceof ResourceResponse) {
            renderURL = ((ResourceResponse) response).createRenderURL();
        } else {
            throw new PortletException("Can not create render URL during " + getPhase() + " phase.");
        }
        if (mode != null) {
            renderURL.setPortletMode(mode);
        }
        if (state != null) {
            renderURL.setWindowState(state);
        }
        return renderURL;
    }

    public static String getRenderURL(PortletMode mode, WindowState state) throws PortletException {
        return getRenderURL(getResponse(), mode, state).toString();
    }

    public static String getMaximizeRenderURL() throws PortletException {
        return getRenderURL(getResponse(), null, WindowState.MAXIMIZED).toString();
    }

    public static String getMinimizeRenderURL() throws PortletException {
        return getRenderURL(getResponse(), null, WindowState.MINIMIZED).toString();
    }

    public static String getNormalRenderURL() throws PortletException {
        return getRenderURL(getResponse(), null, WindowState.NORMAL).toString();
    }

    public static String getMaximizeRenderURL(PortletMode mode) throws PortletException {
        return getRenderURL(getResponse(), mode, WindowState.MAXIMIZED).toString();
    }

    public static String getMinimizeRenderURL(PortletMode mode) throws PortletException {
        return getRenderURL(getResponse(), mode, WindowState.MINIMIZED).toString();
    }

    public static String getNormalRenderURL(PortletMode mode) throws PortletException {
        return getRenderURL(getResponse(), mode, WindowState.NORMAL).toString();
    }

    public static String getMaximizeRenderURL(PortletResponse response) throws PortletException {
        return getRenderURL(response, null, WindowState.MAXIMIZED).toString();
    }

    public static String getMinimizeRenderURL(PortletResponse response) throws PortletException {
        return getRenderURL(response, null, WindowState.MINIMIZED).toString();
    }

    public static String getNormalRenderURL(PortletResponse response) throws PortletException {
        return getRenderURL(response, null, WindowState.NORMAL).toString();
    }

    public static String getMaximizeRenderURL(PortletResponse response, PortletMode mode) throws PortletException {
        return getRenderURL(response, mode, WindowState.MAXIMIZED).toString();
    }

    public static String getMinimizeRenderURL(PortletResponse response, PortletMode mode) throws PortletException {
        return getRenderURL(response, mode, WindowState.MINIMIZED).toString();
    }

    public static String getNormalRenderURL(PortletResponse response, PortletMode mode) throws PortletException {
        return getRenderURL(response, mode, WindowState.NORMAL).toString();
    }

    public static void cancel(Form form) {
        FormHelper.removeStoredForm(Scope.session(), form);
    }

    public static void cancel(Class<? extends Form> formClass) {
        FormHelper.removeStoredForm(Scope.session(), formClass);
    }

    public static interface ParameterizedModel {
        ParameterizedModel msg(MessageType type, String message);
        ParameterizedModel attr(String name, Object value);
        ParameterizedModel rem(String name);
    }

    public static class Model extends HashMap<String, Object> implements ParameterizedModel {

        private static final long serialVersionUID = -3256476494742474699L;
        private static volatile String messageVariableName = Constant.MODEL_MESSAGE;

        // TODO : A Enlever. Ne pas imposer un style de prog fonctionnel dans le framework -> a faire avec @ModelAttribute
        public Option<Object> forKey(String key) {
            return new Maybe<Object>(super.get(key));
        }

        // TODO : A Enlever. Ne pas imposer un style de prog fonctionnel dans le framework -> a faire avec @ModelAttribute
        @SuppressWarnings("unchecked")
        public <T> Option<T> forKey(String key, Class<T> type) {
            return new Maybe<T>((T) super.get(key));
        }

        public ParameterizedModel attr(String name, Object value) {
            put(name, value);
            return this;
        }

        public ParameterizedModel rem(String name) {
            remove(name);
            return this;
        }

        public ParameterizedModel msg(MessageType type, String message) {
            put(messageVariableName, type.buildMessage(message));
            return this;
        }

        public static void setMessageVariableName(String variableName) {
            messageVariableName = variableName;
        }

        public static String getMessageVariableName() {
            return messageVariableName;
        }
    }
}
