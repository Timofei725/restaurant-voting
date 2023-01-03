package ru.javaops.finaltask.restaurantvoting.web.restaurants;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.javaops.finaltask.restaurantvoting.model.Restaurant;
import ru.javaops.finaltask.restaurantvoting.service.RestaurantService;
import ru.javaops.finaltask.restaurantvoting.service.VoteService;
import ru.javaops.finaltask.restaurantvoting.web.AuthUser;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantVotingController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "restaurants")
public class RestaurantVotingController {

    static final String REST_URL = "/api/restaurants";
    private RestaurantService restaurantService;

    private VoteService voteService;
    @Autowired
    public RestaurantVotingController(RestaurantService restaurantService, VoteService voteService) {
        this.restaurantService = restaurantService;
        this.voteService = voteService;
    }

    @GetMapping
    @Cacheable
    public List<Restaurant> getAll() {
        log.info("getALl");
        LocalDate localDate=LocalDate.now();
        return restaurantService.getRestaurantsWithDateMenu(localDate);
    }
    @PostMapping("/{id}/vote")
    public String doVote(@PathVariable Integer id,@AuthenticationPrincipal AuthUser authUser) {
        log.info("user {} doVote for restaurant{}",authUser.getUser().id(),id);
        return voteService.doVote( authUser.getUser().id(),id)==true?"Your vote have been done or changed":
                "You have already voted and you can't change your vote after 11:00";
    }


}

