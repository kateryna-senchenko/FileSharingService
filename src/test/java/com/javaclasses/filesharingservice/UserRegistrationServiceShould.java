package com.javaclasses.filesharingservice;


import com.javaclasses.filesharingservice.dao.UserRepository;
import com.javaclasses.filesharingservice.services.customdatatypes.*;
import com.javaclasses.filesharingservice.dao.UserRepositoryImpl;
import com.javaclasses.filesharingservice.dao.entities.User;
import com.javaclasses.filesharingservice.services.impl.UserRegistrationServiceImpl;
import com.javaclasses.filesharingservice.services.DuplicateUserEmailException;
import com.javaclasses.filesharingservice.services.UserRegistrationService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class UserRegistrationServiceShould {

    private final UserRepository userRepository = new UserRepositoryImpl();
    private final UserRegistrationService registerService = new UserRegistrationServiceImpl(userRepository);
    private final Email email = new Email("makeawish@gmail.com");
    private final Password password = new Password("followthewhiterabbit");
    private final FirstName firstName = new FirstName("Alice");
    private final LastName lastName = new LastName("FromWonderland");
    private final User user = new User(new UserID(1), email, password, firstName, lastName);


    @Test
    public void registerUser() throws DuplicateUserEmailException {

        registerService.registerUser(email, password, firstName, lastName);

        assertEquals("New user was not registered", user, userRepository.findUserByID(new UserID(1)));

    }


    @Test
    public void failRegistrationUserWithDuplicateEmail(){

        try {
            registerService.registerUser(email, password, firstName, lastName);
            registerService.registerUser(email, password, firstName, lastName);
            fail("Expected DuplicateEmailException was not thrown");
        } catch (DuplicateUserEmailException e) {
            assertEquals("User with specified email address is already exist. Try another", e.getMessage());
        }
    }
}
