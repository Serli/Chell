package com.serli.chell.framework;

import com.serli.chell.framework.exception.ChellException;
import com.serli.chell.framework.reflect.Invocation;
import com.serli.chell.framework.reflect.ControllerHandlingMethods;
import com.serli.chell.framework.PortletHelper.Model;
import com.serli.chell.framework.annotation.Catcher;
import com.serli.chell.framework.annotation.Controller;
import com.serli.chell.framework.constant.CancelAction;
import com.serli.chell.framework.constant.Constant;
import com.serli.chell.framework.constant.PhaseType;
import com.serli.chell.framework.exception.CatchedCause;
import com.serli.chell.framework.exception.handler.DisplayExceptionHandler;
import com.serli.chell.framework.exception.handler.ExceptionHandler;
import com.serli.chell.framework.reflect.InjectionType;
import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.form.FormButton;
import com.serli.chell.framework.form.FormHelper;
import com.serli.chell.framework.form.FormStructure;
import com.serli.chell.framework.form.FormStructureRegistry;
import com.serli.chell.framework.reflect.AnnotationMetaData.Type;
import com.serli.chell.framework.reflect.InjectionResolver;
import com.serli.chell.framework.resolver.JspViewResolver;
import com.serli.chell.framework.resolver.ViewResolver;
import com.serli.chell.framework.resource.ResourceElement;
import com.serli.chell.framework.resource.ResourceProvider;
import com.serli.chell.framework.upload.FileUploadStatus;
import com.serli.chell.framework.util.ClassUtils;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.MimeResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import org.w3c.dom.Element;

