package ru.javaops.finaltask.restaurantvoting.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalTime;

import static ru.javaops.finaltask.restaurantvoting.RestaurantTestData.SECOND_RESTAURANT_ID;
import static ru.javaops.finaltask.restaurantvoting.UserTestData.FIRST_USER_ID;
import static ru.javaops.finaltask.restaurantvoting.UserTestData.SECOND_USER_ID;

class UserServiceTest extends AbstractServiceTest{
    @Autowired
    protected UserService service;
    @Test
    void makeVote(){
        Assertions.assertEquals(true, service.doVote(SECOND_USER_ID,SECOND_RESTAURANT_ID));
        Assertions.assertEquals(true,service.getAll().size()==3);
        Assertions.assertEquals(SECOND_RESTAURANT_ID,service.getById(3).get().getRestaurantId());
    }


    @Test
    void changeVote(){
        service.setTime(LocalTime.of(10,30));
       Assertions.assertEquals(true, service.doVote(FIRST_USER_ID,SECOND_RESTAURANT_ID));
        Assertions.assertEquals(true,service.getAll().size()==2);
        Assertions.assertEquals(SECOND_RESTAURANT_ID,service.getById(2).get().getRestaurantId());
    }

}

