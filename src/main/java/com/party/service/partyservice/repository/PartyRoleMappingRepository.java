package com.party.service.partyservice.repository;

import com.party.service.partyservice.model.entity.PartyRoleMappingEntity;
import com.party.service.partyservice.model.entity.PartyRoleMappingEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartyRoleMappingRepository extends JpaRepository<PartyRoleMappingEntity, PartyRoleMappingEntityId> {
    List<PartyRoleMappingEntity> findByPartyRoleMappingEntityIdEmail(String email);
}
