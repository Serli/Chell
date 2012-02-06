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
