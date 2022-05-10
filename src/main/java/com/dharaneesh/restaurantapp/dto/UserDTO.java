package com.dharaneesh.restaurantapp.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String firstName;
    private String lastName;

    private String email;
    private String password;

    private String lastLoginDate;
    private String lastLogoutDate;

    private String role;
}
