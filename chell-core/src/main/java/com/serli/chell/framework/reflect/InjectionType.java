
package com.serli.chell.framework.reflect;

import com.serli.chell.framework.exception.ChellException;
import com.serli.chell.framework.PortletHelper;
import com.serli.chell.framework.PortletHelper.Model;
import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.form.FormHelper;
import java.lang.annotation.Annotation;
import java.util.Locale;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public abstract class InjectionType<T> {

    /**
     * Find an instance of a specified type.
     * @return The instance. Must not return null.
     */
    public abstract T getInstance(Class<T> type, Annotation... qualifiers);

    public static final InjectionType<ActionRequest> ACTION_REQUEST = new RequestInjectionType<ActionRequest>();
    public static final InjectionType<RenderRequest> RENDER_REQUEST = new RequestInjectionType<RenderRequest>();
    public static final InjectionType<ResourceRequest> RESOURCE_REQUEST = new RequestInjectionType<ResourceRequest>();
    public static final InjectionType<EventRequest> EVENT_REQUEST = new RequestInjectionType<EventRequest>();
    public static final InjectionType<ActionResponse> ACTION_RESPONSE = new ResponseInjectionType<ActionResponse>();
    public static final InjectionType<RenderResponse> RENDER_RESPONSE = new ResponseInjectionType<RenderResponse>();
    public static final InjectionType<ResourceResponse> RESOURCE_RESPONSE = new ResponseInjectionType<ResourceResponse>();
    public static final InjectionType<EventResponse> EVENT_RESPONSE = new ResponseInjectionType<EventResponse>();

    public static final InjectionType<PortletRequest> PORTLET_REQUEST = new InjectionType<PortletRequest>() {
        public PortletRequest getInstance(Class<PortletRequest> type, Annotation... qualifiers) {
            return PortletHelper.getRequest();
        }
    };

    public static final InjectionType<PortletResponse> PORTLET_RESPONSE = new InjectionType<PortletResponse>() {
        public PortletResponse getInstance(Class<PortletResponse> type, Annotation... qualifiers) {
            return PortletHelper.getResponse();
        }
    };

    public static final InjectionType<PortletSession> PORTLET_SESSION = new InjectionType<PortletSession>() {
        public PortletSession getInstance(Class<PortletSession> type, Annotation... qualifiers) {
            return PortletHelper.getSession();
        }
    };

    public static final InjectionType<PortletPreferences> PORTLET_PREFERENCES = new InjectionType<PortletPreferences>() {
        public PortletPreferences getInstance(Class<PortletPreferences> type, Annotation... qualifiers) {
            return PortletHelper.getPreferences();
        }
    };

    public static final InjectionType<Locale> PORTLET_LOCALE = new InjectionType<Locale>() {
        public Locale getInstance(Class<Locale> type, Annotation... qualifiers) {
            return PortletHelper.getRequest().getLocale();
        }
    };

    public static final InjectionType<Model> PORTLET_MODEL = new InjectionType<Model>() {
        public Model getInstance(Class<Model> type, Annotation... qualifiers) {
            return PortletHelper.getModel();
        }
    };

    public static final InjectionType<PortletContext> PORTLET_CONTEXT = new InjectionType<PortletContext>() {
        public PortletContext getInstance(Class<PortletContext> type, Annotation... qualifiers) {
            return PortletHelper.getPortletContext();
        }
    };

    public static final InjectionType<PortletConfig> PORTLET_CONFIG = new InjectionType<PortletConfig>() {
        public PortletConfig getInstance(Class<PortletConfig> type, Annotation... qualifiers) {
            return PortletHelper.getPortletConfig();
        }
    };

    public static final InjectionType<Form> PORTLET_FORM = new InjectionType<Form>() {
        public Form getInstance(Class<Form> type, Annotation... qualifiers) {
            return FormHelper.get(type);
        }
    };

    private static class RequestInjectionType<T extends PortletRequest> extends InjectionType<T> {
        public T getInstance(Class<T> type, Annotation... qualifiers) {
            PortletRequest request = PortletHelper.getRequest();
            if (type.isInstance(request)) {
                return type.cast(request);
            } else {
                throw new ChellException("Can not inject object of class '" + type.getName() + "' during " + PortletHelper.getPhase() + " phase.");
            }
        }
    }

    private static class ResponseInjectionType<T extends PortletResponse> extends InjectionType<T> {
        public T getInstance(Class<T> type, Annotation... qualifiers) {
            PortletResponse response = PortletHelper.getResponse();
            if (type.isInstance(response)) {
                return type.cast(response);
            } else {
                throw new ChellException("Can not inject object of class '" + type.getName() + "' during " + PortletHelper.getPhase() + " phase.");
            }
        }
    }
}
