package ru.javaops.finaltask.restaurantvoting.web.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.javaops.finaltask.restaurantvoting.model.User;
import ru.javaops.finaltask.restaurantvoting.util.UserUtil;
import ru.javaops.finaltask.restaurantvoting.util.validation.ValidationUtil;

import javax.validation.Valid;
import java.util.List;

import static ru.javaops.finaltask.restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = AdminUserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class AdminUserController extends AbstractUserController {
    static final String REST_URL = "/api/admin/users";




    @GetMapping
    public List<User> getAll() {
        log.info("getAll");
        return super.repository.findAll(Sort.by(Sort.Direction.ASC, "name", "email"));
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable int id) {

        return super.get(id);

    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public User create(@RequestBody @Valid User user) {
        log.info("creat user {}", user);
        ValidationUtil.checkNew(user);
        return super.prepareAndSave(UserUtil.prepareToSave(user)).orElseThrow();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid User user, @PathVariable int id) {
        log.info("update user {}, id {}", user, id);
        assureIdConsistent(user, id);

        prepareAndSave(user).orElseThrow();
    }


    @GetMapping("/by-email")
    public User getByMail(@RequestParam String email) {
        return super.repository.getByEmail(email).orElse(null);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void enable(@PathVariable int id, @RequestParam boolean enabled) {
        log.info(enabled ? "enable {}" : "disable {}", id);
        User user = repository.getById(id);
        user.setEnabled(enabled);
    }
}
