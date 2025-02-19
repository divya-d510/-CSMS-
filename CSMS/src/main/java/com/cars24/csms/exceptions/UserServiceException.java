package com.cars24.csms.exceptions;


public class UserServiceException extends RuntimeException{
    public UserServiceException(String message) {
        super(message);
    }
}
