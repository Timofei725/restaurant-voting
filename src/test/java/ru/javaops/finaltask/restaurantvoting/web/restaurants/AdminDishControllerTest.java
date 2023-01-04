package ru.javaops.finaltask.restaurantvoting.web.restaurants;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.javaops.finaltask.restaurantvoting.DishTestData;
import ru.javaops.finaltask.restaurantvoting.model.Dish;
import ru.javaops.finaltask.restaurantvoting.repository.DishRepository;
import ru.javaops.finaltask.restaurantvoting.util.JsonUtil;
import ru.javaops.finaltask.restaurantvoting.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.finaltask.restaurantvoting.DishTestData.*;
import static ru.javaops.finaltask.restaurantvoting.RestaurantTestData.*;
import static ru.javaops.finaltask.restaurantvoting.UserTestData.ADMIN_MAIL;
import static ru.javaops.finaltask.restaurantvoting.UserTestData.FIRST_USER_MAIL;

class AdminDishControllerTest extends AbstractControllerTest {
    private static final String REST_URL = AdminDishController.REST_URL.substring(0,23) ;

    @Autowired
    private DishRepository dishRepository;
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + FIRST_RESTAURANT_ID + "/dishes"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(FRIED_FISH, FRIED_POTATOES));

    }
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + FIRST_RESTAURANT_ID + "/dishes/" + FRIED_FISH.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(FRIED_FISH));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + FIRST_RESTAURANT_ID + "/dishes/" + FRIED_FISH.getId()))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
        Assertions.assertFalse(dishRepository.findById(1).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getDishesByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + THIRD_RESTAURANT_ID
                + "/dishes/" + "/date?for-date=2019-06-26"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(GARLIC_BREAD,NACHOS,SPAGHETTI));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        Dish newDish= createNew();
        ResultActions action =   perform(MockMvcRequestBuilders.post(REST_URL + THIRD_RESTAURANT_ID
            + "/dishes").contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andExpect(status().isOk());
        Dish createdDish = DISH_MATCHER.readFromJson(action);

        int newId = createdDish.id();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(createdDish, newDish);
        DISH_MATCHER.assertMatch(dishRepository.getById(newId), newDish);
    }



    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        Dish updated = DishTestData.getUpdated();
       // updated.setId(null);
        perform(MockMvcRequestBuilders.put(REST_URL + FIRST_RESTAURANT_ID + "/dishes/" + FRIED_FISH.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        DISH_MATCHER.assertMatch(dishRepository.getById(FIRST_RESTAURANT_ID),DishTestData. getUpdated());
    }
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + FIRST_RESTAURANT_ID + "/dishes/" + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
    @Test
    @WithUserDetails(value = FIRST_USER_MAIL)
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + FIRST_RESTAURANT_ID + "/dishes"))
                .andExpect(status().isForbidden());
    }



    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateHtmlUnsafe() throws Exception {
        Dish updated = FRIED_FISH;
        updated.setName("<script>alert(123)</script>");
        perform(MockMvcRequestBuilders.put(REST_URL + FIRST_RESTAURANT_ID + "/dishes/"+FRIED_FISH.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}
