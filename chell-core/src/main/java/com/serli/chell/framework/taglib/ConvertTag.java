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

package com.serli.chell.framework.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class ConvertTag extends AbstractFieldTag {

    private String var;

    @Override
    public int doStartTag() throws JspException {
        Object convertedValue = getFormInstance().get(field);
        if (convertedValue != null) {
            if (var == null) {
                print(convertedValue.toString());
            } else {
                pageContext.setAttribute(var, convertedValue, PageContext.PAGE_SCOPE);
            }
        }
        return SKIP_BODY;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }
}
