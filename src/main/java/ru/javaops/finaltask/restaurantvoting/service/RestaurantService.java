package ru.javaops.finaltask.restaurantvoting.service;

import org.springframework.stereotype.Service;
import ru.javaops.finaltask.restaurantvoting.model.Dish;
import ru.javaops.finaltask.restaurantvoting.model.Restaurant;
import ru.javaops.finaltask.restaurantvoting.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;


    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Optional<Restaurant> getRestaurantWithDateMenu(LocalDate date, int id) {
        return restaurantRepository.getRestaurantWithDateMenu(date, id);

    }

    public List<Restaurant> getRestaurantsWithDateMenu(LocalDate date) {
        return restaurantRepository.getRestaurantsWithDateMenu(date);

    }

    public void delete(int id) {
        restaurantRepository.deleteExisted(id);
    }

    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }


    public void update(Restaurant restaurant, int id) {
        restaurantRepository.update(id, restaurant.getName());
    }

    public void setRestaurant(Dish dish, int restaurantId) {
        dish.setRestaurant(restaurantRepository.getById(restaurantId));

    }

    public Optional<Restaurant> getById(int id) {
        return restaurantRepository.findById(id);
    }


}
