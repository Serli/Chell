package com.serli.chell.framework.exception.handler;

import com.serli.chell.framework.constant.PhaseType;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class RethrowExceptionHandler extends ExceptionHandler {

    public RethrowExceptionHandler(Class<? extends Throwable> matchExceptionClass) {
        super(matchExceptionClass);
    }

    @Override
    public boolean accept(Throwable exception) {
        return true;
    }

    @Override
    public String handleException(Throwable exception, Throwable cause, PhaseType phase, RenderRequest request, RenderResponse response) throws Throwable {
        throw exception;
    }
}
