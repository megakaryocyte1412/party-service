package com.party.service.partyservice.controller;


import com.party.service.partyservice.model.CreatePartyRequest;
import com.party.service.partyservice.model.DefaultApiResponse;
import com.party.service.partyservice.model.entity.PartyEntity;
import com.party.service.partyservice.model.entity.PartyRoleMappingEntity;
import com.party.service.partyservice.model.entity.PartyRoleMappingEntityId;
import com.party.service.partyservice.repository.PartyRepository;
import com.party.service.partyservice.repository.PartyRoleMappingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@EnableSwagger2
@Validated
@RequestMapping("/rest/api/party")
@CrossOrigin(origins = "http://localhost:4200")
public class PartyController {

    Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private PartyRoleMappingRepository partyRoleMappingRepository;

    @RequestMapping(value = "/create"
            , method = RequestMethod.POST
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    ResponseEntity<DefaultApiResponse> createParty(@RequestBody @Valid CreatePartyRequest request) {

        try {
            String partyId = UUID.randomUUID().toString();
            // create party
            PartyEntity partyEntity = new PartyEntity();
            partyEntity.setPartyId(partyId);
            partyEntity.setPartyName(request.getPartyName());
            partyEntity.setCurrentMember(1);
            partyEntity.setMemberLimit(request.getMemberLimit());
            partyEntity.setOwnerEmail(request.getOwnerEmail());
            partyEntity.setLastUpdateDatetime(LocalDateTime.now());
            partyRepository.save(partyEntity);

            // insert owner role
            PartyRoleMappingEntity partyRoleMappingEntity = new PartyRoleMappingEntity();
            PartyRoleMappingEntityId partyRoleMappingEntityId = new PartyRoleMappingEntityId();

            partyRoleMappingEntityId.setEmail(request.getOwnerEmail());
            partyRoleMappingEntityId.setPartyId(partyId);

            partyRoleMappingEntity.setPartyRoleMappingEntityId(partyRoleMappingEntityId);
            partyRoleMappingEntity.setRole("OWNER");
            partyRoleMappingEntity.setLastUpdateDatetime(LocalDateTime.now());

            partyRoleMappingRepository.save(partyRoleMappingEntity);

            DefaultApiResponse defaultApiResponse = new DefaultApiResponse();
            defaultApiResponse.setStatusCode("0000");
            defaultApiResponse.setStatusDesc("Success");
            return new ResponseEntity<>(defaultApiResponse, HttpStatus.CREATED);
        } catch (Exception ex) {
            DefaultApiResponse defaultApiResponse = new DefaultApiResponse();
            defaultApiResponse.setStatusCode("9999");
            defaultApiResponse.setStatusDesc("Error");
            return new ResponseEntity<>(defaultApiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = ""
            , method = RequestMethod.GET
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    List<PartyEntity> getAll() {
        return partyRepository.findAll();
    }
}
