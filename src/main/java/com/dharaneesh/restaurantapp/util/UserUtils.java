package com.dharaneesh.restaurantapp.util;

import com.dharaneesh.restaurantapp.dto.UserDTO;
import com.dharaneesh.restaurantapp.service.UserService;
import com.dharaneesh.restaurantapp.data.entity.employee.Employee;

public class UserUtils {
    private UserUtils() {}

    public static UserDTO getUserData(UserService userService, String email) {
        Employee user = userService.getUserByEmail(email);

        UserDTO userData = userService.getUserFromEntity(user);

        return userData;
    }
}
