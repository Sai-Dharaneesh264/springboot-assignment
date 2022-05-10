package com.dharaneesh.restaurantapp.exception;

public class TableNotFoundException extends NotFoundException{
    public TableNotFoundException(String message) {
        super(message);
    }
}
