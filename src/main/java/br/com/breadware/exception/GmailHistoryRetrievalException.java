package br.com.breadware.exception;

import br.com.breadware.model.message.ErrorMessage;

public class GmailHistoryRetrievalException extends RegistrantException {
    public GmailHistoryRetrievalException(ErrorMessage errorMessage, Object... parameters) {
        super(errorMessage, parameters);
    }

    public GmailHistoryRetrievalException(Throwable cause, ErrorMessage errorMessage, Object... parameters) {
        super(cause, errorMessage, parameters);
    }
}
