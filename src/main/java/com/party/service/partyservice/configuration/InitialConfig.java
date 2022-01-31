package com.party.service.partyservice.configuration;

import com.party.service.partyservice.model.entity.UserEntity;
import com.party.service.partyservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InitialConfig {

    @Autowired
    private UserRepository userRepository;

    public InitialConfig(){
        //test adding initial data

    }
}
