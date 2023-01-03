package ru.javaops.finaltask.restaurantvoting;

import ru.javaops.finaltask.restaurantvoting.model.Restaurant;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingEqualsComparator(Restaurant.class);
    public static final int FIRST_RESTAURANT_ID = 1;
    public static final int SECOND_RESTAURANT_ID = 2;
    public static final int THIRD_RESTAURANT_ID = 3;
    public static final int NOT_FOUND = 1001;

    public static final Restaurant firstRestaurant = new Restaurant(FIRST_RESTAURANT_ID, "First_Restaurant");
    public static final Restaurant secondRestaurant = new Restaurant(SECOND_RESTAURANT_ID, "Second_Restaurant");
    public static final Restaurant thirdRestaurant = new Restaurant(THIRD_RESTAURANT_ID, "Third_Restaurant");


    public static Restaurant getUpdated() {

        return new Restaurant(FIRST_RESTAURANT_ID, "FirstUpdatedRestaurant");
    }
    public static Restaurant getNew() {
        return new Restaurant( null,"newRestaurant");
    }
}
