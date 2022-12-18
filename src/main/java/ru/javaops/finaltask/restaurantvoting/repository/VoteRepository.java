package ru.javaops.finaltask.restaurantvoting.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.finaltask.restaurantvoting.model.Vote;

import java.time.LocalDate;
import java.util.Optional;

public interface VoteRepository extends BaseRepository<Vote> {
    @Transactional(readOnly = true)
    Optional<Vote> findByDateAndUserId(LocalDate date, Integer userId);

      @Modifying
      @Query("UPDATE  Vote v SET v.restaurantId=?1 where  v.date=?2 AND v.userId=?3" )
      int changeChoice(Integer restaurant_id, LocalDate vote_date,Integer user_id);



}