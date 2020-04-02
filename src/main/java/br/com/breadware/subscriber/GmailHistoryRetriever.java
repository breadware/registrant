package br.com.breadware.subscriber;

import br.com.breadware.bo.GmailIdsBo;
import br.com.breadware.configuration.GcpConfiguration;
import br.com.breadware.exception.GmailHistoryRetrievalException;
import br.com.breadware.model.message.ErrorMessage;
import br.com.breadware.model.message.LoggerMessage;
import br.com.breadware.util.LoggerUtil;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.History;
import com.google.api.services.gmail.model.ListHistoryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

@Component
public class GmailHistoryRetriever {

    private static final List<String> HISTORY_TYPES = Collections.singletonList("messageAdded");

    private final Gmail gmail;

    private final GmailIdsBo gmailIdsBo;

    @Inject
    public GmailHistoryRetriever(Gmail gmail, GmailIdsBo gmailIdsBo) {
        this.gmail = gmail;
        this.gmailIdsBo = gmailIdsBo;
    }

    public ListHistoryResponse retrieve() throws GmailHistoryRetrievalException {

        BigInteger startHistoryId = gmailIdsBo.getLastHistoryId();

        try {
            return gmail.users()
                    .history()
                    .list(GcpConfiguration.USER_ID)
                    .setStartHistoryId(startHistoryId)
                    .setHistoryTypes(HISTORY_TYPES)
                    .execute();
        } catch (IOException exception) {
            throw new GmailHistoryRetrievalException(exception, ErrorMessage.ERROR_WHILE_REQUESTING_GMAIL_HISTORY, startHistoryId);
        }
    }
}
