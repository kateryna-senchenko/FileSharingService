package com.javaclasses.filesharingservice.dao;

import com.google.common.base.Preconditions;
import com.javaclasses.filesharingservice.services.AuthenticationException;
import com.javaclasses.filesharingservice.services.DuplicateUserEmailException;
import com.javaclasses.filesharingservice.services.customdatatypes.*;
import com.javaclasses.filesharingservice.dao.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implementation of the UserRepository interface
 */
public class UserRepositoryImpl implements UserRepository {

    private static final Logger log = LoggerFactory.getLogger(UserRepositoryImpl.class);

    private long count = 0;

    private Map<Long, User> users = new HashMap<Long, User>() {{

        put(count, new User(count, new Email("smth@gmail.com"), new Password("mypassword"),
                new FirstName("Kolin"), new LastName("Farrel")));


    }};

    private Map<AccessKey, User> activeUsers = new HashMap<AccessKey, User>(){{
        put(new AccessKey(1),  new User(count++, new Email("smth@gmail.com"), new Password("mypassword"),
                new FirstName("Kolin"), new LastName("Farrel")));
    }};

    @Override
    public void createUser(Email email, Password password, FirstName firstName, LastName lastName)
            throws DuplicateUserEmailException {


        checkNotNull(email, "Email should not be null");
        checkNotNull(password, "Password should not be null");
        checkNotNull(firstName, "First Name should not be null");
        checkNotNull(lastName, "Last name should not be null");


        if(isEmailUnique(getAllEmails(), email)){

            User newUser = new User(count++, email, password, firstName, lastName);

            users.put(newUser.getId(), newUser);

            if(log.isInfoEnabled()){
                log.info("Created new user with email {}", email.getEmail());
            }

        } else {
            log.error("Fail creating new user. User with email {} is already exist", email.getEmail());
            throw new DuplicateUserEmailException("User with specified email address is already exist. Try another");
        }


    }

    @Override
    public User findUserByID(long id){

        checkNotNull(id, "Users id should not be null");

        User requiredUser = null;

        requiredUser = users.get(id);

        if(requiredUser == null){
            log.error("Cannot find user with id {}", id);
            throw new IllegalArgumentException("Cannot find user with id " + id);
        }

        if(log.isDebugEnabled()){
            log.debug("Found user with id {}", id);
        }

        return requiredUser;
    }

    @Override
    public User findUserByEmail(Email email) throws AuthenticationException {

        checkNotNull(email, "Email should not be null");

        User requiredUser = null;

        for(User user: users.values()){
            if (email.equals(user.getEmail())){
                requiredUser = user;

                if(log.isInfoEnabled()){
                    log.info("Found user with email {}", email.getEmail());
                }
                break;
            }
        }

        if(requiredUser == null){
            log.error("Cannot find user with email {}", email.getEmail());
            throw new AuthenticationException("Cannot find user with specified email");
        }

        return requiredUser;
    }

    @Override
    public void checkPasswordValidity(User user, Password password) throws AuthenticationException {

        checkNotNull(user, "User should not be null");
        checkNotNull(password, "Password should not be null");

        if(!user.getPassword().equals(password)){
            log.error("Password {} is invalid", password.getPassword());
            throw new AuthenticationException("Invalid password");
        }

        if(log.isInfoEnabled()){
            log.info("Password {} is valid", password.getPassword());
        }


    }

    @Override
    public void addActiveUser(AccessKey key, User user) {

        checkNotNull(key, "Access key should not be null");
        checkNotNull(user, "User should not be null");

        activeUsers.put(key, user);
    }

    @Override
    public User findActiveUserByAccessKey(AccessKey key) {

        checkNotNull(key, "Access key should not be null");

        User user = activeUsers.get(key);

        return user;
    }


    private Set<Email> getAllEmails() {

        Set<Email> emails = new HashSet<Email>();

        if(!users.isEmpty()){

            for(User user: users.values()){
                emails.add(user.getEmail());
            }

        }
        return emails;
    }


    private boolean isEmailUnique(Set<Email> emails, Email newEmail){

        for(Email email: emails){
            if(email.equals(newEmail)){
                return false;
            }
        }

        return true;
    }


}
