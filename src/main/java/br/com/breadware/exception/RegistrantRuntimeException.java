package br.com.breadware.exception;

public class RegistrantRuntimeException extends RuntimeException {

    public RegistrantRuntimeException(String message) {
        super(message);
    }

    public RegistrantRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
