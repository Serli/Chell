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


package com.serli.chell.framework.validation.check;

import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.form.FormField;
import com.serli.chell.framework.validation.Constraint;
import com.serli.chell.framework.validation.ValidationException;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public final class NotRequiredTabCheck implements Constraint<String[]> {

    public static final NotRequiredTabCheck DEFAULT_INSTANCE = new NotRequiredTabCheck();

    private NotRequiredTabCheck() {
    }

    public void validateField(Form form, FormField field, String[] fieldValues) throws ValidationException {
        if (fieldValues == null || fieldValues.length == 0) {
            throw new ValidationException();
        }
    }

    public boolean isTabCheck() {
        return true;
    }
}
