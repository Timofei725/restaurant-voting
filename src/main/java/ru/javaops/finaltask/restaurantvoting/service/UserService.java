package ru.javaops.finaltask.restaurantvoting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javaops.finaltask.restaurantvoting.model.Vote;
import ru.javaops.finaltask.restaurantvoting.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private LocalDate date;
    private  LocalTime time;


    private final VoteRepository voteRepository;


@Autowired
    public UserService(VoteRepository voteRepository ) {
        this.voteRepository = voteRepository;
        this.date = LocalDate.now();
         this.time = LocalTime.now();
    }

    public List<Vote> getAll(){
        return voteRepository.findAll();
    }

    public Optional<Vote> getById(Integer voteId){
        return voteRepository.findById(voteId);
    }
    public boolean doVote(Integer userId,Integer restaurantId){
        if(checkIfUserVotedToday(userId)){ //User change his mind
            if(time.isBefore(LocalTime.of(11,00))){ // before 11 a.m
                voteRepository.changeChoice(restaurantId,date,userId);
                return true;
            }
            else return false;
        }
        else {
            voteRepository.save(new Vote(date,userId,restaurantId));
            return true;
        }
    }

    public void delete(Integer id){
        voteRepository.deleteExisted(id);
    }
    private boolean checkIfUserVotedToday(Integer userId){

        return voteRepository.findByDateAndUserId(date,userId).isPresent();
    }

    protected void setTime(LocalTime time) {
        this.time = time;
    }
}
