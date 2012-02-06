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
