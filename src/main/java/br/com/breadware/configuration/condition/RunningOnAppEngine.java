package br.com.breadware.configuration.condition;

import java.util.Objects;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class RunningOnAppEngine implements Condition {

  @Override
  public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
    String appEngineVersion = System.getProperty("com.google.appengine.runtime.version");
    return Objects.nonNull(appEngineVersion);
  }
}
