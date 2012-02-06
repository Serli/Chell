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
