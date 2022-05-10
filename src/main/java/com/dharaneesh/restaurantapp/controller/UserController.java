package com.dharaneesh.restaurantapp.controller;

import com.dharaneesh.restaurantapp.dto.PhoneNumber;
import com.dharaneesh.restaurantapp.data.UserRole;
import com.dharaneesh.restaurantapp.data.entity.employee.Employee;
import com.dharaneesh.restaurantapp.dto.Address;
import com.dharaneesh.restaurantapp.dto.Name;
import com.dharaneesh.restaurantapp.dto.StringResponse;
import com.dharaneesh.restaurantapp.dto.UserDTO;
import com.dharaneesh.restaurantapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;


    @PostMapping(path = "api/user/save")
    @ResponseBody
    public Object saveEmployee(@RequestBody UserDTO emp) {
         try {
             Employee result = userService.saveUser(emp);
             return ResponseEntity.ok().body(new StringResponse("Employee saved successfully. ID: " +result.getEmployeeId()));
         } catch (Exception e) {
             return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
         }
    }

    @GetMapping(path = "api/user/{id}/details")
    @ResponseBody
    public Object getUserDetails(@PathVariable long id, HttpServletRequest request) {
         ModelAndView mav = new ModelAndView();
        try {
            Employee user = userService.getUserById(id);
            UserDTO requestingUserData = userService.getUserFromEntity(userService.getUserByEmail(request.getRemoteUser()));

            mav.getModel().put("userDetails", user);
            mav.getModel().put("user",requestingUserData);

            mav.setViewName("app/details/user_details");

            return mav;
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }

    @PostMapping(path = "api/user")
    @ResponseBody
    public Object getEmployeeByNumber(@RequestParam("email") String email) {
        try {
            return ResponseEntity.ok().body(userService.getUserByEmail(email));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }

    @DeleteMapping(path = "api/user/{id}")
    @ResponseBody
    public Object deleteUser(@PathVariable("id") long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(new StringResponse("User with ID: "+id+" has deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }

    @GetMapping(path = "api/user/role")
    @ResponseBody
    public ResponseEntity<List<Employee>> getUsersByRole(@RequestParam("role") String role) {
        UserRole userRole = UserRole.valueOf(role.toUpperCase());

        return ResponseEntity.ok(userService.getUsersByRole(userRole));
    }

    @GetMapping(path = "api/user/all")
    @ResponseBody
    public ResponseEntity<List<Employee>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping(path = "/register")
    @ResponseBody
    public ModelAndView registerUser(@ModelAttribute("appUser") UserDTO user, HttpServletRequest request) {
        try {
            Employee employee = userService.saveUser(user);
            request.logout();
            return new ModelAndView("redirect:/login");
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("redirect:/signup", "error", e.getMessage());
        }
    }

    @PostMapping(path = "/api/user/{id}/role")
    @ResponseBody
    public Object assignUserRole(@PathVariable long id, @RequestBody String role) {
        try {
            UserRole parsedRole = UserRole.valueOf(role.toUpperCase().replaceAll(" ","_"));
            userService.assignRole(id, parsedRole);
            return ResponseEntity.ok(new StringResponse("Role assigned successfully."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }

    @PostMapping(path = "/user/{id}/name")
    @ResponseBody
    public Object changeUserName(@PathVariable long id, @RequestBody Name name) {
        try {
            userService.changeName(id, name);
            return ResponseEntity.ok(new StringResponse("Name changed successfully."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }

    @PostMapping(path = "/user/{id}/email")
    @ResponseBody
    public Object changeEmail(@PathVariable long id, @RequestBody String email, HttpServletRequest request) {
        try {
            request.logout();
            userService.changeEmail(id, email);
            return ResponseEntity.ok(new StringResponse("Email changed successfully."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }


    @PostMapping(path = "/user/{id}/password")
    @ResponseBody
    public Object changePassword(@PathVariable long id, @RequestBody String password, HttpServletRequest request) {
        try {
            request.logout();
            userService.changePassword(id, password);
            return ResponseEntity.ok(new StringResponse("Password has updated successfully."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }


}
