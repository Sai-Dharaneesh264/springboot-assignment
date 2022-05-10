package com.dharaneesh.restaurantapp.exception;

public class UserConflictException extends ConflictException{
    public UserConflictException(String message) {
        super(message);
    }
}
