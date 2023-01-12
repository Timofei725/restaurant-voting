package ru.javaops.finaltask.restaurantvoting.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.finaltask.restaurantvoting.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {


    @Query("SELECT DISTINCT r from Restaurant r JOIN FETCH r.menu mi WHERE mi.date=:date ORDER BY r.name ASC")
    List<Restaurant> getRestaurantsWithDateMenu(LocalDate date);

    @Query("SELECT r from Restaurant r JOIN FETCH r.menu mi WHERE r.id=:id AND mi.date=:date")
    Optional<Restaurant> getRestaurantWithDateMenu(LocalDate date, int id);

    @Transactional
    @Modifying
    @Query("UPDATE Restaurant r set r.name=?2 where r.id=?1")
    void update(int id, String newName);

}




