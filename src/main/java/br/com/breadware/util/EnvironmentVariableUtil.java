package br.com.breadware.util;

import br.com.breadware.exception.RegistrantRuntimeException;
import br.com.breadware.model.message.ErrorMessage;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EnvironmentVariableUtil {

    public void throwExceptionIfDoesNotExist(String name) {

        if (Objects.isNull(System.getenv(name))) {
            throw new RegistrantRuntimeException(ErrorMessage.UNDEFINED_ENVIRONMENT_VARIABLE, name);
        }
    }
}
