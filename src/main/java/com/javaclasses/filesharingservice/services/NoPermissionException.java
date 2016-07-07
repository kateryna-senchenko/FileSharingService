package com.javaclasses.filesharingservice.services;

/**
 * Custom exception to indicate that user does not have necessary permission
 */
public class NoPermissionException extends Exception{

    public NoPermissionException(String message){
        super(message);
    }
}
