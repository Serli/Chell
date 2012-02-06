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

package com.serli.chell.framework.form.render;

import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.form.FormField;
import java.util.Set;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class DivFormRenderer extends FormRenderer {

    public static final String INLINE_DISPLAY_STYLE = "display: inline;";

    @Override
    protected String renderFieldsContainer(Form form, String fieldsContent) {
        return renderTag("div", fieldsContent, "chell-container", null, null);
    }

    @Override
    protected String renderRow(Form form, FormField field) {
        StringBuilder b = new StringBuilder();
        b.append(renderTag("span", renderLabel(form, field), "portlet-form-label", null, null));
        b.append(renderTag("span", renderField(form, field), "portlet-form-field", null, null));
        return renderTag("div", b.toString(), "chell-row", null, null);
    }

    @Override
    protected String renderButtonsRow(Form form, String buttonsContainers) {
        return renderTag("div", buttonsContainers, "chell-button-row", null, null);
    }

    @Override
    public String renderMultipleChoiceInput(Form form, FormField field, Set<String> values) {
        String content = renderMultipleChoiceInputContent(form, field, values);
        return renderFieldset(null, content, getFieldCssClass(field), getFieldId(field), INLINE_DISPLAY_STYLE);
    }
}
