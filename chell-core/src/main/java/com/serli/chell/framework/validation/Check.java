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


package com.serli.chell.framework.validation;

import com.serli.chell.framework.PortletHelper;
import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.form.FormField;
import com.serli.chell.framework.message.MessageAnnotationHelper;
import java.lang.annotation.Annotation;
import java.util.Locale;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public abstract class Check<T> extends Test<T> implements Constraint<T> {

    public Check(Annotation annotation) {
        super(annotation == null ? null : MessageAnnotationHelper.getMessage(annotation));
    }

    public void validateField(Form form, FormField field, T fieldValue) throws ValidationException {
        String fieldName = field.getName();
        if (!validate(form, fieldName, fieldValue)) {
            Locale locale = PortletHelper.getLocale();
            String errorMsg = getErrorMessage(locale, field.getLabel(locale));
            form.addError(fieldName, errorMsg);
            throw new ValidationException();
        }
    }
}
