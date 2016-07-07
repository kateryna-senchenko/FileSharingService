package com.javaclasses.filesharingservice.services.impl;

import com.javaclasses.filesharingservice.dao.UserRepository;
import com.javaclasses.filesharingservice.dao.entities.User;
import com.javaclasses.filesharingservice.services.AuthenticationException;
import com.javaclasses.filesharingservice.services.UserAuthenticationService;
import com.javaclasses.filesharingservice.services.customdatatypes.AccessKey;
import com.javaclasses.filesharingservice.services.customdatatypes.Email;
import com.javaclasses.filesharingservice.services.customdatatypes.Password;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of the UserAuthenticationService interface
 */
public class UserAuthenticationServiceImpl implements UserAuthenticationService{

    private static final Logger log = LoggerFactory.getLogger(UserAuthenticationServiceImpl.class);

    private final UserRepository userRepository;

    public UserAuthenticationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AccessKey signIn(Email email, Password password) throws AuthenticationException {


        if(log.isInfoEnabled()){
            log.info("Looking for a user with email {}", email.getEmail());
        }

        User user = userRepository.findUserByEmail(email);

        if(log.isInfoEnabled()){
            log.info("Checking if password {} is valid", password.getPassword());
        }

        userRepository.checkPasswordValidity(user, password);

        final AccessKey key = new AccessKey(0);

        if(log.isInfoEnabled()){
            log.info("Access key {} is assigned to user with email {}", key.getAccessKey(), email.getEmail());
        }
        userRepository.addActiveUser(key, user);

        if(log.isInfoEnabled()){
            log.info("User with email {} is logged in", email.getEmail());
        }

        return key;
    }
}
