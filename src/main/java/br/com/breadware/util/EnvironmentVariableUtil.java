package br.com.breadware.util;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EnvironmentVariableUtil {

    private static final String UNDEFINED_ENVIRONMENT_VARIABLE_EXCEPTION_MESSAGE_TEMPLATE = "Environment variable \"%s\" is not defined.";

    public static void throwExceptionIfDoesNotExist(String name) {
        Optional.ofNullable(System.getenv(name))
                .orElseThrow(() -> new IllegalStateException(String.format(UNDEFINED_ENVIRONMENT_VARIABLE_EXCEPTION_MESSAGE_TEMPLATE, name)));
    }
}
