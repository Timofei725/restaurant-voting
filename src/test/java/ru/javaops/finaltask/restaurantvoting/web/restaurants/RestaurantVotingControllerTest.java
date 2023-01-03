package ru.javaops.finaltask.restaurantvoting.web.restaurants;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.finaltask.restaurantvoting.web.AbstractControllerTest;

import java.time.LocalTime;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.finaltask.restaurantvoting.RestaurantTestData.FIRST_RESTAURANT_ID;
import static ru.javaops.finaltask.restaurantvoting.UserTestData.FIRST_USER_MAIL;
import static ru.javaops.finaltask.restaurantvoting.UserTestData.SECOND_USER_MAIL;

class RestaurantVotingControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestaurantVotingController.REST_URL + '/';

    @Test
    @WithUserDetails(value = FIRST_USER_MAIL)
    void changeVote() throws Exception {
        MvcResult result =   perform(MockMvcRequestBuilders.post(REST_URL + FIRST_RESTAURANT_ID+"/vote"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        String resultString = result.getResponse().getContentAsString();
        if(LocalTime.now().isBefore(LocalTime.of(11,00))){
            Assertions.assertEquals(resultString,"Your vote have been done or changed");
        }
        else   Assertions.assertEquals(resultString,"You have already voted and you can't change your vote after 11:00");
    }
    @Test
    @WithUserDetails(value = SECOND_USER_MAIL)
    void doVote() throws Exception {
        MvcResult result =   perform(MockMvcRequestBuilders.post(REST_URL + FIRST_RESTAURANT_ID+"/vote"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        String resultString = result.getResponse().getContentAsString();
            Assertions.assertEquals(resultString,"Your vote have been done or changed");

    }
}
