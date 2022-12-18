package ru.javaops.finaltask.restaurantvoting.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.javaops.finaltask.restaurantvoting.model.Dish;
import ru.javaops.finaltask.restaurantvoting.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

public interface DishRepository extends BaseRepository<Dish>{

    @Transactional(readOnly = true)
    List<Dish> findByDateAndRestaurant(LocalDate date, Restaurant restaurant);
}
