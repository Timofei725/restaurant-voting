package ru.javaops.finaltask.restaurantvoting.web.restaurants;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javaops.finaltask.restaurantvoting.model.Dish;
import ru.javaops.finaltask.restaurantvoting.repository.DishRepository;
import ru.javaops.finaltask.restaurantvoting.service.RestaurantService;
import ru.javaops.finaltask.restaurantvoting.util.valodation.ValidationUtil;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

import static ru.javaops.finaltask.restaurantvoting.util.valodation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminDishController {
    static final String REST_URL = "/api/admin/restaurants/{restaurantId}/dishes";

    private DishRepository dishesRepository;
    private RestaurantService restaurantService;
    @Autowired
    public AdminDishController(DishRepository dishesRepository, RestaurantService restaurantService) {
        this.dishesRepository = dishesRepository;
        this.restaurantService = restaurantService;
    }


    @GetMapping("/{dishId}")
    public Dish get(@PathVariable int restaurantId,@PathVariable int dishId) {
        log.info("get dish {} for restaurant {}", dishId, restaurantId);
        return dishesRepository.getByRestaurantIdAndId(restaurantId,dishId).orElseThrow();
    }
    @DeleteMapping("/{dishId}")
    @CacheEvict(value = "restaurants", allEntries = true)
    public void delete(@PathVariable int dishId, @PathVariable String restaurantId) {
        log.info("delete dish {} from restaurant {}", dishId,restaurantId);
        dishesRepository.deleteExisted(dishId);
    }

    @GetMapping()
    public List<Dish> getAll(@PathVariable int restaurantId) {
        log.info("getALl dishes for restaurant {}",restaurantId);
        return dishesRepository.getDishesByRestaurantId(restaurantId);
    }
    @GetMapping("/date")
    public List<Dish> getDishesByDate(@RequestParam(value="for-date") String date,@PathVariable int restaurantId) {
        log.info("get restaurant id - {} menu for date: {}",restaurantId, date);
        LocalDate localDate=LocalDate.parse(date);
        return dishesRepository.getDishesByRestaurantIdAndAndDate(restaurantId,localDate);
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(value = "restaurants", allEntries = true)
    public Dish create(@RequestBody @Valid Dish dish, @PathVariable int restaurantId) {
        log.info("create {} for restaurant {}", dish, restaurantId);
        checkNew(dish);
        restaurantService.setRestaurant(dish,restaurantId);
        return dishesRepository.save(dish);
    }
    @PutMapping(value = "/{dishId}",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "restaurants", allEntries = true)
    public void update(@RequestBody @Valid Dish dish, @PathVariable int dishId, @PathVariable int restaurantId) {
        log.info("update {} with id={}", dish, dishId);
        ValidationUtil.assureIdConsistent(dish,restaurantId);
        dishesRepository.save(dish);
    }
}


