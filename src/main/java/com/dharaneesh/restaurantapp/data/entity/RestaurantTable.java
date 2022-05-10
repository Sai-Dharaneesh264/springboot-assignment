package com.dharaneesh.restaurantapp.data.entity;

import com.sun.istack.NotNull;
import com.dharaneesh.restaurantapp.data.TableStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "restaurant_tables")
@Data
@NoArgsConstructor
public class RestaurantTable {
    @NotNull
    @Id
    @Column(name = "TABLE_ID")
    private long tableId;

    @Column(name = "TABLE_CAPACITY")
    private int capacity;

    @Column(name = "TABLE_STATUS")
    private TableStatus status;
}
