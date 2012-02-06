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
