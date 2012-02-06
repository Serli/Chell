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
import com.serli.chell.framework.message.MessageKey;
import com.serli.chell.framework.validation.MultipleConstraint;
import java.text.MessageFormat;

import com.serli.chell.framework.validation.annotation.Size;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class SizeCheck extends MultipleConstraint<String[]> {

    private int minSize;
    private int maxSize;

    public SizeCheck(Size annotation) {
        super(annotation);
        int value = annotation.value();
        minSize = annotation.min();
        maxSize = annotation.max();
        if (value > 0) {
            minSize = value;
            maxSize = value;
            addTest(new KeyMultipleTest(MessageKey.VALIDATION_SIZE) {
                public boolean validate(Form form, String fieldName, String[] fieldValue) {
                    return (fieldValue.length == minSize);
                }
            });
        } else if (minSize > 0 && maxSize > 0) {
            addTest(new KeyMultipleTest(MessageKey.VALIDATION_SIZE_RANGE) {
                public boolean validate(Form form, String fieldName, String[] fieldValue) {
                    return (fieldValue.length >= minSize && fieldValue.length <= maxSize);
                }
            });
        } else if (minSize > 0) {
            addTest(new KeyMultipleTest(MessageKey.VALIDATION_SIZE_MIN) {
                public boolean validate(Form form, String fieldName, String[] fieldValue) {
                    return (fieldValue.length >= minSize);
                }
            });
        } else if (maxSize > 0) {
            addTest(new KeyMultipleTest(MessageKey.VALIDATION_SIZE_MAX) {
                public boolean validate(Form form, String fieldName, String[] fieldValue) {
                    return (fieldValue.length <= maxSize);
                }
            });
        }
    }

    protected String formatDefaultErrorMessage(String pattern, String fieldLabel) {
        return MessageFormat.format(pattern, fieldLabel, minSize, maxSize);
    }
}
