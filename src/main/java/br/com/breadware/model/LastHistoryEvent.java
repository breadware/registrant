package br.com.breadware.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LastHistoryEvent {

    private BigInteger id;

    public LastHistoryEvent(BigInteger id) {
        this.id = id;
        additionalProperties = new HashMap<>();
    }

    public LastHistoryEvent() {
        this.id = BigInteger.ZERO;
        additionalProperties = new HashMap<>();
    }

    @JsonIgnore
    private final Map<String, Object> additionalProperties;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
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
        LastHistoryEvent that = (LastHistoryEvent) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(additionalProperties, that.additionalProperties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, additionalProperties);
    }
}
