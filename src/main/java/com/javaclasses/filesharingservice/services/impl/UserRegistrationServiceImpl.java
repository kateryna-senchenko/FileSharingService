package com.javaclasses.filesharingservice.services.impl;

import com.javaclasses.filesharingservice.dao.UserRepository;
import com.javaclasses.filesharingservice.services.DuplicateUserEmailException;
import com.javaclasses.filesharingservice.services.UserRegistrationService;
import com.javaclasses.filesharingservice.services.customdatatypes.Email;
import com.javaclasses.filesharingservice.services.customdatatypes.FirstName;
import com.javaclasses.filesharingservice.services.customdatatypes.LastName;
import com.javaclasses.filesharingservice.services.customdatatypes.Password;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of the UserRegistrationService interface
 */
public class UserRegistrationServiceImpl implements UserRegistrationService{

    private static final Logger log = LoggerFactory.getLogger(UserRegistrationServiceImpl.class);

    private final UserRepository userRepository;

    public UserRegistrationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(Email email, Password password, FirstName firstName, LastName lastName)
            throws DuplicateUserEmailException {

        if (log.isInfoEnabled()){
            log.info("Registering new user with email {}", email.getEmail());
        }
        userRepository.createUser(email, password, firstName, lastName);

    }
}
