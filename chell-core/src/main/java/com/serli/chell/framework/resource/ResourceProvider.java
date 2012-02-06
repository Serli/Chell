package com.serli.chell.framework.resource;

import com.serli.chell.framework.resource.Resource.Cacheability;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class ResourceProvider {

    private static Map<String, ResourceElement> resourcesCache = new HashMap<String, ResourceElement>();

    private static ResourceElement retrieveResource(String path) {
        ResourceElement resource = resourcesCache.get(path);
        if (resource == null) {
            resource = ResourceElement.load(path);
            resourcesCache.put(path, resource);
        }
        return resource;
    }

    private static ResourceElement retrieveResource(String path, Cacheability cacheability) {
        ResourceElement resource = retrieveResource(path);
        resource.setCacheability(cacheability);
        return resource;
    }

    private static void retrieveResources(Set<ResourceElement> result, Resource.List annotations) {
        if (annotations != null) {
            for (Resource annotation : annotations.value()) {
                result.add(retrieveResource(annotation.path(), annotation.cacheability()));
            }
        }
    }

    public static synchronized ResourceElement getResource(String path) {
        return retrieveResource(path);
    }

    public static synchronized ResourceElement getResource(Resource annotation) {
        if (annotation != null) {
            return retrieveResource(annotation.path(), annotation.cacheability());
        }
        return null;
    }

    public static synchronized Set<ResourceElement> getResources(Resource.List annotations) {
        Set<ResourceElement> result = new LinkedHashSet<ResourceElement>();
        retrieveResources(result, annotations);
        return result;
    }

    private static synchronized void loadResources(Set<ResourceElement> result, Resource.List annotations, Resource annotation) {
        retrieveResources(result, annotations);
        if (annotation != null) {
            result.add(retrieveResource(annotation.path(), annotation.cacheability()));
        }
    }

    public static void loadResourcesFromAnnotation(Set<ResourceElement> result, Annotation annotation) {
        Class<? extends Annotation> annotationType = annotation.annotationType();
        Resource resource = annotationType.getAnnotation(Resource.class);
        Resource.List resources = annotationType.getAnnotation(Resource.List.class);
        loadResources(result, resources, resource);
    }

    public static void loadResourcesFromClass(Set<ResourceElement> result, Class<?> type) {
        Resource resource = type.getAnnotation(Resource.class);
        Resource.List resources = type.getAnnotation(Resource.List.class);
        ResourceProvider.loadResources(result, resources, resource);
    }
}
