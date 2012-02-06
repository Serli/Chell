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

package exception;

import com.serli.chell.framework.constant.PhaseType;
import com.serli.chell.framework.exception.handler.ExceptionHandler;
import com.serli.chell.framework.message.MessageType;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class SampleExceptionHandler extends ExceptionHandler {

    public SampleExceptionHandler(Class<? extends Throwable> matchExceptionClass) {
        super(matchExceptionClass);
    }

    @Override
    public String handleException(Throwable exception, Throwable cause, PhaseType phase, RenderRequest request, RenderResponse response) throws Throwable {
        String message = MessageType.ALERT.buildMessage(cause.getMessage());
        response.getWriter().append(message);
        return null;
    }
}
