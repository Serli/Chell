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

package com.serli.chell.framework.form.render.el;

import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.form.FormField;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class UploadFilenameAccessor extends AbstractFieldAccessor<String> {

    public UploadFilenameAccessor(Form form) {
        super(form);
    }

    @Override
    public String get(Object key) {
        String fieldName = (String) key;
        String displayFileName = super.get(fieldName);
        if (displayFileName == null && !containsKey(fieldName)) {
            FormField formField = form.getField(fieldName);
            if (formField.isUploadField()) {
                displayFileName = formField.getUploadHandler().onDisplay(form.get(fieldName));
                super.put(fieldName, displayFileName);
            } else {
                super.put(fieldName, null);
            }
        }
        return displayFileName;
    }

    @Override
    public String put(String key, String convertedValue) {
        String fieldName = (String) key;
        FormField formField = form.getField(fieldName);
        formField.setConvertedValue(form, convertedValue);
        return super.put(fieldName, convertedValue);
    }
}
