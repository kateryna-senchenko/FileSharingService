package com.javaclasses.filesharingservice.dao;


import com.javaclasses.filesharingservice.dao.entities.User;
import com.javaclasses.filesharingservice.services.AuthenticationException;
import com.javaclasses.filesharingservice.services.DuplicateUserEmailException;
import com.javaclasses.filesharingservice.services.customdatatypes.*;

import java.util.Set;

/**
 * Interface of the service that manages User entities
 */
public interface UserRepository {

    /**
     * Creates a new user
     * @param email - email address of the new user
     * @param password - password of the new user
     * @param firstName - first name of the user
     * @param lastName - last name of the user
     * @throws DuplicateUserEmailException if exists a user with identical email address
     */
    void createUser(Email email, Password password, FirstName firstName, LastName lastName)
            throws DuplicateUserEmailException;


    /**
     * Provides access to existing user by id
     * @param id - user id
     * @return User object with specified id
     */
    User findUserByID(long id);


    /**
     * Provides access to existing user by email
     * @param email - users email address
     * @return User object with specified email
     * @throws AuthenticationException if user with specified email address does not exist
     */
    User findUserByEmail(Email email) throws AuthenticationException;


    /**
     * Checks if specified password is valid
     * @param user - user whose password is checking
     * @param password - users password
     * @throws AuthenticationException if password is invalid
     */
    void checkPasswordValidity(User user, Password password) throws AuthenticationException;


    /**
     * Adds logged in user
     * @param key - users unique Access key
     * @param user - logged in user
     */
    void addActiveUser(AccessKey key, User user);
}
