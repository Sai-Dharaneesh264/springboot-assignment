package com.dharaneesh.restaurantapp.security;

import com.dharaneesh.restaurantapp.data.UserRole;
import com.dharaneesh.restaurantapp.data.entity.employee.Employee;
import com.dharaneesh.restaurantapp.dto.Address;
import com.dharaneesh.restaurantapp.dto.Name;
import com.dharaneesh.restaurantapp.dto.PhoneNumber;
import com.dharaneesh.restaurantapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class InitialDataLoader {
    private UserService userService;

    @Autowired
    public InitialDataLoader(UserService userService) {
        this.userService = userService;

        createAdminAccount();
    }

    private void createAdminAccount() {
        if(userService.countAll() == 0) {
            Employee admin = new Employee();

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            admin.setName(new Name("admin", "admin"));
            admin.setEmail("admin@account.com");
            admin.setPassword(encoder.encode("administrator"));
            admin.setRole(UserRole.ADMIN);

            userService.saveUser(admin);
        }
    }
}
