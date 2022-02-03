package com.party.service.partyservice.controller;


import com.party.service.partyservice.model.CreatePartyRequest;
import com.party.service.partyservice.model.DefaultApiResponse;
import com.party.service.partyservice.model.JoinPartyRequest;
import com.party.service.partyservice.model.UserPartyRequest;
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

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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

    @RequestMapping(value = "/join"
            , method = RequestMethod.POST
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    @Transactional
    public @ResponseBody
    ResponseEntity<DefaultApiResponse> joinParty(@RequestBody @Valid JoinPartyRequest request) {

        try {
            PartyRoleMappingEntityId partyRoleMappingEntityId = new PartyRoleMappingEntityId();
            partyRoleMappingEntityId.setEmail(request.getEmail());
            partyRoleMappingEntityId.setPartyId(request.getPartyId());

            PartyRoleMappingEntity currentEntity =
                    partyRoleMappingRepository.findById(partyRoleMappingEntityId).orElse(null);
            if (currentEntity != null) {
                DefaultApiResponse defaultApiResponse = new DefaultApiResponse();
                defaultApiResponse.setStatusCode("9003");
                defaultApiResponse.setStatusDesc("You already join this party");
                return new ResponseEntity<>(defaultApiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            }



            PartyEntity partyEntity = partyRepository.findById(request.getPartyId()).orElse(null);

            if (partyEntity == null) {
                DefaultApiResponse defaultApiResponse = new DefaultApiResponse();
                defaultApiResponse.setStatusCode("9002");
                defaultApiResponse.setStatusDesc("Party not found");
                return new ResponseEntity<>(defaultApiResponse, HttpStatus.NOT_FOUND);
            }
            if (Objects.equals(partyEntity.getCurrentMember(), partyEntity.getMemberLimit())) {
                DefaultApiResponse defaultApiResponse = new DefaultApiResponse();
                defaultApiResponse.setStatusCode("9001");
                defaultApiResponse.setStatusDesc("The party is full");
                return new ResponseEntity<>(defaultApiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                partyEntity.setCurrentMember(partyEntity.getCurrentMember() + 1);
                partyEntity.setLastUpdateDatetime(LocalDateTime.now());
            }

            // insert role
            PartyRoleMappingEntity partyRoleMappingEntity = new PartyRoleMappingEntity();

            partyRoleMappingEntity.setPartyRoleMappingEntityId(partyRoleMappingEntityId);
            partyRoleMappingEntity.setRole("USER");
            partyRoleMappingEntity.setLastUpdateDatetime(LocalDateTime.now());
            partyRepository.save(partyEntity);
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

    @RequestMapping(value = "/user"
            , method = RequestMethod.POST
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    List<PartyRoleMappingEntity> getUserParty(@RequestBody @Valid UserPartyRequest request) {
        return partyRoleMappingRepository.findByPartyRoleMappingEntityIdEmail(request.getEmail());
    }

    @RequestMapping(value = ""
            , method = RequestMethod.GET
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    List<PartyEntity> getUserParty() {
        return partyRepository.findAll();
    }


}
