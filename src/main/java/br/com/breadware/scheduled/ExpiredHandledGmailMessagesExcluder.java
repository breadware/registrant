package br.com.breadware.scheduled;

import br.com.breadware.bo.GmailMessageBo;
import br.com.breadware.exception.DataAccessException;
import br.com.breadware.exception.RegistrantRuntimeException;
import br.com.breadware.model.HandledGmailMessage;
import br.com.breadware.model.message.ErrorMessage;
import br.com.breadware.model.message.LoggerMessage;
import br.com.breadware.properties.MessagesProperties;
import br.com.breadware.util.LoggerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ExpiredHandledGmailMessagesExcluder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExpiredHandledGmailMessagesExcluder.class);

    /* Exclusion method will run every 24h */
    private static final long EXCLUSION_SCHEDULED_FIXED_RATE = 24L * 60L * 60L * 1000L;

    private static final long EXCLUSION_SCHEDULED_INITIAL_DELAY = 2000L;

    private static final double SECONDS_IN_A_DAY = 24.0 * 60.0 * 60.0;

    private final GmailMessageBo gmailMessageBo;

    private final MessagesProperties messagesProperties;

    private final LoggerUtil loggerUtil;

    public ExpiredHandledGmailMessagesExcluder(GmailMessageBo gmailMessageBo, MessagesProperties messagesProperties, LoggerUtil loggerUtil) {
        this.gmailMessageBo = gmailMessageBo;
        this.messagesProperties = messagesProperties;
        this.loggerUtil = loggerUtil;
    }

    @Scheduled(initialDelay = EXCLUSION_SCHEDULED_INITIAL_DELAY, fixedRate = EXCLUSION_SCHEDULED_FIXED_RATE)
    public void exclude() {
        loggerUtil.info(LOGGER, LoggerMessage.EXCLUDING_EXPIRED_HANDLED_GMAIL_MESSAGES, messagesProperties.getHandledIdsRetentionTimeDays());
        try {
            Set<HandledGmailMessage> handledGmailMessages = gmailMessageBo.getHandledGmailMessages();
            Instant retentionLimit = calculateRetentionLimit();
            Set<String> expiredMessageIds = handledGmailMessages.stream()
                    .filter(handledGmailMessage -> handledBeforeRetentionLimit(handledGmailMessage, retentionLimit))
                    .map(HandledGmailMessage::getId)
                    .collect(Collectors.toSet());

            if (CollectionUtils.isEmpty(expiredMessageIds)) {
                loggerUtil.info(LOGGER, LoggerMessage.NO_EXPIRED_HANDLED_GMAIL_MESSAGE_TO_EXCLUDE);
            } else {
                gmailMessageBo.deleteHandledMessagesByIds(expiredMessageIds);
                loggerUtil.info(LOGGER, LoggerMessage.EXPIRED_HANDLED_GMAIL_MESSAGES_EXCLUDED, expiredMessageIds.size());
            }
        } catch (DataAccessException exception) {
            throw new RegistrantRuntimeException(exception, ErrorMessage.ERROR_EXCLUDING_EXPIRED_HANDLED_MESSAGES);
        }
    }

    private boolean handledBeforeRetentionLimit(HandledGmailMessage handledGmailMessage, Instant retentionLimit) {
        Instant handledGmailMessageInstant = Instant.ofEpochSecond(handledGmailMessage.getTime());
        return handledGmailMessageInstant.isBefore(retentionLimit);
    }

    private Instant calculateRetentionLimit() {
        return Instant.now()
                .minus(retrieveRetentionTimeSeconds(), ChronoUnit.SECONDS);
    }

    private long retrieveRetentionTimeSeconds() {
        return (long) (messagesProperties.getHandledIdsRetentionTimeDays() * SECONDS_IN_A_DAY);
    }
}
