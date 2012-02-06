package exception;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class SampleException extends Exception {

    public SampleException(Throwable cause) {
        super(cause);
    }

    public SampleException(String message, Throwable cause) {
        super(message, cause);
    }

    public SampleException(String message) {
        super(message);
    }

    public SampleException() {
    }
}
