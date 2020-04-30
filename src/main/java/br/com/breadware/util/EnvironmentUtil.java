package br.com.breadware.util;

import br.com.breadware.exception.RegistrantRuntimeException;
import br.com.breadware.model.message.ErrorMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EnvironmentUtil {

  private void throwExceptionIfVariableDoesNotExist(String environmentVariableName) {

    if (Objects.isNull(System.getenv(environmentVariableName))) {
      throw new RegistrantRuntimeException(ErrorMessage.UNDEFINED_ENVIRONMENT_VARIABLE,
          environmentVariableName);
    }
  }

  public boolean isDefined(String environmentVariableName) {
    return !StringUtils.isEmpty(System.getenv(environmentVariableName));
  }

  public String retrieveMandatoryVariable(String name) {
    throwExceptionIfVariableDoesNotExist(name);
    return System.getenv(name);
  }

  public boolean isAppEngine() {
      return isDefined(EnvironmentVariables.APP_ENGINE_ENVIRONMENT);
  }
}
