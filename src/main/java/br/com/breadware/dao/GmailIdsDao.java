package br.com.breadware.dao;

import br.com.breadware.model.GmailIds;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class GmailIdsDao {

    public static final String HISTORY_ID_SYNC = "historyIdSync";

    public static final String MESSAGE_IDS_SYNC = "messageIdsSync";

    private GmailIds gmailIds;

    public GmailIdsDao() {
        this.gmailIds = new GmailIds();
    }

    public BigInteger getLastHistoryId() {
        synchronized (HISTORY_ID_SYNC) {
            return gmailIds.getLastHistory();
        }
    }

    public void setLastHistoryId(BigInteger lastHistory) {

        synchronized (HISTORY_ID_SYNC) {
            gmailIds.setLastHistory(lastHistory);
        }
    }

    public boolean hasMessageId(String messageId) {
        synchronized (MESSAGE_IDS_SYNC) {
            return gmailIds.getMessages().contains(messageId);
        }
    }

    public void putMessageId(String messageId) {
        synchronized (MESSAGE_IDS_SYNC) {
            gmailIds.getMessages().add(messageId);
        }
    }
}
