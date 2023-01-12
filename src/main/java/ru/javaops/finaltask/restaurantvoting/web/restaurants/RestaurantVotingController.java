package ru.javaops.finaltask.restaurantvoting.web.restaurants;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.javaops.finaltask.restaurantvoting.model.Restaurant;
import ru.javaops.finaltask.restaurantvoting.model.Vote;
import ru.javaops.finaltask.restaurantvoting.service.RestaurantService;
import ru.javaops.finaltask.restaurantvoting.service.VoteService;
import ru.javaops.finaltask.restaurantvoting.util.validation.ValidationUtil;
import ru.javaops.finaltask.restaurantvoting.web.AuthUser;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = RestaurantVotingController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "restaurants")
@AllArgsConstructor
public class RestaurantVotingController {

    static final String REST_URL = "/api/restaurants";
    private RestaurantService restaurantService;

    private VoteService voteService;

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        log.info("get {}", id);
        return ResponseEntity.of(restaurantService.getRestaurantWithDateMenu(LocalDate.now(), id));
    }

    @GetMapping
    @Cacheable
    public List<Restaurant> getAll() {
        log.info("getALl");
        LocalDate localDate = LocalDate.now();
        return restaurantService.getRestaurantsWithDateMenu(localDate);
    }

    @PostMapping("/{id}/vote")
    public Vote doVote(@PathVariable int id, @AuthenticationPrincipal AuthUser authUser) {
        log.info("user {} is trying to vote for restaurant {}", authUser.getUser().id(), id);
        ValidationUtil.checkRestaurantId(restaurantService.getById(id), id);
        Optional<Vote> vote = voteService.doVote(authUser.getUser().id(), id);
        ValidationUtil.checkVote(vote);
        return vote.get();

    }


}

