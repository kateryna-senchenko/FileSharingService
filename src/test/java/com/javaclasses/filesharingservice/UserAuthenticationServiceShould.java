package com.javaclasses.filesharingservice;


import com.javaclasses.filesharingservice.dao.UserRepositoryImpl;
import com.javaclasses.filesharingservice.services.AuthenticationException;
import com.javaclasses.filesharingservice.services.UserAuthenticationService;
import com.javaclasses.filesharingservice.services.customdatatypes.*;
import com.javaclasses.filesharingservice.services.impl.UserAuthenticationServiceImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

public class UserAuthenticationServiceShould {


    private final UserAuthenticationService authenticationService =
            new UserAuthenticationServiceImpl(new UserRepositoryImpl());
    private final Email email = new Email("smth@gmail.com");
    private final Password password = new Password("mypassword");



    @Test
    public void signIn() throws AuthenticationException {

        AccessKey key = authenticationService.signIn(email, password);

        assertFalse("User could not sign in", key == null);
    }


    @Test
    public void failSignInWithWrongEmail(){

        final Email wrongEmail = new Email("wrong.email@gmail.com");

        try {
            authenticationService.signIn(wrongEmail, password);
            fail("Expected AuthenticationException was not thrown");
        } catch (AuthenticationException e) {
            assertEquals("Cannot find user with specified email", e.getMessage());
        }
    }

    @Test
    public void failSignInWithWrongPassword(){

        final Password wrongPassword = new Password("tarabarshchina");

        try {
            authenticationService.signIn(email, wrongPassword);
            fail("Expected AuthenticationException was not thrown");
        } catch (AuthenticationException e) {
            assertEquals("Invalid password", e.getMessage());
        }
    }

}
