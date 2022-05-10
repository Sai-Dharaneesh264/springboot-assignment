package com.dharaneesh.restaurantapp.data.repository;

import com.dharaneesh.restaurantapp.data.UserRole;
import com.dharaneesh.restaurantapp.dto.Name;
import com.dharaneesh.restaurantapp.data.entity.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findEmployeeByEmail(String email);
    boolean existsByEmail(String email);

    List<Employee> findEmployeesByRole(UserRole role);
    boolean existsByRole(UserRole role);

    int countEmployeesByRole(UserRole role);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Employee employee set employee.lastLoginDate = :loginDate where employee.email = :email")
    void updateLastLogin(@Param("loginDate") Date loginDate, @Param("email") String email);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Employee employee set employee.lastLogoutDate = :logoutDate where employee.email = :email")
    void updateLastLogout(@Param("logoutDate") Date logoutDate, @Param("email") String email);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Employee employee set employee.name = :name where employee.employeeId = :employeeId")
    void changeName(@Param("employeeId") long employeeId, @Param("name") Name name);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Employee employee set employee.email = :email where employee.employeeId = :employeeId")
    void changeEmail(@Param("employeeId") long employeeId, @Param("email") String email);
    
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Employee employee set employee.role = :employeeRole where employee.employeeId = :employeeId")
    void assignRole(@Param("employeeId") long employeeId, @Param("employeeRole") UserRole employeeRole);
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Employee employee set employee.password = :password where employee.employeeId = :employeeId")
    void updatePassword(@Param("employeeId") long employeeId, @Param("password") String password);
}
