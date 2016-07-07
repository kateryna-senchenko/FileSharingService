package com.javaclasses.filesharingservice.services;

import com.javaclasses.filesharingservice.services.customdatatypes.Email;
import com.javaclasses.filesharingservice.services.customdatatypes.FirstName;
import com.javaclasses.filesharingservice.services.customdatatypes.LastName;
import com.javaclasses.filesharingservice.services.customdatatypes.Password;

/**
 * Public API of user registration service
 */
public interface UserRegistrationService {

    /**
     * Registers a new user
     * @param email - email address of the new user
     * @param password - password of the new user
     * @param firstName - first name of the user
     * @param lastName - last name of the user
     * @throws DuplicateUserEmailException if exists a user with identical email address
     */
    void registerUser(Email email, Password password, FirstName firstName, LastName lastName)
            throws DuplicateUserEmailException;
}
