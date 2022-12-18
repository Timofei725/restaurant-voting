package ru.javaops.finaltask.restaurantvoting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static ru.javaops.finaltask.restaurantvoting.utill.ValidationUtil.checkModification;


// https://stackoverflow.com/questions/42781264/multiple-base-repositories-in-spring-data-jpa
@NoRepositoryBean
@Transactional
public interface BaseRepository<T> extends JpaRepository<T, Integer> {

    //    https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query.spel-expressions
    @Modifying
    @Query("DELETE FROM #{#entityName} u WHERE u.id=:id")
    int delete(Integer id);

    default void deleteExisted(Integer id) {
        checkModification(delete(id), id);
    }




    @Transactional(readOnly = true)
    List<T> findAll();

    @Transactional(readOnly = true)
    T getById(Integer id);




}