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
import com.serli.chell.framework.message.MessageType;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class ErrorFieldRenderer extends AbstractFieldRenderer {

    private String all = null;

    public ErrorFieldRenderer(Form form) {
        super(form);
    }

    protected String render(FormField formField, String fieldName) {
        String errorMsg = form.getError().get(fieldName);
        if (errorMsg != null) {
            return structure.getRenderer().renderError(form, errorMsg);
        }
        return Constant.EMPTY;
    }

    @Override
    public boolean isEmpty() {
        return form.getError().isEmpty();
    }

    @Override
    public int size() {
        return form.getError().size();
    }

    @Override
    public void clear() {
        super.clear();
        all = null;
    }

    @Override
    public String toString() {
        if (all == null) {
            if (form.hasErrors()) {
                String errorSection = structure.getRenderer().renderErrorSection(form);
                all = MessageType.ERROR.buildMessage(errorSection);
            } else {
                all = Constant.EMPTY;
            }
        }
        return all;
    }
}
