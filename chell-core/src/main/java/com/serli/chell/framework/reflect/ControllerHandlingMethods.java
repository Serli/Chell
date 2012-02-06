package com.serli.chell.framework.reflect;

import com.serli.chell.framework.annotation.OnAction;
import com.serli.chell.framework.annotation.OnCancel;
import com.serli.chell.framework.annotation.OnEvent;
import com.serli.chell.framework.annotation.OnRender;
import com.serli.chell.framework.annotation.OnRenderHeader;
import com.serli.chell.framework.annotation.OnResource;
import com.serli.chell.framework.constant.Constant;
import com.serli.chell.framework.constant.RenderMode;
import com.serli.chell.framework.form.Form;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.portlet.Event;
import javax.portlet.PortletMode;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class ControllerHandlingMethods {

    private List<Invocation> allEventHandlingMethods = new ArrayList<Invocation>();
    private List<Invocation> allActionHandlingMethods = new ArrayList<Invocation>();
    private List<Invocation> allRenderHandlingMethods = new ArrayList<Invocation>();
    private List<Invocation> allHeaderHandlingMethods = new ArrayList<Invocation>();
    private List<Invocation> allResourceHandlingMethods = new ArrayList<Invocation>();
    
    private Map<String, List<Invocation>> eventHandlingMethods = new HashMap<String, List<Invocation>>();
    private Map<String, List<Invocation>> actionHandlingMethods = new HashMap<String, List<Invocation>>();
    private Map<String, List<Invocation>> renderHandlingMethods = new HashMap<String, List<Invocation>>();
    private Map<String, List<Invocation>> headerHandlingMethods = new HashMap<String, List<Invocation>>();
    private Map<String, List<Invocation>> cancelHandlingMethods = new HashMap<String, List<Invocation>>();

    public void cacheAnnotatedMethods(Class<?> controllerType, InjectionResolver ir) {
        Class<? extends Form> formClass;
        RenderMode renderMode;
        String value;
        do {
            for (Method m : controllerType.getDeclaredMethods()) {
                m.setAccessible(true);

                // Render
                if (m.isAnnotationPresent(OnRender.class)) {
                    renderMode = m.getAnnotation(OnRender.class).value();
                    if (renderMode.equals(RenderMode.ALL)) {
                        addMethodToList(allRenderHandlingMethods, m, ir);
                    } else {
                        value = renderMode.toString().toLowerCase();
                        addMethodToMap(renderHandlingMethods, value, m, ir);
                    }
                }

                // Action
                if (m.isAnnotationPresent(OnAction.class)) {
                    value = m.getAnnotation(OnAction.class).value();
                    if (value.equals(Constant.WILDCARD)) {
                        addMethodToList(allActionHandlingMethods, m, ir);
                    } else {
                        addMethodToMap(actionHandlingMethods, value, m, ir);
                    }
                }

                // Event
                if (m.isAnnotationPresent(OnEvent.class)) {
                    OnEvent annotation = m.getAnnotation(OnEvent.class);
                    value = annotation.name();
                    if (!value.equals(Constant.WILDCARD)) {
                        addMethodToMap(eventHandlingMethods, value, m, ir);
                    } else {
                        value = annotation.qname();
                        if (!value.equals(Constant.WILDCARD)) {
                            addMethodToMap(eventHandlingMethods, value, m, ir);
                        } else {
                            addMethodToList(allEventHandlingMethods, m, ir);
                        }
                    }
                }

                // Header
                if (m.isAnnotationPresent(OnRenderHeader.class)) {
                    renderMode = m.getAnnotation(OnRenderHeader.class).value();
                    if (renderMode.equals(RenderMode.ALL)) {
                        addMethodToList(allHeaderHandlingMethods, m, ir);
                    } else {
                        value = renderMode.toString().toLowerCase();
                        addMethodToMap(headerHandlingMethods, value, m, ir);
                    }
                }

                // Resource
                if (m.isAnnotationPresent(OnResource.class)) {
                    addMethodToList(allResourceHandlingMethods, m, ir);
                }

                // Cancel
                if (m.isAnnotationPresent(OnCancel.class)) {
                    formClass = m.getAnnotation(OnCancel.class).value();
                    addMethodToMap(cancelHandlingMethods, formClass.getName(), m, ir);
                }
            }
            controllerType = controllerType.getSuperclass();
        } while (!controllerType.equals(Object.class));
    }

    public List<Invocation> getActionHandlingMethods(String actionName) {
        List<Invocation> specific = actionHandlingMethods.get(actionName);
        return getHandlingMethods(allActionHandlingMethods, specific);
    }
    
    public List<Invocation> getRenderHandlingMethods(PortletMode renderMode) {
        List<Invocation> specific = renderHandlingMethods.get(renderMode.toString().toLowerCase());
        return getHandlingMethods(allRenderHandlingMethods, specific);
    }

    public List<Invocation> getHeaderHandlingMethods(PortletMode renderMode) {
        List<Invocation> specific = headerHandlingMethods.get(renderMode.toString().toLowerCase());
        return getHandlingMethods(allHeaderHandlingMethods, specific);
    }

    protected List<Invocation> getHandlingMethods(List<Invocation> allInvocations, List<Invocation> specificInvocations) {
        int size = allInvocations.size() + (specificInvocations == null ? 0 : specificInvocations.size());
        List<Invocation> result = new ArrayList<Invocation>(size);
        result.addAll(allInvocations);
        if (specificInvocations != null) {
            result.addAll(specificInvocations);
        }
        return result;
    }

    public List<Invocation> getEventHandlingMethods(Event event) {
        String eventName = event.getQName().toString();
        List<Invocation> specific = eventHandlingMethods.get(eventName);
        if (specific == null) {
            eventName = event.getName();
            specific = eventHandlingMethods.get(eventName);
            if (specific == null) {
                specific = new ArrayList<Invocation>(0);
            }
        }
        int size = allEventHandlingMethods.size() + specific.size();
        List<Invocation> result = new ArrayList<Invocation>(size);
        result.addAll(allEventHandlingMethods);
        result.addAll(specific);
        return result;
    }

    public List<Invocation> getResourceHandlingMethods() {
        return allResourceHandlingMethods;
    }

    public List<Invocation> getCancelHandlingMethods(Class<? extends Form> formClass) {
        return cancelHandlingMethods.get(formClass.getName());
    }

    private static Invocation getMethodInvocation(Method method, InjectionResolver ir) {
        for (Class<?> type : method.getParameterTypes()) {
            ir.resolveType(type);
        }
        return new Invocation(method);
    }

    private static void addMethodToList(List<Invocation> list, Method method, InjectionResolver ir) {
        list.add(getMethodInvocation(method, ir));
    }

    private static void addMethodToMap(Map<String, List<Invocation>> map, String key, Method method, InjectionResolver ir) {
        List<Invocation> listMethods = map.get(key);
        if (listMethods == null) {
            listMethods = new ArrayList<Invocation>();
            map.put(key, listMethods);
        }
        listMethods.add(getMethodInvocation(method, ir));
    }
}
