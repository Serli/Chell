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

package com.serli.chell.framework.constant;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public interface Constant {

    public static final String[] EMPTY_TAB = {};
    public static final String EMPTY = "";
    public static final String WILDCARD = "*";
    public static final String NAMESPACE_ID_PREFIX = "#";
    public static final String DEFAULT_ENCODING = "UTF-8";
    public static final int DEFAULT_ORDER = Integer.MAX_VALUE;
    public static final int AUTO = -1;
    public static final int UNSPECIFIED = -2;

    // Name of the portlet CSS class
    public static final String PORTLET_CLASS = "chell-portlet";

    // Names of model elements
    public static final String MODEL_MESSAGE = "messageArea";

    // Names of specific elements stored in session scope by the framework
    public static final String KEY_FORMS = "chell-forms";

    // Names of specific elements stored in scopes by the framework
    public static final String KEY_STORE = "chell-store";

    // Name of specific framework actions
    public static final String ACTION_CANCEL = "chell-cancel-action";

    // Name of specific framework parameters
    public static final String PARAMETER_FORM_CLASS = "chell-form-class";
    public static final String PARAMETER_BUTTON_CLICKED = "chell-button";
    public static final String PARAMETER_RESOURCE = "chell-resource";

    // Constants for HTML renderers
    public static final String HTML_SPACE = "&nbsp;";
    public static final String HTML_FIELD_SEPARATOR = " :&nbsp;";
    public static final String HTML_REQUIRED = "<span class=\"required\"> *</span>";
    public static final String HTML_CSS_FORM_CLASS = "PortletGeneratedForm";

    // Constants for dates
    public static final String TODAY = "TODAY";
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

    // Constants for view resolvers
    public static final String RESOLVER_BASE_VIEW_PATH = "/views";
}
