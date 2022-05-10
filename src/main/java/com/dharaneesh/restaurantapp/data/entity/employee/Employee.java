package com.dharaneesh.restaurantapp.data.entity.employee;

import com.dharaneesh.restaurantapp.data.UserRole;
import com.dharaneesh.restaurantapp.dto.Address;
import com.dharaneesh.restaurantapp.dto.Name;
import com.dharaneesh.restaurantapp.dto.PhoneNumber;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPLOYEE_ID")
    private long employeeId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "firstName", column = @Column(name = "FIRST_NAME")),
            @AttributeOverride( name = "lastName", column = @Column(name = "LAST_NAME"))
    })
    private Name name;

    @NotNull
    @Column(name = "EMAIL")
    private String email;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private UserRole role;



    @Column(name = "LAST_LOGIN")
    private Date lastLoginDate;

    @Column(name = "LAST_LOGOUT")
    private Date lastLogoutDate;

    @Column(name = "PASSWORD")
    private String password;

}
