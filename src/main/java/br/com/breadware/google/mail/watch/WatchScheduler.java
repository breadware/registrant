package br.com.breadware.google.mail.watch;

import br.com.breadware.model.message.LoggerMessage;
import br.com.breadware.util.LoggerUtil;
import br.com.breadware.util.ZonedDateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class WatchScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(WatchScheduler.class);

    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private final LoggerUtil loggerUtil;

    private final ZonedDateTimeUtil zonedDateTimeUtil;

    @Inject
    public WatchScheduler(ThreadPoolTaskScheduler threadPoolTaskScheduler, LoggerUtil loggerUtil, ZonedDateTimeUtil zonedDateTimeUtil) {
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
        this.loggerUtil = loggerUtil;
        this.zonedDateTimeUtil = zonedDateTimeUtil;
    }

    public void schedule(Instant expirationInstant, WatchRequester watchRequester) {

        Instant nextExecutionInstant = calculateNextExecutionInstant(expirationInstant);
        threadPoolTaskScheduler.schedule(watchRequester::request, nextExecutionInstant);
    }

    private Instant calculateNextExecutionInstant(Instant expirationInstant) {
        Instant nextExecutionInstant = expirationInstant.minus(30, ChronoUnit.MINUTES);
        ZonedDateTime nextExecutionZonedDateTime = zonedDateTimeUtil.convertFromUtcInstant(nextExecutionInstant);
        loggerUtil.info(LOGGER, LoggerMessage.SCHEDULING_NEXT_WATCH_REQUEST, zonedDateTimeUtil.writeAsHumanReadableFormat(nextExecutionZonedDateTime));
        return nextExecutionInstant;
    }
}
