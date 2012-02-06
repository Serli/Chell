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
import com.serli.chell.framework.message.MessageAnnotationHelper;
import com.serli.chell.framework.validation.Check;
import com.serli.chell.framework.validation.annotation.Required;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class RequiredTabCheck extends Check<String[]> {

    public static final RequiredTabCheck DEFAULT_INSTANCE = new RequiredTabCheck();

    private RequiredTabCheck() {
        super(null);
        setMessage(MessageAnnotationHelper.getDefaultMessage(Required.class));
    }

    public RequiredTabCheck(Required annotation) {
        super(annotation);
    }

    public boolean validate(Form form, String fieldName, String[] fieldValue) {
        return (fieldValue != null && fieldValue.length > 0);
    }
}
