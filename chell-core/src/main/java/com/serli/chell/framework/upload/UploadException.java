
package com.serli.chell.framework.upload;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class UploadException extends Exception {
    
    private static final Object[] EMPTY = new Object[0];

    private Object[] messageArguments;

    public UploadException(String messageKey) {
        this(messageKey, EMPTY);
    }

    public UploadException(String messageKey, Throwable throwable) {
        this(messageKey, throwable, EMPTY);
    }
    
    public UploadException(String messageKey, Object... messageArguments) {
        super(messageKey);
        this.messageArguments = messageArguments;
    }

    public UploadException(String messageKey, Throwable throwable, Object... messageArguments) {
        super(messageKey, throwable);
        this.messageArguments = messageArguments;
    }

    public Object[] getMessageArguments() {
        return messageArguments;
    }
}
