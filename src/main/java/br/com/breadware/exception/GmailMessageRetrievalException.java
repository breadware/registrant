package br.com.breadware.exception;

import br.com.breadware.model.message.ErrorMessage;

public class GmailMessageRetrievalException extends RegistrantException {

    public GmailMessageRetrievalException(Throwable cause, ErrorMessage errorMessage, Object... parameters) {
        super(cause, errorMessage, parameters);
    }
}
