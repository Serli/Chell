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

package com.serli.chell.framework.exception.handler;

import com.serli.chell.framework.PortletHelper;
import com.serli.chell.framework.constant.PhaseType;
import com.serli.chell.framework.message.MessageBundle;
import com.serli.chell.framework.message.MessageKey;
import com.serli.chell.framework.message.MessageType;
import com.serli.chell.framework.util.ClassUtils;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public abstract class ExceptionHandler {

    private static final Log LOGGER = LogFactory.getLog(ExceptionHandler.class);

    private Class<? extends Throwable> matchExceptionClass;

    public ExceptionHandler(Class<? extends Throwable> matchExceptionClass) {
        this.matchExceptionClass = matchExceptionClass;
    }

    public boolean accept(Throwable exception) {
        return ClassUtils.isDescendantOf(exception.getClass(), matchExceptionClass);
    }

    protected String renderError(Throwable exception, Throwable cause, PhaseType phase) {
        if (LOGGER.isErrorEnabled()) {
            String portletName = PortletHelper.getPortletContext().getPortletContextName();
            LOGGER.error("Portlet " + portletName + " throws exception during phase " + phase + ":", exception);
        }
        String message = MessageBundle.getMessageFormatted(MessageKey.MESSAGE_EXCEPTION, cause.getClass().getName(), cause.getMessage());
        return MessageType.ERROR.buildMessage(message);
    }

    public abstract String handleException(Throwable exception, Throwable cause, PhaseType phase, RenderRequest request, RenderResponse response) throws Throwable;
}
