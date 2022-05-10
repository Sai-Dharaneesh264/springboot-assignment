package com.dharaneesh.restaurantapp;

import com.dharaneesh.restaurantapp.data.repository.OrderRepository;
import com.dharaneesh.restaurantapp.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;z
import org.springframework.ui.Model;

@SpringBootTest
class RestaurantManagementSystemApplicationTests {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Mock
    private Model model;
    @Test
    void contextLoads() {
    }

    void findAllOrders() {
        when(orderRepository.findAll().thenReturn(
                Stream
        ))
    }

}
