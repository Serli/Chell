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

import com.serli.chell.framework.form.Form;
import java.lang.annotation.Annotation;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public abstract class CrossConstraint extends Check<String> {

    private String otherFieldName;

    public CrossConstraint(Annotation annotation) {
        super(annotation);
        otherFieldName = ""; // TODO : Ameliorer la lecture des annotation
    }

    public boolean validate(Form form, String fieldName, String fieldValue) {
        String fieldValue2 = form.getFieldValue(fieldName);
        return crossValidate(form, fieldName, otherFieldName, fieldValue, fieldValue2);
    }

    public abstract boolean crossValidate(Form form,
                                          String fieldName1, String fieldName2,
                                          String fieldValue1, String fieldValue2);
}
