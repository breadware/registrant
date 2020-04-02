package br.com.breadware.model;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

public class GmailIds {

    private BigInteger lastHistory;

    private Set<String> messages;

    public GmailIds() {
        this.lastHistory = BigInteger.ONE;
        this.messages = new HashSet<>();
    }

    public BigInteger getLastHistory() {
        return lastHistory;
    }

    public void setLastHistory(BigInteger lastHistory) {
        this.lastHistory = lastHistory;
    }

    public Set<String> getMessages() {
        return messages;
    }

    public void setMessages(Set<String> messages) {
        this.messages = messages;
    }
}
