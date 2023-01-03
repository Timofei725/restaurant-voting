package ru.javaops.finaltask.restaurantvoting.web.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javaops.finaltask.restaurantvoting.model.User;
import ru.javaops.finaltask.restaurantvoting.repository.UserRepository;
import ru.javaops.finaltask.restaurantvoting.util.UserUtil;

import java.util.Optional;

@Slf4j
public abstract class AbstractUserController {

    @Autowired
    protected UserRepository repository;


    public Optional<User> get(int id) {
        log.info("get {}", id);
        return  repository.findById(id);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        repository.deleteExisted(id);
    }

    protected Optional<User> prepareAndSave(User user) {
        return Optional.of(repository.save(UserUtil.prepareToSave(user)));
    }
}