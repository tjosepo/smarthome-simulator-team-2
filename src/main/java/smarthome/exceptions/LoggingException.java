package smarthome.exceptions;

import java.io.IOException;

public class LoggingException extends IOException {

    public LoggingException(String message) {
        super(message);
    }

    public LoggingException() {
        super("Caught LoggingException");
    }

    public LoggingException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage() + ": Error while writing to the log.";
    }

}

