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

package com.serli.chell.framework.util;

import com.serli.chell.framework.exception.ChellException;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
// TODO : Gerer les return type / parameters
public class ClassUtils {

    protected ClassUtils() {
    }

    public static <T> T newInstance(Class<T> objectClass) {
        try {
            Constructor<T> constructor = objectClass.getConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (Exception ex) {
            throw new ChellException("Error during singleton instantiation. Class " + objectClass.getName() +
                                     " must have a default constructor.", ex);
        }
    }

    /**
     * Determine if a specified class is, or is a descendant of another class type.
     * The function looks for superclasses and interfaces.
     * @param type The class to test
     * @param superClassType The super class
     * @return True / False
     */
    public static boolean isDescendantOf(Class<?> type, Class<?> superClassType) {
        if (type != null) {
            if (type.equals(superClassType)) {
                return true;
            } else {
                for (Class<?> itf : type.getInterfaces()) {
                    if (isDescendantOf(itf, superClassType)) {
                        return true;
                    }
                }
                return isDescendantOf(type.getSuperclass(), superClassType);
            }
        }
        return false;
    }

    /**
     * Get types of a generics arguments in super class declaration.
     * @param currentType The class on which determine generic argument type
     * @param targetSuperclass A super class or interface of 'currentType' containing generics arguments
     * @return Arguments types
     */
    public static <T> ArgumentTypes getGenericArgumentTypes(Class<T> currentType, Class<? super T> targetSuperclass) {
        return getGenericArgumentTypes(currentType, currentType, targetSuperclass);
    }

    /**
     * Get the type of a generic argument in super class declaration.
     * @param currentType The class on which determine generic argument type
     * @param targetSuperclass A super class or interface of 'currentType' containing generics arguments
     * @param argumentIndex The index of the generic argument in the super class / interface
     * @return The argument type
     */
    public static <T> ArgumentType getGenericArgumentType(Class<T> currentType, Class<? super T> targetSuperclass, int argumentIndex) {
        ArgumentTypes ats = getGenericArgumentTypes(currentType, currentType, targetSuperclass);
        if (ats != null) {
            return ats.get(argumentIndex);
        }
        return null;
    }

    /**
     * Get the class of a generic argument in super class declaration.
     * @param currentType The class on which determine generic argument type
     * @param targetSuperclass A super class or interface of 'currentType' containing generics arguments
     * @param argumentIndex The index of the generic argument in the super class / interface
     * @return The argument class
     */
    public static <T> Class<?> getGenericArgumentClass(Class<T> currentType, Class<? super T> targetSuperclass, int argumentIndex) {
        ArgumentTypes ats = getGenericArgumentTypes(currentType, currentType, targetSuperclass);
        if (ats != null) {
            return ats.get(argumentIndex).getType();
        }
        return null;
    }

    private static ArgumentTypes getGenericArgumentTypes(Class<?> type, Type genericType, Class<?> superclass) {
        if (type != null && genericType != null) {
            if (type.equals(superclass)) {
                if (genericType instanceof ParameterizedType) {
                    return new ArgumentTypes((ParameterizedType) genericType);
                } else {
                    return new ArgumentTypes(type.getTypeParameters());
                }
            }
            Class<?>[] itfs = type.getInterfaces();
            Type[] genericItfs = type.getGenericInterfaces();
            ArgumentTypes ats;
            for (int i = 0; i < itfs.length; i++) {
                if ((ats = getGenericArgumentTypes(itfs[i], genericItfs[i], superclass)) != null) {
                    return ats.mergeVariables(type.getTypeParameters(), genericType);
                }
            }
            if ((ats = getGenericArgumentTypes(type.getSuperclass(), type.getGenericSuperclass(), superclass)) != null) {
                return ats.mergeVariables(type.getTypeParameters(), genericType);
            }
        }
        return null;
    }

    public static class ArgumentTypes {

        private ArgumentType[] argumentTypes;

        private ArgumentTypes(ParameterizedType result) {
            argumentTypes = buildArgumentTypes(result);
        }

        private ArgumentTypes(TypeVariable[] variableDefinitions) {
            argumentTypes = new ArgumentType[variableDefinitions.length];
            for (int i = 0; i < variableDefinitions.length; i++) {
                argumentTypes[i] = ArgumentType.NO_GENERIC_ARGUMENT;
            }
        }

        private static ArgumentType[] buildArgumentTypes(ParameterizedType type) {
            Type[] argTypes = type.getActualTypeArguments();
            ArgumentType[] result = new ArgumentType[argTypes.length];
            for (int i = 0; i < argTypes.length; i++) {
                result[i] = ArgumentType.create(argTypes[i]);
            }
            return result;
        }

        private ArgumentTypes mergeVariables(TypeVariable[] variableDefinitions, Type type) {
            if (variableDefinitions.length > 0 && type instanceof ParameterizedType) {
                ArgumentType[] argTypes = buildArgumentTypes((ParameterizedType) type);
                Map<String, Integer> positionByVariable = new HashMap<String, Integer>(variableDefinitions.length);
                for (int i = 0; i < variableDefinitions.length; i++) {
                    positionByVariable.put(variableDefinitions[i].getName(), new Integer(i));
                }
                ArgumentType argType, newArgType;
                for (int i = 0; i < argumentTypes.length; i++) {
                    argType = argumentTypes[i];
                    if (argType.name != null) {
                        newArgType = argTypes[positionByVariable.get(argType.name)];
                        newArgType.arrayDimension += argType.arrayDimension;
                        argumentTypes[i] = newArgType;
                    }
                }
            }
            return this;
        }

        /**
         * Get the type of the argument.
         * @param argumentIndex The index of the generic argument in declaration
         * @return The argument type
         */
        public ArgumentType get(int argumentIndex) {
            return argumentTypes[argumentIndex];
        }

        /**
         * Get types of all arguments in declaration.
         * @return Arguments types
         */
        public ArgumentType[] get() {
            return argumentTypes;
        }
    }

    public static class ArgumentType {

        private static ArgumentType NO_GENERIC_ARGUMENT = new ArgumentType(Object.class, 0, null, null);
        private static final String WILDCARD_VARIABLE_NAME = "?";

        private Class<?> type;
        private String name;
        private Boolean upperBound;
        private int arrayDimension;

        private ArgumentType(Class<?> type, int arrayDimension, String name, Boolean upperBound) {
            this.type = type;
            this.arrayDimension = arrayDimension;
            this.name = name;
            this.upperBound = upperBound;
            if (Object.class.equals(type)) {
                this.upperBound = null;
            }
        }

        private static ArgumentType create(Type type, int dimension, String name, Boolean isUpperBound) {
            if (type instanceof Class) {
                return new ArgumentType((Class) type, dimension, name, isUpperBound);
            } else if (type instanceof TypeVariable) {
                TypeVariable typeVariable = (TypeVariable) type;
                Type[] upperBounds = typeVariable.getBounds();
                if (upperBounds.length > 0) {
                    return create(upperBounds[0], dimension, typeVariable.getName(), Boolean.TRUE);
                } else {
                    return new ArgumentType(Object.class, dimension, typeVariable.getName(), isUpperBound);
                }
            } else if (type instanceof GenericArrayType) {
                type = ((GenericArrayType) type).getGenericComponentType();
                return create(type, dimension + 1, name, isUpperBound);
            } else if (type instanceof WildcardType) {
                WildcardType wt = (WildcardType) type;
                Type[] types = wt.getLowerBounds();
                if (types.length > 0) {
                    return create(types[0], dimension, WILDCARD_VARIABLE_NAME, Boolean.FALSE);
                }
                types = wt.getUpperBounds();
                if (types.length > 0) {
                    return create(types[0], dimension, WILDCARD_VARIABLE_NAME, Boolean.TRUE);
                }
            }
            return null;
        }

        private static ArgumentType create(Type type) {
            return create(type, 0, null, null);
        }

        /**
         * Determine if the argument is an array.
         * @return True / False
         */
        public boolean isArray() {
            return (arrayDimension > 0);
        }

        /**
         * Determine if the dimension of the argument.
         * @return The dimension of the argument, 0 if the argument is not an array
         */
        public int getArrayDimension() {
            return arrayDimension;
        }

        /**
         * Determine if the argument is an not resolved variable.
         * @return True / False
         */
        public boolean isVariable() {
            return (name != null);
        }

        /**
         * Determine if the argument is a wildcard.
         * @return True / False
         */
        public boolean isWildcardVariable() {
            return (WILDCARD_VARIABLE_NAME.equals(name));
        }

        /**
         * Determine if the argument is a bounded.
         * @return True / False
         */
        public boolean isBounded() {
            return (upperBound != null);
        }

        /**
         * Determine if the argument is a upper bounded.
         * @return True / False
         */
        public boolean isUpperBounded() {
            return (upperBound != null && upperBound.booleanValue());
        }

        /**
         * Determine if the argument is a lower bounded.
         * @return True / False
         */
        public boolean isLowerBounded() {
            return (upperBound != null && !upperBound.booleanValue());
        }

        /**
         * Get the type of the argument.
         * @return The type of the argument
         */
        public Class<?> getType() {
            return type;
        }

        /**
         * Get the name of the not resolved variable.
         * @return The name of the not resolved variable or null.
         */
        public String getVariableName() {
            return name;
        }
    }
}
