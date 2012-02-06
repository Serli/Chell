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

import com.serli.chell.framework.exception.ChellException;
import com.serli.chell.framework.constant.CancelAction;
import com.serli.chell.framework.constant.FormType;
import com.serli.chell.framework.form.FormButton.OnClickBuilder;
import com.serli.chell.framework.form.FormButton.ButtonType;
import com.serli.chell.framework.form.converter.FieldConverter;
import com.serli.chell.framework.form.render.FormRenderer;
import com.serli.chell.framework.loader.DataLoader;
import com.serli.chell.framework.reflect.AnnotationDefaultValues.DefaultValue;
import com.serli.chell.framework.upload.UploadHandler;
import com.serli.chell.framework.util.ClassUtils;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
// TODO : Gestion des types generiques + revoir ClassUtils.getGenericArgumentTypes
@Target(METHOD)
@Retention(RUNTIME)
@Documented
@Inherited
public @interface AnnotationMetaData {

    Type value();

    public static enum Type {
        MESSAGE_TEXT(String.class),
        MESSAGE_KEY(String.class),
        MESSAGE_BUNDLE(String.class),
        HTML_CSS_CLASS(String.class),
        HTML_ID(String.class),
        HTML_COLS(int.class),
        HTML_ROWS(int.class),
        HTML_SIZE(int.class),
        PREFERENCE_NAME(String.class),
        FORM_SEPARATOR_TEXT(String.class),
        FORM_REQUIRED_TEXT(String.class),
        FORM_RENDERER(Class.class, FormRenderer.class),
        FORM_TYPE(FormType.class),
        BUTTON_NAME(String.class),
        BUTTON_TYPE(ButtonType.class),
        BUTTON_PORTLET_ACTION(String.class),
        BUTTON_CANCEL_ACTION(CancelAction.class),
        BUTTON_PARAMETERS(String[].class),
        BUTTON_ON_CLICK(String.class),
        BUTTON_ON_CLICK_BUILDER(Class.class, OnClickBuilder.class),
        BUTTON_ORDER(int.class),
        FIELD_ORDER(int.class),
        FIELD_CONVERTER(Class.class, FieldConverter.class),
        FIELD_DATA_LOADER(Class.class, DataLoader.class),
        FIELD_FILE_UPLOAD_HANDLER(Class.class, UploadHandler.class);

        private Class<?> acceptReturnType;
        private Class<?>[] genericArguments;

        private Type(Class<?> returnType, Class<?>... genericArguments) {
            this.acceptReturnType = returnType;
            this.genericArguments = genericArguments;
        }

        public void validateDefaultValue(DefaultValue defaultValue) {
            Object value = defaultValue.getDefaultValue();
            if (value != null && !ClassUtils.isDescendantOf(value.getClass(), acceptReturnType)) {
                throwBadDefaultValueClass();
            }
        }

        public <T, S, U> void validateMethod(Method method) {
            Class<T> type = (Class<T>) method.getReturnType();
            if (!ClassUtils.isDescendantOf(type, acceptReturnType)) {
                throwBadMethodSignature();
            }
            /*else if (genericArguments.length > 0) {
                java.lang.reflect.Type genericType = method.getGenericReturnType();
                if (genericType instanceof ParameterizedType) {
                    java.lang.reflect.Type[] types = ((ParameterizedType) genericType).getActualTypeArguments();
                    type = (Class<T>) genericType;
                    Class<? super T> returnType = (Class<? super T>) acceptReturnType;
                    ArgumentType[] argTypes = ClassUtils.getGenericArgumentTypes(type, returnType).get();
                    if (argTypes.length != genericArguments.length) {
                        throwBadMethodSignature();
                    } else {
                        for (int i = 0; i < genericArguments.length; i++) {
                            if (!ClassUtils.isDescendantOf(argTypes[i].getType(), genericArguments[i])) {
                                throwBadMethodSignature();
                            }
                        }
                    }
                } else {
                    throwBadMethodSignature();
                }
            }*/
        }

        private void throwBadMethodSignature() {
            StringBuilder b = new StringBuilder();
            b.append("Functions annoted with @");
            b.append(AnnotationMetaData.class.getSimpleName());
            b.append('(').append(getClass().getSimpleName());
            b.append('.').append(name());
            b.append(") must return a '");
            b.append(acceptReturnType.getSimpleName());
            if (genericArguments.length > 0) {
                b.append('<');
                for (Class<?> argumentClass : genericArguments) {
                    b.append(argumentClass.getName()).append(", ");
                }
                b.setLength(b.length() - 2);
                b.append('>');
            }
            b.append("' value.");
            throw new ChellException(b.toString());
        }

        private void throwBadDefaultValueClass() {
            StringBuilder b = new StringBuilder();
            b.append("Default value of type ");
            b.append(getClass().getSimpleName());
            b.append('.').append(name());
            b.append(" must return a '");
            b.append(acceptReturnType.getSimpleName());
            b.append("' value.");
            throw new ChellException(b.toString());
        }
    }
}
