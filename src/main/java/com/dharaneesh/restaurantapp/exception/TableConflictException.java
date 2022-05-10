package com.dharaneesh.restaurantapp.exception;

public class TableConflictException extends ConflictException{
    public TableConflictException(String message) {
        super(message);
    }
}
