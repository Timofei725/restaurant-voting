package ru.javaops.finaltask.restaurantvoting.service;

import org.springframework.stereotype.Service;
import ru.javaops.finaltask.restaurantvoting.model.Vote;
import ru.javaops.finaltask.restaurantvoting.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class VoteService {
    private final VoteRepository voteRepository;

    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public boolean doVote(Integer userId,Integer restaurantId){
        if(checkIfUserVotedToday(userId)){ //User changed his mind
        if(LocalTime.now().isBefore(LocalTime.of(11,00))){ // before 11 a.m
                voteRepository.changeChoice(restaurantId, LocalDate.now(),userId);
                return true;
            }
            else return false;
        }
        else {
            voteRepository.save(new Vote(LocalDate.now(),userId,restaurantId));
            return true;
        }
    }


    private boolean checkIfUserVotedToday(Integer userId){

        return voteRepository.findByDateAndUserId(LocalDate.now(),userId).isPresent();
    }
}
