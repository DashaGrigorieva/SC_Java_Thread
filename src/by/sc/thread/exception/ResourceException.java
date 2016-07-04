package by.sc.thread.exception;

/**
 * Created by User on 19.06.2016.
 */
public class ResourceException extends Exception{

    public ResourceException(String message) {
        super(message);
    }

    public ResourceException() {
    }

    public ResourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceException(Throwable cause) {
        super(cause);
    }

}
