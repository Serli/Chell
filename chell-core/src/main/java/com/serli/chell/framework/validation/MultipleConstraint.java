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
import com.serli.chell.framework.message.Message;
import com.serli.chell.framework.message.MessageAnnotationHelper;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public abstract class MultipleConstraint<T> implements Constraint<T> {

    private List<Test<T>> testList = new ArrayList<Test<T>>();
    private Message message;
    private boolean messageOverriden;

    public MultipleConstraint(Annotation annotation) {
        this.message = MessageAnnotationHelper.getMessage(annotation);
        this.messageOverriden = !this.message.isEmpty();
    }

    protected void addTest(Test<T> test) {
        testList.add(test);
    }

    public void validateField(Form form, FormField field, T fieldValue) throws ValidationException {
        String messagePattern, errorMsg, fieldName = field.getName();
        Locale locale = PortletHelper.getLocale();
        for (Test<T> test : testList) {
            if (!test.validate(form, fieldName, fieldValue)) {
                if (messageOverriden) {
                    messagePattern = Test.getMessagePattern(locale, message);
                    errorMsg = formatDefaultErrorMessage(messagePattern, field.getLabel(locale));
                } else {
                    errorMsg = test.getErrorMessage(locale, field.getLabel(locale));
                }
                form.addError(fieldName, errorMsg);
                throw new ValidationException();
            }
        }
    }

    protected abstract String formatDefaultErrorMessage(String pattern, String fieldLabel);

    protected abstract class KeyTest extends Test<String> {

        public KeyTest(String errorKey) {
            super(Message.withKey(errorKey));
        }

        public KeyTest(String errorKey, String bundleName) {
            super(Message.withKey(errorKey, bundleName));
        }

        @Override
        protected String formatErrorMessage(String pattern, String fieldLabel) {
            return formatDefaultErrorMessage(pattern, fieldLabel);
        }
    }

    protected abstract class KeyMultipleTest extends Test<String[]> {

        public KeyMultipleTest(String errorKey) {
            super(Message.withKey(errorKey));
        }

        public KeyMultipleTest(String errorKey, String bundleName) {
            super(Message.withKey(errorKey, bundleName));
        }

        @Override
        protected String formatErrorMessage(String pattern, String fieldLabel) {
            return formatDefaultErrorMessage(pattern, fieldLabel);
        }
    }
}
