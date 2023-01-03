package ru.javaops.finaltask.restaurantvoting.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.finaltask.restaurantvoting.model.User;
import ru.javaops.finaltask.restaurantvoting.repository.UserRepository;
import ru.javaops.finaltask.restaurantvoting.util.JsonUtil;
import ru.javaops.finaltask.restaurantvoting.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.finaltask.restaurantvoting.UserTestData.*;
import static ru.javaops.finaltask.restaurantvoting.web.user.ProfileController.REST_URL;


class ProfileControllerTest  extends AbstractControllerTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @WithUserDetails(value = FIRST_USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentJson(user));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = FIRST_USER_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL))
                .andExpect(status().isNoContent());
        USER_MATCHER.assertMatch(userRepository.findAll(), admin, userSecond);
    }

    @Test
    @WithUserDetails(value = FIRST_USER_MAIL)
    void update() throws Exception {
        User updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(updated, "newPass")))
                .andDo(print())
                .andExpect(status().isNoContent());

        USER_MATCHER.assertMatch(userRepository.findById(FIRST_USER_ID).get(), getUpdated());
    }

    @Test
    void registerInvalid() throws Exception {
        User newUser = new User(null, null, null, null);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newUser)))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithUserDetails(value = FIRST_USER_MAIL)
    void updateInvalid() throws Exception {
        User updatedTo = new User(null, null, "password", null);
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithUserDetails(value = FIRST_USER_MAIL)
    void updateDuplicate() throws Exception {
        User updatedTo = new User(null, "newName", ADMIN_MAIL, "newPassword");
        perform(MockMvcRequestBuilders.put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}

