package ru.javaops.finaltask.restaurantvoting.repository;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.finaltask.restaurantvoting.model.User;

import java.util.Optional;
@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User> {

    Optional<User> getByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u set u.name=?2, u.email=?3,u.password=?4 where u.id=?1")
    void update(Integer id, String newName, String email,String password);
}