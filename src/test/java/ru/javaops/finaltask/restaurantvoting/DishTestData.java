package ru.javaops.finaltask.restaurantvoting;

import ru.javaops.finaltask.restaurantvoting.model.Dish;

import static ru.javaops.finaltask.restaurantvoting.RestaurantTestData.firstRestaurant;
import static ru.javaops.finaltask.restaurantvoting.RestaurantTestData.secondRestaurant;

public class DishTestData {
    public static final Dish FRIED_FISH = new Dish(1,"Fried_Fish",150, firstRestaurant);
    public static final Dish FRIED_POTATOES = new Dish(2,"Fried_POTATOES",120, firstRestaurant);
    public static final Dish SALAD = new Dish(3,"Salad",100, secondRestaurant);
    public static final Dish ICE_CREAM = new Dish(4,"Salad",90, secondRestaurant);
}
