package br.com.breadware.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HandledGmailMessage {

    @JsonIgnore
    private final Map<String, Object> additionalProperties;

    private String id;

    private long time;

    public HandledGmailMessage() {
        additionalProperties = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
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
        HandledGmailMessage that = (HandledGmailMessage) o;
        return time == that.time &&
                Objects.equals(id, that.id) &&
                Objects.equals(additionalProperties, that.additionalProperties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, time, additionalProperties);
    }

    public static final class Builder {
        private String id;
        private long time;

        private Builder() {
        }

        public static Builder aHandledGmailMessage() {
            return new Builder();
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder time(long time) {
            this.time = time;
            return this;
        }

        public HandledGmailMessage build() {
            HandledGmailMessage handledGmailMessage = new HandledGmailMessage();
            handledGmailMessage.setId(id);
            handledGmailMessage.setTime(time);
            return handledGmailMessage;
        }
    }
}
