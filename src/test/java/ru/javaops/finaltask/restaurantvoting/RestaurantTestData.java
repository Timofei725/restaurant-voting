package ru.javaops.finaltask.restaurantvoting;

import ru.javaops.finaltask.restaurantvoting.model.Restaurant;

public class RestaurantTestData {
    public static final int FIRST_RESTAURANT_ID = 1;
    public static final int SECOND_RESTAURANT_ID = 2;

    public static final Restaurant firstRestaurant = new Restaurant(FIRST_RESTAURANT_ID, "First_Restaurant");
    public static final Restaurant secondRestaurant = new Restaurant(SECOND_RESTAURANT_ID, "Second_Restaurant");


    public static Restaurant getNew() {
        return new Restaurant( null,"New");
    }
}
