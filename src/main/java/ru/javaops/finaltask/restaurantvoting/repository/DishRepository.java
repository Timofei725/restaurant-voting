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
    void update(int id,String newName, LocalDate newDate,int price,int restaurantId);
    List<Dish> getDishesByRestaurantIdAndAndDate(int restaurantId, LocalDate date);

    Optional<Dish> getByRestaurantIdAndId(int restaurantId, int dishId);
    List<Dish> getDishesByRestaurantId(int restaurantId);
}
