package ru.javaops.finaltask.restaurantvoting.web.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javaops.finaltask.restaurantvoting.model.User;
import ru.javaops.finaltask.restaurantvoting.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping(value = AdminUserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminUserController {
     static final String REST_URL ="/api/admin/users" ;
     private UserRepository userRepository;
@Autowired
    public AdminUserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping
    public List<User> getAll() {
    log.info("getAll");
        return userRepository.findAll();
    }


    @GetMapping("/{id}")
    public User get(@PathVariable Integer id) {
        log.info("get user {}",id);
        return userRepository.findById(id).orElse(null);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public User create(@RequestBody User user) {
        log.info("creat user {}",user);
        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        log.info("delete user {}",id);
        userRepository.deleteExisted(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody User user, @PathVariable Integer id) {
        log.info("update user {}, id {}",user,id);
        userRepository.update(id,user.getName(),user.getEmail(),user.getPassword());
    }


    @GetMapping("/by-email")
    public User getByMail(@RequestParam String email) {
        return userRepository.getByEmail(email).orElse(null);
    }


}
