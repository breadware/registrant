package br.com.breadware.google.mail;

import br.com.breadware.configuration.GcpConfiguration;
import br.com.breadware.exception.GmailHistoryRetrievalException;
import br.com.breadware.model.GmailHistoryEvent;
import br.com.breadware.model.message.ErrorMessage;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListHistoryResponse;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

@Component
public class GmailHistoryRetriever {

    private static final List<String> HISTORY_TYPES = Collections.singletonList("messageAdded");

    private final Gmail gmail;

    @Inject
    public GmailHistoryRetriever(Gmail gmail) {
        this.gmail = gmail;
    }

    public ListHistoryResponse retrieve(GmailHistoryEvent gmailHistoryEvent) throws GmailHistoryRetrievalException {

        try {
            BigInteger startHistoryId = gmailHistoryEvent.getId();

            return gmail.users()
                    .history()
                    .list(GcpConfiguration.USER_ID)
                    .setStartHistoryId(startHistoryId)
                    .setHistoryTypes(HISTORY_TYPES)
                    .execute();

        } catch (IOException exception) {
            throw new GmailHistoryRetrievalException(exception, ErrorMessage.ERROR_WHILE_RETRIEVING_GMAIL_HISTORY, gmailHistoryEvent.getId());
        }
    }
}
