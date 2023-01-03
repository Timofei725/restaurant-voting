package ru.javaops.finaltask.restaurantvoting.web.restaurants;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.finaltask.restaurantvoting.RestaurantTestData;
import ru.javaops.finaltask.restaurantvoting.model.Restaurant;
import ru.javaops.finaltask.restaurantvoting.repository.RestaurantRepository;
import ru.javaops.finaltask.restaurantvoting.util.JsonUtil;
import ru.javaops.finaltask.restaurantvoting.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.finaltask.restaurantvoting.RestaurantTestData.*;
import static ru.javaops.finaltask.restaurantvoting.UserTestData.ADMIN_MAIL;
import static ru.javaops.finaltask.restaurantvoting.UserTestData.FIRST_USER_MAIL;

class AdminRestaurantControllerTest  extends AbstractControllerTest {

        private static final String REST_URL = AdminRestaurantController.REST_URL + '/';

        @Autowired
        private RestaurantRepository restaurantRepository;
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(firstRestaurant, secondRestaurant,thirdRestaurant));
    }
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + FIRST_RESTAURANT_ID))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(firstRestaurant));
    }
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND))
                .andExpect( status().is4xxClientError());
    }
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + FIRST_RESTAURANT_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        Assertions.assertFalse(restaurantRepository.findById(FIRST_RESTAURANT_ID).isPresent());
    }
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
    @Test
    @WithUserDetails(value = FIRST_USER_MAIL)
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isForbidden());
    }
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        Restaurant updated = RestaurantTestData.getUpdated();
        updated.setId(null);
        perform(MockMvcRequestBuilders.put(REST_URL + FIRST_RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        RESTAURANT_MATCHER.assertMatch(restaurantRepository.getById(FIRST_RESTAURANT_ID),RestaurantTestData. getUpdated());
    }
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        Restaurant newRestaurant = RestaurantTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isOk());

        Restaurant createdRestaurant = RESTAURANT_MATCHER.readFromJson(action);
        int newId = createdRestaurant.id();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(createdRestaurant, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(restaurantRepository.getById(newId), newRestaurant);
    }
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        Restaurant invalid = new Restaurant( null, "s");
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateHtmlUnsafe() throws Exception {
        Restaurant updated = firstRestaurant;
        updated.setName("<script>alert(123)</script>");
        perform(MockMvcRequestBuilders.put(REST_URL + FIRST_RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
    }