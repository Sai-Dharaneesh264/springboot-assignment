package com.dharaneesh.restaurantapp.service;

import com.dharaneesh.restaurantapp.data.entity.Item;
import com.dharaneesh.restaurantapp.data.entity.Order;
import com.dharaneesh.restaurantapp.data.repository.ItemRepository;
import com.dharaneesh.restaurantapp.data.repository.OrderRepository;
import com.dharaneesh.restaurantapp.data.repository.ProductRepository;
import com.dharaneesh.restaurantapp.dto.OrderDTO;
import com.dharaneesh.restaurantapp.dto.OrderItemDTO;
import com.dharaneesh.restaurantapp.dto.stat.OrderStats;
import com.dharaneesh.restaurantapp.dto.stat.Stats;
import com.dharaneesh.restaurantapp.exception.OrderNotFoundException;
import com.dharaneesh.restaurantapp.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.*;
import java.time.temporal.ChronoField;
import java.util.*;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepo;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductService productService;
    @Autowired
    TableService tableService;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    EntityManager em;

    public void saveOrder(Order order) {
        orderRepo.save(order);
    }
    
    @Transactional
    public Order saveOrderFromData(OrderDTO orderData) {
        try {
            Set<Item> orderItems = new HashSet<>();
            List<OrderItemDTO> rawItems = orderData.getItems();

            Order order = new Order();
            double totalPrice = 0;

            Item item;
            for (OrderItemDTO itemDTO : rawItems) {
                item = new Item();

                item.setProduct(itemDTO.getProduct());
                item.setQuantity(itemDTO.getQuantity());
                item.setOrder(order);

                totalPrice = totalPrice + item.getProduct().getPrice() * item.getQuantity();

                orderItems.add(item);
            }

            productService.handleStocks(orderItems);

            order.setTotalPrice(totalPrice);
            order.setTable(orderData.getTable());
            order.setOrderDate(LocalDateTime.ofInstant(orderData.getOrderDate().toInstant(), ZoneId.systemDefault()));
            order.setItems(orderItems);

            em.persist(order);

            saveOrder(order);

            return order;
        }
        catch (Exception e) {
            throw e;
        }
    }

    public Order getById(long id) throws NotFoundException {
        if(orderRepo.existsById(id)) {
            return orderRepo.findById(id).get();
        }
        else throw new OrderNotFoundException("No such order with id: '" + id + "'");
    }

    public List<Order> getAll() {
        return orderRepo.findAll();
    }

    public void deleteOrder(long id) throws NotFoundException {
        if(orderRepo.existsById(id)) {
            orderRepo.deleteById(id);
        }
        else throw new OrderNotFoundException("No such order with id: '" + id + "'");
    }

    public List<Order> getOrdersByInterval(LocalDate start, LocalDate end) {
        if(start.isAfter(LocalDate.now())) {
            return new ArrayList<>();
        }
        System.out.println(LocalDateTime.of(start, LocalTime.MIDNIGHT));
        System.out.println(LocalDateTime.of(end, LocalTime.MIDNIGHT));
        return orderRepo.findByOrderDateBetween(LocalDateTime.of(start, LocalTime.MIDNIGHT),  LocalDateTime.of(end, LocalTime.MIDNIGHT));
    }
    public long countOrdersByInterval(LocalDate start, LocalDate end) {
        if(start.isAfter(LocalDate.now())) {
            return 0;
        }
        return orderRepo.countByOrderDateBetween(LocalDateTime.of(start, LocalTime.MIDNIGHT),  LocalDateTime.of(end,LocalTime.MIDNIGHT));
    }

    public List<Order> getOrdersFromDateUntilNow(LocalDate date) {
        if(date.isAfter(LocalDate.now())) {
            return new ArrayList<>();
        }
        return orderRepo.findByOrderDateBetween(LocalDateTime.of(date, LocalTime.MIDNIGHT),  LocalDateTime.now());
    }

    public long countOrdersFromDateUntilNow(LocalDate date) {
        if(date.isAfter(LocalDate.now())) {
            return 0;
        }
        return orderRepo.countByOrderDateBetween(LocalDateTime.of(date, LocalTime.MIDNIGHT),  LocalDateTime.now());
    }

    public Stats getStats() {
        LocalDate startOfToday = LocalDateTime.now(ZoneId.systemDefault()).toLocalDate();

        LocalDate startOfYesterday = startOfToday.minusDays(1);
        LocalDate startOfCurrentWeek = startOfToday.with(ChronoField.DAY_OF_WEEK, 1);
        LocalDate startOfCurrentMonth = startOfToday.withDayOfMonth(1);
        LocalDate startOfLastWeek = startOfToday.with(DayOfWeek.MONDAY).minusWeeks(1);
        LocalDate startOfLastMonth = startOfToday.withDayOfMonth(1).minusMonths(1);

        OrderStats orderStats = new OrderStats();

        // Total count
        orderStats.setTotalCount(orderRepo.count());

        orderStats.setOrderCountToday(countOrdersFromDateUntilNow(startOfToday));
        orderStats.setOrderCountYesterday(countOrdersByInterval(startOfYesterday, startOfToday));

        orderStats.setOrderCountCurrentMonth(countOrdersFromDateUntilNow(startOfCurrentMonth));
        orderStats.setOrderCountCurrentWeek(countOrdersFromDateUntilNow(startOfCurrentWeek));

        orderStats.setOrderCountLastWeek(countOrdersByInterval(startOfLastWeek, startOfCurrentWeek));
        orderStats.setOrderCountLastMonth(countOrdersByInterval(startOfLastMonth, startOfCurrentMonth));

        // Total cash
        orderStats.setCashToday(getOrdersFromDateUntilNow(startOfToday).stream().mapToDouble(Order::getTotalPrice).sum());
        orderStats.setCashYesterday(getOrdersByInterval(startOfYesterday,startOfToday).stream().mapToDouble(Order::getTotalPrice).sum());

        orderStats.setCashCurrentWeek(getOrdersFromDateUntilNow(startOfCurrentWeek).stream().mapToDouble(Order::getTotalPrice).sum());
        orderStats.setCashCurrentMonth(getOrdersFromDateUntilNow(startOfCurrentMonth).stream().mapToDouble(Order::getTotalPrice).sum());

        orderStats.setCashLastWeek(getOrdersByInterval(startOfLastWeek,startOfCurrentWeek).stream().mapToDouble(Order::getTotalPrice).sum());
        orderStats.setCashLastMonth(getOrdersByInterval(startOfLastMonth,startOfCurrentMonth).stream().mapToDouble(Order::getTotalPrice).sum());

        return orderStats;
    }

    public Set<Order> getOrdersItemsContains(String name) {
        List<Item> itemsWithName = itemRepository.findAllByProduct_Name(name);

        Set<Order> orders = new HashSet<>();

        if( itemsWithName.size() == 0) return Set.of();

        for (Item item : itemsWithName) {
            orders.addAll(orderRepo.findOrdersByItemsContaining(item));
        }

        return orders;
    }

}
