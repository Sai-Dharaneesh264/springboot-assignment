package com.dharaneesh.restaurantapp.data.repository;

import com.dharaneesh.restaurantapp.data.entity.Item;
import com.dharaneesh.restaurantapp.data.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByOrderDateBetween(LocalDateTime orderDate, LocalDateTime orderDate2);
    long countByOrderDateBetween(LocalDateTime orderDate, LocalDateTime orderDate2);

    List<Order> findOrdersByItemsContaining(Item item);
}
