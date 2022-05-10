package com.dharaneesh.restaurantapp.dto;

import com.dharaneesh.restaurantapp.data.entity.Product;
import lombok.Data;

@Data
public class OrderItemDTO {
    private Product product;
    private int quantity;
}
