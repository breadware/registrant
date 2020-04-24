package br.com.breadware.configuration.condition;

import java.util.Objects;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class RunningOnAppEngine implements Condition {

  public static final String ENVIRONMENT_VARIABLE_IS_APPENGINE = "IS_APPENGINE";

  @Override
  public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
    String appEngineVersion = System.getenv(ENVIRONMENT_VARIABLE_IS_APPENGINE);
    return Objects.nonNull(appEngineVersion);
  }
}
