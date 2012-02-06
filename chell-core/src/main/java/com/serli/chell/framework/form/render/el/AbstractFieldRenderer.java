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

import com.serli.chell.framework.constant.Constant;
import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.form.FormField;
import com.serli.chell.framework.form.FormStructure;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public abstract class AbstractFieldRenderer extends AbstractFieldAccessor<String> {

    protected FormStructure structure;

    public AbstractFieldRenderer(Form form) {
        super(form);
        this.structure = form.getStructure();
    }

    @Override
    public String get(Object key) {
        String fieldName = (String) key;
        String value = super.get(fieldName);
        if (value == null) {
            FormField formField = form.getField(fieldName);
            value = render(formField, fieldName);
            if (value == null) {
                value = Constant.EMPTY;
            }
            put(fieldName, value);
        }
        return value;
    }

    protected abstract String render(FormField formField, String fieldName);
}
