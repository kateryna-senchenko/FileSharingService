package com.javaclasses.filesharingservice.services;

/**
 * Custom exception to indicate that users can not have identical email addresses
 */
public class DuplicateUserEmailException extends Exception {

    public DuplicateUserEmailException(String message){
        super(message);
    }
}
