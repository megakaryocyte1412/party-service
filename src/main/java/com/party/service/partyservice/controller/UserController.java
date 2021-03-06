package com.party.service.partyservice.controller;

import com.party.service.partyservice.configuration.AuthenConfig;
import com.party.service.partyservice.model.DefaultApiResponse;
import com.party.service.partyservice.model.UserRequest;
import com.party.service.partyservice.model.entity.UserEntity;
import com.party.service.partyservice.repository.UserRepository;
import com.party.service.partyservice.util.PasswordUtil;
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
import java.util.List;

@Controller
@EnableSwagger2
@Validated
@RequestMapping("/rest/api/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenConfig authenConfig;

    @Autowired
    private PasswordUtil passwordUtil;


    @RequestMapping(value = "", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    List<UserEntity> getUser() {
        return userRepository.findAll();
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    ResponseEntity<DefaultApiResponse> login(@RequestBody @Valid UserRequest userRequest) {
        UserEntity userEntity = userRepository.findById(userRequest.getEmail()).orElse(null);
        if (userEntity != null && passwordUtil.doPasswordsMatch(userRequest.getPassword(), userEntity.getPassword())) {
            DefaultApiResponse defaultApiResponse = new DefaultApiResponse();
            defaultApiResponse.setStatusCode("0000");
            defaultApiResponse.setStatusDesc("Success");
            return new ResponseEntity<>(defaultApiResponse, HttpStatus.OK);
        } else {
            DefaultApiResponse defaultApiResponse = new DefaultApiResponse();
            defaultApiResponse.setStatusCode("9999");
            defaultApiResponse.setStatusDesc("Error");
            return new ResponseEntity<>(defaultApiResponse, HttpStatus.UNAUTHORIZED);
        }
    }


    @RequestMapping(value = "/register"
            , method = RequestMethod.POST
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    ResponseEntity<DefaultApiResponse> register(@RequestBody @Valid UserRequest userRequest) {
        try {
            UserEntity user = new UserEntity();
            user.setEmail(userRequest.getEmail());
            user.setPassword(passwordUtil.bcryptEncryptor(userRequest.getPassword()));
            log.info(user.toString());
            userRepository.save(user);

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


}
