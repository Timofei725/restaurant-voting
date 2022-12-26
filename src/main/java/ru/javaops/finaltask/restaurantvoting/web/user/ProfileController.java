package ru.javaops.finaltask.restaurantvoting.web.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.javaops.finaltask.restaurantvoting.model.User;
import ru.javaops.finaltask.restaurantvoting.repository.UserRepository;
import ru.javaops.finaltask.restaurantvoting.web.AuthUser;

import javax.validation.Valid;

import static ru.javaops.finaltask.restaurantvoting.util.ValidationUtil.assureIdConsistent;
import static ru.javaops.finaltask.restaurantvoting.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = ProfileController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class ProfileController {
    static final String REST_URL = "/api/profile";
    private final UserRepository userRepository;
@Autowired
    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping
    public User get(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get authorized user {}",authUser.getUser());
        return authUser.getUser();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser) {
        log.info("delete authorized user {}",authUser.getUser());
        userRepository.deleteExisted(authUser.id());
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@Valid @RequestBody User user) {
        log.info("register {}", user);
        checkNew(user);
      return userRepository.save(user);
     }
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@RequestBody @Valid User user, @AuthenticationPrincipal AuthUser authUser) {
        assureIdConsistent(user, authUser.id());
        userRepository.update(authUser.id(),user.getName(),user.getEmail(),user.getPassword());
    }

}
