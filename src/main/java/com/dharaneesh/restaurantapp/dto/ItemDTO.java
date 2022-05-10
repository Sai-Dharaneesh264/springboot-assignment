package com.dharaneesh.restaurantapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ItemDTO {
    private String name;
    private String description;
    private String type;
    private double price;
}
