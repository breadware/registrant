package br.com.breadware.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GmailHistoryEvent {

    @JsonIgnore
    private final Map<String, Object> additionalProperties;

    @JsonProperty("historyId")
    private BigInteger id;

    private String emailAddress;

    public GmailHistoryEvent() {
        this.additionalProperties = new HashMap<>();
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    @JsonAnyGetter
    public final Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GmailHistoryEvent that = (GmailHistoryEvent) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(emailAddress, that.emailAddress) &&
                Objects.equals(additionalProperties, that.additionalProperties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, emailAddress, additionalProperties);
    }

    @Override
    public String toString() {
        return "GmailHistoryEvent{" +
                "id=" + id +
                ", emailAddress='" + emailAddress + '\'' +
                ", additionalProperties=" + additionalProperties +
                '}';
    }

    public static final class Builder {
        private BigInteger id;
        private String emailAddress;

        private Builder() {
        }

        public static Builder aGmailHistoryEvent() {
            return new Builder();
        }

        public Builder id(BigInteger id) {
            this.id = id;
            return this;
        }

        public Builder emailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        public GmailHistoryEvent build() {
            GmailHistoryEvent gmailHistoryEvent = new GmailHistoryEvent();
            gmailHistoryEvent.setId(id);
            gmailHistoryEvent.setEmailAddress(emailAddress);
            return gmailHistoryEvent;
        }
    }
}
