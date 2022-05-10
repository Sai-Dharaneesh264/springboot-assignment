package com.dharaneesh.restaurantapp.exception;

public class OrderNotFoundException extends NotFoundException{
    public OrderNotFoundException(String message) {
        super(message);
    }
}
