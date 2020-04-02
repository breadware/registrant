package br.com.breadware.bo;

import br.com.breadware.dao.GmailIdsDao;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class GmailIdsBo {

    private final GmailIdsDao gmailIdsDao;

    public GmailIdsBo(GmailIdsDao gmailIdsDao) {
        this.gmailIdsDao = gmailIdsDao;
    }

    public BigInteger getLastHistoryId() {
        return gmailIdsDao.getLastHistoryId();
    }

    public void setLastHistoryId(BigInteger lastHistory) {
        gmailIdsDao.setLastHistoryId(lastHistory);
    }

    public boolean hasMessageId(String messageId) {
        return gmailIdsDao.hasMessageId(messageId);
    }

    public void putMessageId(String messageId) {
        gmailIdsDao.putMessageId(messageId);
    }
}
