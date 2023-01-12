package ru.javaops.finaltask.restaurantvoting.service;

import org.springframework.stereotype.Service;
import ru.javaops.finaltask.restaurantvoting.model.Vote;
import ru.javaops.finaltask.restaurantvoting.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class VoteService {
    private final VoteRepository voteRepository;
    public static final LocalTime TOO_LATE_FOR_CHANGE = LocalTime.of(11, 0);

    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public Optional<Vote> doVote(int userId, int restaurantId) {
        Optional<Vote> oldVote = voteRepository.findByDateAndUserId(LocalDate.now(), userId);
        if (oldVote.isPresent()) { //User changed his mind
            if (LocalTime.now().isBefore(TOO_LATE_FOR_CHANGE)) { // before 11 a.m
                voteRepository.changeChoice(restaurantId, LocalDate.now(), userId);
                Vote newVote = new Vote(LocalDate.now(), userId, restaurantId);
                newVote.setId(oldVote.get().getId());
                return Optional.of(newVote);
            } else return Optional.empty();
        } else {
            return Optional.of(voteRepository.save(new Vote(LocalDate.now(), userId, restaurantId)));
        }
    }


}
