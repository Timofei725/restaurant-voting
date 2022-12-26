package ru.javaops.finaltask.restaurantvoting.service;

import org.springframework.stereotype.Service;
import ru.javaops.finaltask.restaurantvoting.model.Dish;
import ru.javaops.finaltask.restaurantvoting.model.Restaurant;
import ru.javaops.finaltask.restaurantvoting.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;


    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Optional<Restaurant> getRestaurantWithDateMenu(LocalDate date, Integer id) {
        Optional<Restaurant> restaurant=  restaurantRepository.getRestaurantWithMenu(id);
        restaurant.get().setMenu(restaurant.get().getMenu().stream().
                filter(x->x.getDate().isEqual(date)).collect(Collectors.toList()));
        return restaurant;
    }

    public List<Restaurant> getRestaurantsWithDateMenu(LocalDate date) {
        List<Restaurant> restaurants=  restaurantRepository.getRestaurantsWithMenu();
        restaurants.stream().forEach(r->r.setMenu(r.getMenu().stream().
                filter(d->d.getDate().isEqual(date)).collect(Collectors.toList())));
        return restaurants;
    }

    public void delete(Integer id) {
        restaurantRepository.deleteById(id);
    }

    public List<Restaurant> getAll() {
     return    restaurantRepository.findAll();
    }

    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }



    public void update(Restaurant restaurant,Integer id) {
        restaurantRepository.update(id,restaurant.getName());
    }

    public Dish setRestaurant(Dish dish,Integer restaurantId) {
        //it was gerReferenceById
       dish.setRestaurant(restaurantRepository.getById(restaurantId));
       return dish;

    }
}
