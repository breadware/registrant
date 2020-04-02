package br.com.breadware.subscriber;

import br.com.breadware.bo.LastHistoryEventBo;
import br.com.breadware.configuration.GcpConfiguration;
import br.com.breadware.exception.DataAccessException;
import br.com.breadware.exception.GmailHistoryRetrievalException;
import br.com.breadware.model.message.ErrorMessage;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListHistoryResponse;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class GmailHistoryRetriever {

    private static final List<String> HISTORY_TYPES = Collections.singletonList("messageAdded");

    private final Gmail gmail;

    private final LastHistoryEventBo lastHistoryEventBo;

    @Inject
    public GmailHistoryRetriever(Gmail gmail, LastHistoryEventBo lastHistoryEventBo) {
        this.gmail = gmail;
        this.lastHistoryEventBo = lastHistoryEventBo;
    }

    public ListHistoryResponse retrieve() throws GmailHistoryRetrievalException {


        BigInteger startHistoryId = null;
        try {
            startHistoryId = lastHistoryEventBo.get()
                    .getId();
            return gmail.users()
                    .history()
                    .list(GcpConfiguration.USER_ID)
                    .setStartHistoryId(startHistoryId)
                    .setHistoryTypes(HISTORY_TYPES)
                    .execute();
        } catch (DataAccessException | IOException exception) {
            throw new GmailHistoryRetrievalException(exception, ErrorMessage.ERROR_WHILE_REQUESTING_GMAIL_HISTORY, Optional.ofNullable(startHistoryId)
                    .map(BigInteger::toString)
                    .orElse("?"));
        }
    }
}
