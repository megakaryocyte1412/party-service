package com.party.service.partyservice.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtil {

    private static BCryptPasswordEncoder passwordEcorder = new BCryptPasswordEncoder();


    public String bcryptEncryptor(String plainText) {
        return passwordEcorder.encode(plainText);
    }

    public Boolean doPasswordsMatch(String rawPassword, String encodedPassword) {
        return passwordEcorder.matches(rawPassword, encodedPassword);
    }

//        public String bcryptEncryptor(String plainText) {
//        return plainText;
//    }
//
//    public Boolean doPasswordsMatch(String rawPassword, String encodedPassword) {
//        return rawPassword.equals(encodedPassword);
//    }
}
