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

package model;

import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.form.annotation.HtmlInputText;
import com.serli.chell.framework.validation.annotation.Length;
import com.serli.chell.framework.validation.annotation.Required;
import com.serli.chell.framework.validation.annotation.SetOf;

/**
 * Representation of the preference form.
 * This form validates the input values.
 */
public class PrefsForm extends Form {

    @Required
    @Length(min = 2, max = 3)
    @SetOf({ "on", "off"})
    @HtmlInputText(key = "field.upper.mode")
    private String upper = "off";

    public PrefsForm() {
        super("pf");
    }

    public String getUpper() {
        return upper;
    }

    public void setUpper(String upper) {
        this.upper = upper;
    }
}