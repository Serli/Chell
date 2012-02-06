
package com.serli.chell.framework.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class Invocation {
    
    private Method method;
    private Annotation[][] parametersQualifiers;
    private Class<?>[] parametersTypes;

    public Invocation(Method method) {
        this.method = method;
        this.parametersQualifiers = method.getParameterAnnotations();
        this.parametersTypes = method.getParameterTypes();
    }

    public Method getMethod() {
        return method;
    }

    public Annotation[] getParametersQualifiers(int index) {
        return parametersQualifiers[index];
    }

    public Class<?> getParametersTypes(int index) {
        return parametersTypes[index];
    }

    public int countParameters() {
        return parametersTypes.length;
    }
}
