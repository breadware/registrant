package br.com.breadware.exception;

public class ApplicationContextRuntimeException extends RegistrantRuntimeException {

    public ApplicationContextRuntimeException(String message) {
        super(message);
    }

    public ApplicationContextRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
