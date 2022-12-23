package ru.javaops.finaltask.restaurantvoting.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.finaltask.restaurantvoting.model.Dish;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish>{

    @Transactional
    @Modifying
    @Query("UPDATE Dish d set d.name=?2,d.date=?3,d.price=?4 ,d.restaurant.id=?5 where d.id=?1")
    void update(Integer id,String newName, LocalDate newDate,Integer price,Integer restaurantId);
    List<Dish> getDishesByRestaurantIdAndAndDate(Integer restaurantId, LocalDate date);

    Optional<Dish> getByRestaurantIdAndId(Integer restaurantId, Integer dishId);
    List<Dish> getDishesByRestaurantId(Integer restaurantId);
}