/**
 * @author Mathieu Ancelin (mathieu.ancelin@serli.com)
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class ChellPortlet extends GenericPortlet {

    public static final String PARAM_CONTROLLER_PACKAGE = "controller-package";
    public static final String PARAM_CONTROLLER_CLASS = "controller-class";
    public static final String DEFAULT_CONTROLLER_PACKAGE = "controller.";

    protected static ThreadLocal<PortletRequest> currentRequests = new ThreadLocal<PortletRequest>();
    protected static ThreadLocal<PortletResponse> currentResponses = new ThreadLocal<PortletResponse>();
    protected static ThreadLocal<PhaseType> currentPhases = new ThreadLocal<PhaseType>();
    protected static ThreadLocal<PortletConfig> currentConfig = new ThreadLocal<PortletConfig>();
    protected static PortletThreadLocal<Model> currentModels = new PortletThreadLocal<Model>();
    protected static PortletThreadLocal<String> currentTitle = new PortletThreadLocal<String>();
    protected static PortletThreadLocal<List<Element>> currentHeadersElements = new PortletThreadLocal<List<Element>>();
    protected static PortletThreadLocal<CatchedCause> currentCatchedCauses = new PortletThreadLocal<CatchedCause>();

    protected Object controller;
    protected List<ExceptionHandler> exceptionHandlers;
    protected ControllerHandlingMethods handlingMethods;
    protected InjectionResolver injectionResolver;
    protected ViewResolver viewResolver;

    @Override
    public final void init() throws PortletException {
        PortletConfig config = getPortletConfig();
        String controllerClassName = config.getInitParameter(PARAM_CONTROLLER_CLASS);
        if (controllerClassName == null || controllerClassName.length() == 0) {
            controllerClassName = getControllerPackage(config) + config.getPortletName();
        }
        try {
            Class<?> controllerType = getClass().getClassLoader().loadClass(controllerClassName);
            Controller annotation = controllerType.getAnnotation(Controller.class);
            viewResolver = instantiateViewResolver(annotation);
            handlingMethods = new ControllerHandlingMethods();
            injectionResolver = initInjectionResolver();
            controller = getInstance(controllerType);
            exceptionHandlers = instantiateExceptionHandlers(annotation);
            initPortlet(config);
        } catch (ClassNotFoundException ex) {
            throw new ChellException("Can not find the controller class of the portlet.", ex);
        }
    }

    @Override
    public final void init(PortletConfig config) throws PortletException {
        super.init(config);
        handlingMethods.cacheAnnotatedMethods(controller.getClass(), injectionResolver);
    }

    protected InjectionResolver initInjectionResolver() {
        InjectionResolver resolver = new InjectionResolver();
        resolver.setInjectionType(PortletRequest.class, InjectionType.PORTLET_REQUEST);
        resolver.setInjectionType(ActionRequest.class, InjectionType.ACTION_REQUEST);
        resolver.setInjectionType(RenderRequest.class, InjectionType.RENDER_REQUEST);
        resolver.setInjectionType(ResourceRequest.class, InjectionType.RESOURCE_REQUEST);
        resolver.setInjectionType(EventRequest.class, InjectionType.EVENT_REQUEST);
        resolver.setInjectionType(PortletResponse.class, InjectionType.PORTLET_RESPONSE);
        resolver.setInjectionType(ActionResponse.class, InjectionType.ACTION_RESPONSE);
        resolver.setInjectionType(RenderResponse.class, InjectionType.RENDER_RESPONSE);
        resolver.setInjectionType(ResourceResponse.class, InjectionType.RESOURCE_RESPONSE);
        resolver.setInjectionType(EventResponse.class, InjectionType.EVENT_RESPONSE);
        resolver.setInjectionType(PortletSession.class, InjectionType.PORTLET_SESSION);
        resolver.setInjectionType(PortletPreferences.class, InjectionType.PORTLET_PREFERENCES);
        resolver.setInjectionType(PortletConfig.class, InjectionType.PORTLET_CONFIG);
        resolver.setInjectionType(PortletContext.class, InjectionType.PORTLET_CONTEXT);
        resolver.setInjectionType(Locale.class, InjectionType.PORTLET_LOCALE);
        resolver.setInjectionType(Model.class, InjectionType.PORTLET_MODEL);
        resolver.setInjectionType(Form.class, InjectionType.PORTLET_FORM);
        return resolver;
    }

    @Override
    public final void destroy() {
        super.destroy();
        controller = null;
        handlingMethods = null;
        viewResolver = null;
        destroyPortlet();
    }

    @Override
    public void processAction(ActionRequest request, ActionResponse response) throws PortletException, IOException {
        try {
            initRequest(request, response, PhaseType.ACTION);
            invokeActionMethods(request, response);
        } catch (Throwable ex) {
            catchException(ex);
        }
    }

    @Override
    public void processEvent(EventRequest request, EventResponse response) throws PortletException, IOException {
        if (currentCatchedCauses.get() == null) {
            try {
                initRequest(request, response, PhaseType.EVENT);
                invokeEventMethods(request);
            } catch (Throwable ex) {
                catchException(ex);
            }
        }
    }

    // TODO : Ameliorer la gestion (routage avec @RequestParam)
    @Override
    public void serveResource(ResourceRequest request, ResourceResponse response) throws PortletException, IOException {
        initRequest(request, response, PhaseType.RESOURCE);
        String resourcePath = request.getParameter(Constant.PARAMETER_RESOURCE);
        if (resourcePath != null && resourcePath.length() > 0) {
            serveInternalResource(response, resourcePath);
        } else {
            invokeResourceMethods(request);
            propageModelToView(request);
        }
        endRequest(request, response);
    }

    protected void serveInternalResource(ResourceResponse response, String path) throws IOException {
        OutputStream os = null;
        response.setCharacterEncoding(Constant.DEFAULT_ENCODING);
        try {
            ResourceElement resource = ResourceProvider.getResource(path);
            int contentLength = resource.getSize();
            response.setContentLength(contentLength);
            response.setContentType(resource.getMimeType());
            if (contentLength > 0) {
                os = response.getPortletOutputStream();
                os.write(resource.getContent());
            }
        } catch (ChellException ex) {
            response.setContentLength(0);
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }

    @Override
    public void render(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        initRequest(request, response, PhaseType.RENDER);
        super.render(request, response);
        endRequest(request, response);
    }

    protected void doRender(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        PrintWriter writer = null;
        CatchedCause exception = null;
        try {
            writer = response.getWriter();
            writePortletHeader(writer);
            String viewKey = null;
            exception = currentCatchedCauses.get();
            if (exception == null) {
                try {
                    viewKey = invokeRenderMethods(request);
                } catch (Throwable ex) {
                    exception = catchException(ex);
                }
            }
            if (exception != null) {
                currentModels.set(new Model());
                currentHeadersElements.remove();
                currentCatchedCauses.remove();
                currentTitle.remove();
                viewKey = exception.handleException(request, response);
            }
            propageModelToView(request);
            propageResourcesToView(request, response);
            if (exception == null || viewKey != null) {
                viewResolver.include(viewKey, request, response);
            }
        } catch (Throwable ex) {
            DisplayExceptionHandler.INSTANCE.handleException(ex, request, response);
        } finally {
            if (writer != null) {
                writePortletFooter(writer);
                writer.close();
            }
        }
    }

    @Override
    protected void doHeaders(RenderRequest request, RenderResponse response) {
        try {
            invokeHeaderMethods(request);
            addPendingHeaderElements(response);
        } catch (Exception ex) {
            catchException(ex);
        }
    }

    @Override
    public void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        doRender(request, response);
    }

    @Override
    public void doHelp(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        doRender(request, response);
    }

    @Override
    public void doEdit(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        doRender(request, response);
    }

    protected String getControllerPackage(PortletConfig config) {
        String controllerPackage = config.getInitParameter(PARAM_CONTROLLER_PACKAGE);
        if (controllerPackage == null) {
            return DEFAULT_CONTROLLER_PACKAGE;
        } else if (!controllerPackage.endsWith(".")) {
            return controllerPackage + ".";
        }
        return controllerPackage;
    }

    protected CatchedCause catchException(Throwable exception) {
        ExceptionHandler handlers;
        CatchedCause catchedCause;
        for (int i = exceptionHandlers.size() - 1; i >= 0; i--) {
            handlers = exceptionHandlers.get(i);
            catchedCause = catchExceptionCause(exception, exception, handlers);
            if (catchedCause != null) {
                currentCatchedCauses.set(catchedCause);
                return catchedCause;
            }
        }
        return null;
    }

    private CatchedCause catchExceptionCause(Throwable exception, Throwable cause, ExceptionHandler handlers) {
        CatchedCause catched = null;
        if (cause != null) {
            catched = catchExceptionCause(exception, cause.getCause(), handlers);
            if (catched == null && handlers.accept(cause)) {
                catched = new CatchedCause(exception, cause, handlers);
            }
        }
        return catched;
    }

    protected void initRequest(PortletRequest request, PortletResponse response, PhaseType phase) throws PortletException {
        currentPhases.set(phase);
        currentRequests.set(request);
        currentResponses.set(response);
        currentConfig.set(getPortletConfig());
        Model model = currentModels.get();
        if (model == null) {
            model = new Model();
            currentModels.set(model);
            model.putAll(getManagedBeans(model));
            startRequest(request, response);
        }
    }

    protected void propageModelToView(PortletRequest request) throws PortletException {
        Model model = currentModels.get();
        for (String name : model.keySet()) {
            request.setAttribute(name, model.get(name));
        }
    }

    protected void propageResourcesToView(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        Model model = currentModels.get();
        Enumeration<String> attributeNames = request.getAttributeNames();
        String attributeName;
        Object attributeValue;
        Set<ResourceElement> resources = new LinkedHashSet<ResourceElement>();
        while (attributeNames.hasMoreElements()) {
            attributeName = attributeNames.nextElement();
            attributeValue = request.getAttribute(attributeName);
            addFormResources(resources, attributeValue);
        }
        for (Object value : model.values()) {
            addFormResources(resources, value);
        }
        PrintWriter writer = response.getWriter();
        for (ResourceElement resource : resources) {
            writer.append(resource.toHtml(request, response));
        }
    }

    protected void addFormResources(Set<ResourceElement> resources, Object object) {
        if (object instanceof Form) {
            FormStructure structure = ((Form) object).getStructure();
            resources.addAll(structure.getResources());
        }
    }

    protected void addPendingHeaderElements(RenderResponse response) {
        List<Element> elements = currentHeadersElements.get();
        if (elements != null && elements.size() > 0) {
            for (Element element : elements) {
                response.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, element);
            }
            elements.clear();
        }
    }

    protected void endRequest(PortletRequest request, PortletResponse response) {
        stopRequest(request, response);
        Scope.endRequest();
        String title = currentTitle.get();
        if (title != null && response instanceof RenderResponse) {
            ((RenderResponse) response).setTitle(title);
            currentTitle.remove();
        }
        currentCatchedCauses.remove();
        currentModels.remove();
        currentConfig.remove();
        currentPhases.remove();
        currentRequests.remove();
        currentResponses.remove();
    }

    protected <T> void addInjectionType(Class<T> injectionClass, InjectionType<T> injectType) {
        injectionResolver.setInjectionType(injectionClass, injectType);
    }

    protected String invokeRenderMethods(RenderRequest request) throws PortletException {
        PortletMode currentMode = request.getPortletMode();
        List<Invocation> methods = handlingMethods.getRenderHandlingMethods(currentMode);
        String viewKey = null;
        Object result;
        for (Invocation invocation : methods) {
            result = invokeAnnotedMethod(invocation, controller);
            if (result != null && result instanceof String) {
                viewKey = (String) result;
            }
        }
        return viewKey;
    }

    protected void invokeHeaderMethods(RenderRequest request) {
        PortletMode currentMode = request.getPortletMode();
        List<Invocation> methods = handlingMethods.getHeaderHandlingMethods(currentMode);
        invokeAnnotedMethods(methods);
    }

    protected void invokeActionMethods(ActionRequest request, ActionResponse response) {
        String currentAction = PortletHelper.getActionName(request);
        if (Constant.ACTION_CANCEL.equals(currentAction)) {
            processCancelAction(request, response);
        } else {
            List<Invocation> methods = handlingMethods.getActionHandlingMethods(currentAction);
            invokeAnnotedMethods(methods);
        }
    }

    protected void invokeEventMethods(EventRequest request) {
        invokeAnnotedMethods(handlingMethods.getEventHandlingMethods(request.getEvent()));
    }

    protected void invokeResourceMethods(ResourceRequest request) {
        invokeAnnotedMethods(handlingMethods.getResourceHandlingMethods());
    }

    protected void invokeAnnotedMethods(List<Invocation> methods) {
        if (methods != null) {
            for (Invocation invocation : methods) {
                invokeAnnotedMethod(invocation, controller);
            }
        }
    }

    protected Object invokeAnnotedMethod(Invocation invocation, Object controller) {
        int size = invocation.countParameters();
        Object[] parameters = new Object[size];
        for (int i = 0; i < size; i++) {
            parameters[i] = getInstance(invocation.getParametersTypes(i), invocation.getParametersQualifiers(i));
        }
        try {
            return invocation.getMethod().invoke(controller, parameters);
        } catch (Exception ex) {
            throw new ChellException(ex);
        }
    }

    protected CancelAction getButtonCancelAction(Class<? extends Form> formClass, ActionRequest request) {
        FormStructure structure = FormStructureRegistry.getFormStructure(formClass);
        String buttonName = request.getParameter(Constant.PARAMETER_BUTTON_CLICKED);
        FormButton button = structure.getButton(buttonName);
        if (button != null) {
            return (CancelAction) button.getDefinition(Type.BUTTON_CANCEL_ACTION);
        }
        return null;
    }

    protected void processCancelAction(ActionRequest request, ActionResponse response) {
        String formClassStr = request.getParameter(Constant.PARAMETER_FORM_CLASS);
        try {
            Class<? extends Form> formClass = (Class<? extends Form>) Class.forName(formClassStr);
            CancelAction cancelAction = getButtonCancelAction(formClass, request);
            if (cancelAction != null) {
                Form form = FormHelper.removeStoredForm(Scope.session(), formClass);
                if (form != null) {
                    FileUploadStatus.discardCanceledFormUploadedItems(form);
                    switch (cancelAction) {
                        case SWITCH_TO_VIEW_MODE:
                            response.setPortletMode(PortletMode.VIEW); break;
                        case SWITCH_TO_EDIT_MODE:
                            response.setPortletMode(PortletMode.EDIT); break;
                        case SWITCH_TO_HELP_MODE:
                            response.setPortletMode(PortletMode.HELP); break;
                        case ANNOTATION_DELEGATE:
                            List<Invocation> invocations = handlingMethods.getCancelHandlingMethods(formClass);
                            if (invocations != null && invocations.size() > 0) {
                                invokeAnnotedMethods(invocations);
                            } else if (!PortletMode.VIEW.equals(request.getPortletMode())) {
                                response.setPortletMode(PortletMode.VIEW);
                            }
                            break;
                        default: break;
                    }
                }
            }
        } catch (Exception ex) {
            throw new ChellException("Can not cancel form of class " + formClassStr, ex);
        }
    }

    protected void writePortletHeader(PrintWriter writer) {
        writer.append("<div class=\"").append(Constant.PORTLET_CLASS).append("\">");
    }

    protected void writePortletFooter(PrintWriter writer) {
        writer.append("</div>");
    }

    protected <T> T getInstance(Class<T> type, Annotation... qualifiers) {
        T result;
        InjectionType<T> it = injectionResolver.resolveType(type);
        if (it != null) {
            result = it.getInstance(type, qualifiers);
        } else {
            try {
                result = findInstance(type, qualifiers);
            } catch (Exception ex) {
                result = null;
            }
            if (result == null) {
                result = ClassUtils.newInstance(type);
            }
        }
        return result;
    }

    protected ViewResolver instantiateViewResolver(Controller annotation) {
        Class<? extends ViewResolver> viewResolverClass = JspViewResolver.class;
        String baseViewPath = Constant.RESOLVER_BASE_VIEW_PATH;
        String viewFolderName = getPortletName();
        if (annotation != null) {
            viewResolverClass = annotation.viewResolver();
            String value = annotation.baseViewPath();
            if (value.length() > 0) {
                baseViewPath = value;
            }
            value = annotation.viewFolderName();
            if (value.length() > 0) {
                viewFolderName = value;
            }
        }
        try {
            Constructor<? extends ViewResolver> c = viewResolverClass.getConstructor(PortletContext.class, String.class, String.class);
            return c.newInstance(getPortletContext(), baseViewPath, viewFolderName);
        } catch (NoSuchMethodException ex) {
            throw new ChellException("ViewResolver class (" + viewResolverClass.getName() + ") has not a (PortletContext, String, String) constructor");
        } catch (SecurityException ex) {
            throw new ChellException(ex);
        } catch (InstantiationException ex) {
            throw new ChellException("Can not instantiate ViewResolver class (" + viewResolverClass.getName() + ")");
        } catch (IllegalAccessException ex) {
            throw new ChellException("Can not instantiate ViewResolver class (" + viewResolverClass.getName() + ")");
        } catch (InvocationTargetException ex) {
            throw new ChellException("Can not instantiate ViewResolver class (" + viewResolverClass.getName() + ")", ex);
        }
    }

    protected List<ExceptionHandler> instantiateExceptionHandlers(Controller annotation) {
        List<ExceptionHandler> result;
        if (annotation != null) {
            Catcher catcher;
            Catcher[] catchers = annotation.exceptionCatchers();
            result = new ArrayList<ExceptionHandler>(catchers.length + 1);
            result.add(DisplayExceptionHandler.INSTANCE);
            for (int i = catchers.length - 1; i >= 0; i--) {
                catcher = catchers[i];
                result.add(instantiateExceptionHandlers(catcher.exception(), catcher.handler()));
            }
        } else {
            result = new ArrayList<ExceptionHandler>(1);
            result.add(DisplayExceptionHandler.INSTANCE);
        }
        return result;
    }

    protected ExceptionHandler instantiateExceptionHandlers(Class<? extends Throwable> exceptionClass, Class<? extends ExceptionHandler> handlerClass) {
        try {
            Constructor<? extends ExceptionHandler> c = handlerClass.getConstructor(Class.class);
            return c.newInstance(exceptionClass);
        } catch (NoSuchMethodException ex) {
            throw new ChellException("ExceptionHandler class (" + handlerClass.getName() + ") has not a (Class<? extends Throwable>) constructor");
        } catch (SecurityException ex) {
            throw new ChellException(ex);
        } catch (InstantiationException ex) {
            throw new ChellException("Can not instantiate ExceptionHandler class (" + handlerClass.getName() + ")");
        } catch (IllegalAccessException ex) {
            throw new ChellException("Can not instantiate ExceptionHandler class (" + handlerClass.getName() + ")");
        } catch (InvocationTargetException ex) {
            throw new ChellException("Can not instantiate ExceptionHandler class (" + handlerClass.getName() + ")", ex);
        }
    }

    /* -------------------------------------------------------------
     *    Methods which can be overriden by integration portlets
     * ------------------------------------------------------------- */

    /**
     * Override this method for specific initialisation of portlet.
     */
    protected void initPortlet(PortletConfig config) {
    }

    /**
     * Override this method for specific destruction of portlet.
     */
    protected void destroyPortlet() {
    }

    /**
     * Override this method for specific initialisation of request.
     */
    protected void startRequest(PortletRequest request, PortletResponse response) {
    }

    /**
     * Override this method for specific destruction of request.
     */
    protected void stopRequest(PortletRequest request, PortletResponse response) {
    }

    /**
     * Override this method for integration with third party DI frameworks.
     */
    protected <T> T findInstance(Class<T> type, Annotation... qualifiers) throws Exception {
        return null;
    }

    /**
     * Override this method for integration with third party DI frameworks.
     * The DI managed returned will be accessible from EL.
     */
    protected Map<String, Object> getManagedBeans(Model model) {
        return Collections.emptyMap();
    }
}
