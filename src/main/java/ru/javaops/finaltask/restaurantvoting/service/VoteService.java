package ru.javaops.finaltask.restaurantvoting.service;

import org.springframework.stereotype.Service;
import ru.javaops.finaltask.restaurantvoting.model.Vote;
import ru.javaops.finaltask.restaurantvoting.repository.RestaurantRepository;
import ru.javaops.finaltask.restaurantvoting.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class VoteService {
    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;
    public VoteService(VoteRepository voteRepository, RestaurantRepository restaurantRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public boolean doVote(int userId,int restaurantId){
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


    private boolean checkIfUserVotedToday(int userId){

        return voteRepository.findByDateAndUserId(LocalDate.now(),userId).isPresent();
    }

}
