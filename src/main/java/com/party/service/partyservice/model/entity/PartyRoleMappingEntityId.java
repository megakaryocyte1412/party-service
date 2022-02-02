package com.party.service.partyservice.model.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
public class PartyRoleMappingEntityId implements Serializable {
    @Column(name = "PARTY_ID", length = 36, nullable = false)
    private String partyId;

    @Column(name = "USER_EMAIL", length = 255, nullable = false)
    private String email;

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartyRoleMappingEntityId that = (PartyRoleMappingEntityId) o;
        return Objects.equals(partyId, that.partyId) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(partyId, email);
    }
}
