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

import com.serli.chell.framework.exception.ChellException;
import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.form.FormButton;
import com.serli.chell.framework.form.FormStructure;
import com.serli.chell.framework.form.render.FormRenderer;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class ButtonRenderer extends AbstractFieldAccessor<String> {

    public ButtonRenderer(Form form) {
        super(form);
    }

    @Override
    public String get(Object key) {
        String buttonName = (String) key;
        String value = super.get(buttonName);
        if (value == null) {
            FormStructure structure = form.getStructure();
            FormButton button = structure.getButton(buttonName);
            if (button != null) {
                FormRenderer fr = structure.getRenderer();
                value = fr.renderFormButton(form, button);
                put(buttonName, value);
            } else {
                throw new ChellException("No button '" + buttonName + "' found in form type : " + form.getClass().getName());
            }
        }
        return value;
    }
}
