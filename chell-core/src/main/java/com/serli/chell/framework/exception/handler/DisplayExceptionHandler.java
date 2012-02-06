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
import java.io.IOException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class DisplayExceptionHandler extends ExceptionHandler {

    public static final DisplayExceptionHandler INSTANCE = new DisplayExceptionHandler(Throwable.class);

    public DisplayExceptionHandler(Class<? extends Throwable> matchExceptionClass) {
        super(matchExceptionClass);
    }

    @Override
    public boolean accept(Throwable exception) {
        return true;
    }

    @Override
    public String handleException(Throwable exception, Throwable cause, PhaseType phase, RenderRequest request, RenderResponse response) throws IOException {
        String message = renderError(exception, cause, phase);
        response.getWriter().append(message);
        return null;
    }

    public void handleException(Throwable exception, RenderRequest request, RenderResponse response) throws IOException {
        PhaseType phase = PortletHelper.getPhase();
        Throwable cause = exception;
        while (cause.getCause() != null) {
            cause = cause.getCause();
        }
        handleException(exception, cause, phase, request, response);
    }
}
