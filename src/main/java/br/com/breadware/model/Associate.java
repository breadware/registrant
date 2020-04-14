package br.com.breadware.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Associate {

    @JsonIgnore
    private final Map<String, Object> additionalProperties;

    private Long cpf;

    private String firstName;

    private String lastName;

    private String address;

    private String phone;

    public Associate() {
        this.additionalProperties = new HashMap<>();
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
        Associate associate = (Associate) o;
        return Objects.equals(cpf, associate.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    public static final class Builder {
        private Long cpf;
        private String firstName;
        private String lastName;
        private String address;
        private String phone;

        private Builder() {
        }

        public static Builder anAssociate() {
            return new Builder();
        }

        public Builder cpf(Long cpf) {
            this.cpf = cpf;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Associate build() {
            Associate associate = new Associate();
            associate.setCpf(cpf);
            associate.setFirstName(firstName);
            associate.setLastName(lastName);
            associate.setAddress(address);
            associate.setPhone(phone);
            return associate;
        }
    }
}
