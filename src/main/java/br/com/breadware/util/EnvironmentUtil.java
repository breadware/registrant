package br.com.breadware.util;

import br.com.breadware.exception.RegistrantRuntimeException;
import br.com.breadware.model.message.ErrorMessage;
import br.com.breadware.model.message.LoggerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EnvironmentUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentUtil.class);

    private final LoggerUtil loggerUtil;

    private boolean googleApplicationCredentialsEnvironmentVariableChecked;


    public EnvironmentUtil(LoggerUtil loggerUtil) {
        this.loggerUtil = loggerUtil;
        this.googleApplicationCredentialsEnvironmentVariableChecked = false;
    }

    private void throwExceptionIfVariableDoesNotExist(String environmentVariableName) {

        if (Objects.isNull(System.getenv(environmentVariableName))) {
            throw new RegistrantRuntimeException(ErrorMessage.UNDEFINED_ENVIRONMENT_VARIABLE,
                    environmentVariableName);
        }
    }

    public String retrieveMandatoryVariable(String name) {
        throwExceptionIfVariableDoesNotExist(name);
        return System.getenv(name);
    }

    public void checkGoogleApplicationCredentialsEnvironmentVariable() {
        if (!googleApplicationCredentialsEnvironmentVariableChecked) {
            if (isRunningOnAppEngine()) {
                loggerUtil.info(LOGGER, LoggerMessage.SKIPPING_GOOGLE_CREDENTIALS_ENVIRONMENT_VARIABLE_CHECK, EnvironmentVariables.GOOGLE_APPLICATION_CREDENTIALS);
                googleApplicationCredentialsEnvironmentVariableChecked = true;
            } else {
                throwExceptionIfVariableDoesNotExist(EnvironmentVariables.GOOGLE_APPLICATION_CREDENTIALS);
            }
        }
    }

    private boolean isRunningOnAppEngine() {
        return !Objects.isNull(System.getenv(EnvironmentVariables.APP_ENGINE_ENVIRONMENT));
    }
}
