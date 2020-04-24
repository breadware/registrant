package br.com.breadware.configuration.condition;

import org.springframework.boot.autoconfigure.condition.NoneNestedConditions;
import org.springframework.context.annotation.Conditional;

public class NotRunningOnAppEngine extends NoneNestedConditions {


  public NotRunningOnAppEngine() {
    super(ConfigurationPhase.PARSE_CONFIGURATION);
  }

  @Conditional(RunningOnAppEngine.class)
  static class NotRunningOnAppEngineCheck{}
}
