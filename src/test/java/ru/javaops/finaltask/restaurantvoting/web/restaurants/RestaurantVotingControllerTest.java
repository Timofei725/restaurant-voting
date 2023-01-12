package ru.javaops.finaltask.restaurantvoting.web.restaurants;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.finaltask.restaurantvoting.MatcherFactory;
import ru.javaops.finaltask.restaurantvoting.model.Vote;
import ru.javaops.finaltask.restaurantvoting.repository.VoteRepository;
import ru.javaops.finaltask.restaurantvoting.web.AbstractControllerTest;
import ru.javaops.finaltask.restaurantvoting.web.GlobalExceptionHandler;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.finaltask.restaurantvoting.RestaurantTestData.*;
import static ru.javaops.finaltask.restaurantvoting.UserTestData.FIRST_USER_MAIL;
import static ru.javaops.finaltask.restaurantvoting.UserTestData.SECOND_USER_MAIL;
import static ru.javaops.finaltask.restaurantvoting.service.VoteService.TOO_LATE_FOR_CHANGE;

class RestaurantVotingControllerTest extends AbstractControllerTest {
    @Autowired
    private VoteRepository voteRepository;
    private static final String REST_URL = RestaurantVotingController.REST_URL + '/';
    private static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingEqualsComparator(Vote.class);

    @Test
    @WithUserDetails(value = FIRST_USER_MAIL)
    void changeVote() throws Exception {
        if (LocalTime.now().isBefore(TOO_LATE_FOR_CHANGE)) {
            Vote changedVote = new Vote(LocalDate.now(), 1, 3);
            changedVote.setId(1);
            perform(MockMvcRequestBuilders.post(REST_URL + FIRST_RESTAURANT_ID + "/vote"))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

            VOTE_MATCHER.assertMatch(voteRepository.getById(1), changedVote);
        } else
            perform(MockMvcRequestBuilders.post(REST_URL + THIRD_RESTAURANT_ID + "/vote")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isUnprocessableEntity())
                    .andExpect(content().string(containsString(GlobalExceptionHandler.EXCEPTION_LATE_VOTE)));
    }

    @Test
    @WithUserDetails(value = SECOND_USER_MAIL)
    void doVote() throws Exception {
        Vote newVote = new Vote(LocalDate.now(), 3, 3);
        newVote.setId(3);
        perform(MockMvcRequestBuilders.post(REST_URL + FIRST_RESTAURANT_ID + "/vote"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        VOTE_MATCHER.assertMatch(voteRepository.getById(3), newVote);


    }

    @Test
    @WithUserDetails(value = SECOND_USER_MAIL)
    void voteForWrongRestaurantId() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL + NOT_FOUND + "/vote"))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

    }
}
