package com.party.service.partyservice.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "PARTY")
public class PartyEntity {
    @Id
    @Column(name = "PARTY_ID", length = 36, nullable = false)
    private String partyId;

    @Column(name = "PARTY_NAME", length = 255, nullable = false)
    private String partyName;

    @Column(name = "OWNER_EMAIL", length = 255, nullable = false)
    private String ownerEmail;

    @Column(name = "CURRENT_MEMBER", nullable = false)
    private Integer currentMember;

    @Column(name = "MEMBER_LIMIT", nullable = false)
    private Integer memberLimit;

    @Column(name = "LAST_UPDATE_DATETIME", nullable = false)
    private LocalDateTime lastUpdateDatetime;

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public Integer getCurrentMember() {
        return currentMember;
    }

    public void setCurrentMember(Integer currentMember) {
        this.currentMember = currentMember;
    }

    public Integer getMemberLimit() {
        return memberLimit;
    }

    public void setMemberLimit(Integer memberLimit) {
        this.memberLimit = memberLimit;
    }

    public LocalDateTime getLastUpdateDatetime() {
        return lastUpdateDatetime;
    }

    public void setLastUpdateDatetime(LocalDateTime lastUpdateDatetime) {
        this.lastUpdateDatetime = lastUpdateDatetime;
    }
}
