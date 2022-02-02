package com.party.service.partyservice.model.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity(name = "PARTY_ROLE_MAPPING")
public class PartyRoleMappingEntity {
    @EmbeddedId
    private PartyRoleMappingEntityId partyRoleMappingEntityId;

    @Column(name = "ROLE", length = 255, nullable = false)
    private String role;

    @Column(name = "LAST_UPDATE_DATETIME", nullable = false)
    private LocalDateTime lastUpdateDatetime;

    public PartyRoleMappingEntityId getPartyRoleMappingEntityId() {
        return partyRoleMappingEntityId;
    }

    public void setPartyRoleMappingEntityId(PartyRoleMappingEntityId partyRoleMappingEntityId) {
        this.partyRoleMappingEntityId = partyRoleMappingEntityId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getLastUpdateDatetime() {
        return lastUpdateDatetime;
    }

    public void setLastUpdateDatetime(LocalDateTime lastUpdateDatetime) {
        this.lastUpdateDatetime = lastUpdateDatetime;
    }
}
