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
import com.serli.chell.framework.validation.Check;
import com.serli.chell.framework.validation.annotation.SetOf;
import java.util.HashSet;
import java.util.Set;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class SetOfCheck extends Check<String> {

    private Set<String> availableValues;
    private boolean ignoreCase;

    public SetOfCheck(SetOf annotation) {
        super(annotation);
        this.availableValues = new HashSet<String>();
        this.ignoreCase = annotation.ignoreCase();
        if (this.ignoreCase) {
            for (String availableValue : annotation.value()) {
                this.availableValues.add(availableValue.toLowerCase());
            }
        } else {
            for (String availableValue : annotation.value()) {
                this.availableValues.add(availableValue);
            }
        }
    }

    public boolean validate(Form form, String fieldName, String fieldValue) {
        if (ignoreCase) {
            fieldValue = fieldValue.toLowerCase();
        }
        return availableValues.contains(fieldValue);
    }
}
