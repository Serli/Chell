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
import com.serli.chell.framework.validation.annotation.LongNumber;
import java.text.MessageFormat;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class LongNumberCheck extends MultipleConstraint<String> {

    private long minValue;
    private long maxValue;

    public LongNumberCheck(LongNumber annotation) {
        super(annotation);
        minValue = annotation.min();
        maxValue = annotation.max();
        addTest(new KeyTest(MessageKey.VALIDATION_INTEGER) {
            public boolean validate(Form form, String fieldName, String fieldValue) {
                try {
                    form.get(fieldName);
                    return true;
                } catch (NumberFormatException ex) {
                    return false;
                }
            }
        });
        if (minValue != Long.MIN_VALUE && maxValue != Long.MAX_VALUE) {
            addTest(new KeyTest(MessageKey.VALIDATION_INTEGER_RANGE) {
                public boolean validate(Form form, String fieldName, String fieldValue) {
                    long value = (Long) form.get(fieldName);
                    return (value >= minValue && value <= maxValue);
                }
            });
        } else if (minValue != Long.MIN_VALUE) {
            addTest(new KeyTest(MessageKey.VALIDATION_INTEGER_MIN) {
                public boolean validate(Form form, String fieldName, String fieldValue) {
                    return ((Long) form.get(fieldName) >= minValue);
                }
            });
        } else if (maxValue != Long.MAX_VALUE) {
            addTest(new KeyTest(MessageKey.VALIDATION_INTEGER_MAX) {
                public boolean validate(Form form, String fieldName, String fieldValue) {
                    return ((Long) form.get(fieldName) <= maxValue);
                }
            });
        }
    }

    protected String formatDefaultErrorMessage(String pattern, String fieldLabel) {
        return MessageFormat.format(pattern, fieldLabel, String.valueOf(minValue), String.valueOf(maxValue));
    }
}
