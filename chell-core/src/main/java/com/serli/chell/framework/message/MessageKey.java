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

package com.serli.chell.framework.message;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public interface MessageKey {

    public static final String MESSAGE_PREFERENCES_SAVED = "chell.message.preferences.saved";
    public static final String MESSAGE_EXCEPTION = "chell.message.exception";

    public static final String ACTION_SAVE = "chell.action.save";
    public static final String ACTION_CANCEL = "chell.action.cancel";
    public static final String ACTION_DELETE = "chell.action.delete";
    public static final String ACTION_BUTTON = "chell.action.button";

    public static final String VALIDATION_FAIL = "chell.validation.fail";
    public static final String VALIDATION_DEFAULT = "chell.validation.default";
    public static final String VALIDATION_REQUIRED = "chell.validation.required";
    public static final String VALIDATION_MATCH_WITH = "chell.validation.match.with";
    public static final String VALIDATION_SET_OF = "chell.validation.set.of";
    public static final String VALIDATION_EMAIL = "chell.validation.email";
    public static final String VALIDATION_URL = "chell.validation.url";
    public static final String VALIDATION_IPV4 = "chell.validation.ipv4";
    public static final String VALIDATION_IPV6 = "chell.validation.ipv6";

    public static final String VALIDATION_LENGTH = "chell.validation.length";
    public static final String VALIDATION_LENGTH_MIN = "chell.validation.length.min";
    public static final String VALIDATION_LENGTH_MAX = "chell.validation.length.max";
    public static final String VALIDATION_LENGTH_RANGE = "chell.validation.length.range";

    public static final String VALIDATION_SIZE = "chell.validation.size";
    public static final String VALIDATION_SIZE_MIN = "chell.validation.size.min";
    public static final String VALIDATION_SIZE_MAX = "chell.validation.size.max";
    public static final String VALIDATION_SIZE_RANGE = "chell.validation.size.range";

    public static final String VALIDATION_INTEGER = "chell.validation.integer";
    public static final String VALIDATION_INTEGER_MIN = "chell.validation.integer.min";
    public static final String VALIDATION_INTEGER_MAX = "chell.validation.integer.max";
    public static final String VALIDATION_INTEGER_RANGE = "chell.validation.integer.range";

    public static final String VALIDATION_DECIMAL = "chell.validation.decimal";
    public static final String VALIDATION_DECIMAL_MIN = "chell.validation.decimal.min";
    public static final String VALIDATION_DECIMAL_MAX = "chell.validation.decimal.max";
    public static final String VALIDATION_DECIMAL_RANGE = "chell.validation.decimal.range";

    public static final String VALIDATION_DATE = "chell.validation.date";
    public static final String VALIDATION_DATE_AFTER = "chell.validation.date.after";
    public static final String VALIDATION_DATE_BEFORE = "chell.validation.date.before";
    public static final String VALIDATION_DATE_PERIOD = "chell.validation.date.period";

    public static final String VALIDATION_UPLOAD_BAD_EXTENSION = "chell.validation.upload.bad.extension";
    public static final String VALIDATION_UPLOAD_BAD_MIMETYPE = "chell.validation.upload.bad.mimetype";
    public static final String VALIDATION_UPLOAD_BAD_DIRECTORY = "chell.validation.upload.bad.directory";
    public static final String VALIDATION_UPLOAD_FILE_TOO_LARGE = "chell.validation.upload.file.too.large";
    public static final String VALIDATION_UPLOAD_FILE_EXISTING = "chell.validation.upload.file.existing";
    public static final String VALIDATION_UPLOAD_ERROR = "chell.validation.upload.error";
}
