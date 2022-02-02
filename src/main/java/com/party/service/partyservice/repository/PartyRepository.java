package com.party.service.partyservice.repository;

import com.party.service.partyservice.model.entity.PartyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyRepository extends JpaRepository<PartyEntity, String> {
}
