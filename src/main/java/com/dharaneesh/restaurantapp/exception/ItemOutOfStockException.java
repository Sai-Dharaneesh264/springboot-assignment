package com.dharaneesh.restaurantapp.exception;

public class ItemOutOfStockException extends RuntimeException{
    public ItemOutOfStockException(String message) {
        super(message);
    }
}
