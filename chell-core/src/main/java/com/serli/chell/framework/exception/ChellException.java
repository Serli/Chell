package com.serli.chell.framework.exception;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class ChellException extends RuntimeException {

    public ChellException(Throwable cause) {
        super(cause);
    }

    public ChellException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChellException(String message) {
        super(message);
    }

    public ChellException() {
    }
}
