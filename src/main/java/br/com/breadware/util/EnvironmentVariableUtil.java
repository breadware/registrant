package br.com.breadware.util;

import br.com.breadware.exception.RegistrantRuntimeException;
import br.com.breadware.model.message.ErrorMessage;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EnvironmentVariableUtil {

    public void throwExceptionIfDoesNotExist(String name) {
        Optional.ofNullable(System.getenv(name))
                .orElseThrow(() -> new RegistrantRuntimeException(ErrorMessage.UNDEFINED_ENVIRONMENT_VARIABLE, name));
    }
}
