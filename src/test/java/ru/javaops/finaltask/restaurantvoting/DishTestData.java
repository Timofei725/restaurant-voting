package ru.javaops.finaltask.restaurantvoting;

import ru.javaops.finaltask.restaurantvoting.model.Dish;

import java.time.LocalDate;

import static ru.javaops.finaltask.restaurantvoting.RestaurantTestData.*;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingEqualsComparator(Dish.class);

    public static final Dish FRIED_FISH = new Dish(1,"Fried_Fish",150, firstRestaurant);
    public static final Dish FRIED_POTATOES = new Dish(2,"Fried_POTATOES",120, firstRestaurant);
    public static final Dish SALAD = new Dish(3,"Salad",100, secondRestaurant);
    public static final Dish ICE_CREAM = new Dish(4,"Salad",90, secondRestaurant);
    public static final Dish GARLIC_BREAD = new Dish(5,"Garlic_Bread",85, LocalDate.of(2019,06,29), thirdRestaurant);
    public static final Dish NACHOS = new Dish(6,"Nachos",120, LocalDate.of(2019,06,29), thirdRestaurant);
    public static final Dish SPAGHETTI = new Dish(7,"Spaghetti",14, LocalDate.of(2019,06,29), thirdRestaurant);
public static Dish createNew(){
    return new Dish(null,"new_dish_for_test",200,LocalDate.of(2019,06,29),null);
}

    public static Dish getUpdated() {
    Dish updated=new Dish(FRIED_FISH);
         updated.setDate(LocalDate.of(2019,06,29));
        updated.setName("new_dish_for_test");
        return updated;
    }
}
