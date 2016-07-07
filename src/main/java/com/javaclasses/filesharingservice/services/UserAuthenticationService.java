package com.javaclasses.filesharingservice.services;

import com.javaclasses.filesharingservice.services.customdatatypes.AccessKey;
import com.javaclasses.filesharingservice.services.customdatatypes.Email;
import com.javaclasses.filesharingservice.services.customdatatypes.Password;

/**
 * Public API of user authentication service
 */
public interface UserAuthenticationService {


    /**
     * Signs in an existing user
     * @param email - email address of the user
     * @param password - users password
     * @return unique AccessKey
     * @throws AuthenticationException if authentication fails
     */
    AccessKey signIn(Email email, Password password) throws AuthenticationException;
}
