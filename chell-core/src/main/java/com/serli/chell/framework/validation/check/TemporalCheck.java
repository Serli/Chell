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

import com.serli.chell.framework.exception.ChellException;
import com.serli.chell.framework.PortletHelper;
import com.serli.chell.framework.constant.Constant;
import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.message.MessageBundle;
import com.serli.chell.framework.message.MessageKey;
import com.serli.chell.framework.validation.MultipleConstraint;
import com.serli.chell.framework.validation.annotation.Temporal;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class TemporalCheck extends MultipleConstraint<String> {

    private static final String PREFIX_KEY = "chell.date.pattern.";
    private static final Set<Character> SUPPORTED_PATTERN_CHARS = initSupportedPatternChars();

    private Map<Locale, String> datePatternByLocales = new ConcurrentHashMap<Locale, String>();

    private SimpleDateFormat dateFormat;
    private DateElement after;
    private DateElement before;

    public TemporalCheck(Temporal annotation, SimpleDateFormat format) {
        super(annotation);
        dateFormat = format;
        after = new DateElement(annotation.after());
        before = new DateElement(annotation.before());
        Date afterDate = after.getDate();
        Date beforeDate = before.getDate();
        addTest(new KeyTest(MessageKey.VALIDATION_DATE) {
            public boolean validate(Form form, String fieldName, String fieldValue) {
                try {
                    form.get(fieldName);
                    return true;
                } catch (Exception ex) {
                    return false;
                }
            }
        });
        if (afterDate != null && beforeDate != null) {
            addTest(new KeyTest(MessageKey.VALIDATION_DATE_PERIOD) {
                public boolean validate(Form form, String fieldName, String fieldValue) {
                    Date value = (Date) form.get(fieldName);
                    return (value.after(after.getDate()) && value.before(before.getDate()));
                }
            });
        } else if (afterDate != null) {
            addTest(new KeyTest(MessageKey.VALIDATION_DATE_AFTER) {
                public boolean validate(Form form, String fieldName, String fieldValue) {
                    return (((Date) form.get(fieldName)).after(after.getDate()));
                }
            });
        } else if (beforeDate != null) {
            addTest(new KeyTest(MessageKey.VALIDATION_DATE_BEFORE) {
                public boolean validate(Form form, String fieldName, String fieldValue) {
                    return (((Date) form.get(fieldName)).before(before.getDate()));
                }
            });
        }
    }

    private String getLocalizedDatePattern() {
        Locale locale = PortletHelper.getLocale();
        String result = datePatternByLocales.get(locale);
        if (result == null) {
            StringBuilder b = new StringBuilder();
            String datePattern = dateFormat.toPattern();
            int length = datePattern.length();
            char character;
            for (int i = 0; i < length; i++) {
                character = datePattern.charAt(i);
                if (SUPPORTED_PATTERN_CHARS.contains(character)) {
                    b.append(MessageBundle.getMessage(locale, PREFIX_KEY + character));
                } else {
                    b.append(character);
                }
            }
            result = b.toString();
            datePatternByLocales.put(locale, result);
        }
        return result;
    }

    protected String formatDefaultErrorMessage(String pattern, String fieldLabel) {
        return MessageFormat.format(pattern, fieldLabel, getLocalizedDatePattern(), after.format(), before.format());
    }

    private static Set<Character> initSupportedPatternChars() {
        Set<Character> result = new HashSet<Character>();
        result.add('y');
        result.add('M');
        result.add('d');
        result.add('H');
        result.add('K');
        result.add('m');
        result.add('s');
        result.add('S');
        return result;
    }

    private class DateElement {

        private Date date;
        private boolean isToday;

        public DateElement(String dateStr) {
            date = null;
            isToday = false;
            if (dateStr.length() > 0) {
                if (dateStr.equals(Constant.TODAY)) {
                    isToday = true;
                } else {
                    try {
                        date = dateFormat.parse(dateStr);
                    } catch (ParseException ex) {
                        throw new ChellException("The format of date " + dateStr + " must have pattern " + dateFormat.toPattern());
                    }
                }
            }
        }

        public Date getDate() {
            if (isToday) {
                return new Date();
            } else {
                return date;
            }
        }

        public String format() {
            Date currentDate = getDate();
            if (currentDate != null) {
                return dateFormat.format(currentDate);
            }
            return null;
        }
    }
}
