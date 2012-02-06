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

import com.serli.chell.framework.constant.Constant;
import com.serli.chell.framework.message.MessageBundle;
import com.serli.chell.framework.message.MessageType;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class MessageTag extends AbstractTag {

    private String key = null;
    private String bundle = null;
    private String text = Constant.EMPTY;
    private String var = null;
    private MessageType type;

    @Override
    public int doStartTag() throws JspException {
        String result;
        if (key != null) {
            if (bundle != null) {
                result = MessageBundle.getBundleMessage(bundle, key);
            } else {
                result = MessageBundle.getMessage(key);
            }
        } else {
            result = text;
        }
        if (type != null) {
            result = type.buildMessage(result);
        }
        if (var == null) {
            print(result);
        } else {
            pageContext.setAttribute(var, result, PageContext.PAGE_SCOPE);
        }
        return SKIP_BODY;
    }

    public String getBundle() {
        return bundle;
    }

    public void setBundle(String bundle) {
        this.bundle = bundle;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setType(String type) {
        this.type = MessageType.find(type);
    }
}
