package com.dharaneesh.restaurantapp.exception;

public class ItemConflictException extends ConflictException{
    public ItemConflictException(String message) {
        super(message);
    }
}
