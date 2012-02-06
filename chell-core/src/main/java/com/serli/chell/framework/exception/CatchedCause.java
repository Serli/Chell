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

package com.serli.chell.framework.exception;

import com.serli.chell.framework.PortletHelper;
import com.serli.chell.framework.constant.PhaseType;
import com.serli.chell.framework.exception.handler.ExceptionHandler;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class CatchedCause {

    private Throwable cause;
    private Throwable exception;
    private ExceptionHandler handler;
    private PhaseType phase;

    public CatchedCause(Throwable exception, Throwable cause, ExceptionHandler handler) {
        this.cause = cause;
        this.exception = exception;
        this.handler = handler;
        this.phase = PortletHelper.getPhase();
    }

    public Throwable getException() {
        return exception;
    }

    public Throwable getCause() {
        return cause;
    }

    public ExceptionHandler getHandler() {
        return handler;
    }

    public PhaseType getPhase() {
        return phase;
    }

    public String handleException(RenderRequest request, RenderResponse response) throws Throwable {
        return handler.handleException(exception, cause, phase, request, response);
    }
}
