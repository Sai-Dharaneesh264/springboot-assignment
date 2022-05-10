package com.dharaneesh.restaurantapp.data;

public enum ItemType {
    MEAL("Meal"), BEVERAGE("Beverage"), DESSERT("Dessert");

    String typeText;

    ItemType(String typeText) {
        this.typeText = typeText;
    }

    public String getTypeText() {
        return this.typeText;
    }
}
