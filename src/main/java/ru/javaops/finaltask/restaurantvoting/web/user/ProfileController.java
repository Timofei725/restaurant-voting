package ru.javaops.finaltask.restaurantvoting.web.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.javaops.finaltask.restaurantvoting.model.User;
import ru.javaops.finaltask.restaurantvoting.repository.UserRepository;
import ru.javaops.finaltask.restaurantvoting.web.AuthUser;

import javax.validation.Valid;

import static ru.javaops.finaltask.restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.finaltask.restaurantvoting.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = ProfileController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class ProfileController extends AbstractUserController {
    static final String REST_URL = "/api/profile";
    private final UserRepository userRepository;

    @GetMapping
    public User get(@AuthenticationPrincipal AuthUser authUser) {
        return authUser.getUser();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser) {
        userRepository.deleteExisted(authUser.id());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@Valid @RequestBody User user) {
        log.info("register {}", user);
        checkNew(user);
        return prepareAndSave(user).orElseThrow();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@RequestBody @Valid User user, @AuthenticationPrincipal AuthUser authUser) {
        log.info("update user {}", user);
        assureIdConsistent(user, authUser.id());

        prepareAndSave(user).orElseThrow();
    }

}
