package com.javaclasses.filesharingservice.services;

/**
 * Custom exception to indicate that user entered invalid email and password
 */
public class AuthenticationException extends Exception{

    public AuthenticationException(String message){
        super(message);
    }
}
