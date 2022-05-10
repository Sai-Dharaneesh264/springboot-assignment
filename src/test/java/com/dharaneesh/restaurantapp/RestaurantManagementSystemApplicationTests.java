package com.dharaneesh.restaurantapp;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import com.dharaneesh.restaurantapp.dto.Name;
import org.mockito.Mockito;
@SpringBootTest
class RestaurantManagementSystemApplicationTests {


    @Test
    public void checkName() {
        Name name = new Name("sai", "dharaneesh");
        Assert.assertEquals(name.getFirstName(), "sai");
        Assert.assertEquals(name.getLastName(), "dharaneesh");
    }

}
