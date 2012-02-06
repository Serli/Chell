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
public enum MessageType {
    INFO("portlet-msg-info"),
    ERROR("portlet-msg-error"),
    STATUS("portlet-msg-status"),
    ALERT("portlet-msg-alert"),
    SUCCESS("portlet-msg-success");

    private String cssClass;

    private MessageType(String cssClass) {
        this.cssClass = cssClass;
    }

    public String getCssClass() {
        return cssClass;
    }

    public String buildMessage(String message) {
        StringBuilder b = new StringBuilder();
        b.append("<div class=\"");
        b.append(cssClass).append("\">");
        b.append(message).append("</div>");
        return b.toString();
    }

    public static MessageType find(String name) {
        for (MessageType mt : values()) {
            if (mt.name().equalsIgnoreCase(name)) {
                return mt;
            }
        }
        return null;
    }
}
