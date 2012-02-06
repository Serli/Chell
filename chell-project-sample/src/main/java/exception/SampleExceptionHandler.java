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
