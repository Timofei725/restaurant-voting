package ru.javaops.finaltask.restaurantvoting;

import ru.javaops.finaltask.restaurantvoting.model.Role;
import ru.javaops.finaltask.restaurantvoting.model.User;
import ru.javaops.finaltask.restaurantvoting.util.JsonUtil;

import java.time.LocalDateTime;

public class UserTestData {

    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "registered", "password");

    public static final int FIRST_USER_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final int SECOND_USER_ID = 3;
    public static final String FIRST_USER_MAIL = "user@gmail.com";
    public static final String ADMIN_MAIL = "admin@gmail.com";
    public static final String SECOND_USER_MAIL = "user2@gmail.com";

    public static final User user = new User(FIRST_USER_ID, "User_First", FIRST_USER_MAIL, "password", Role.USER);
    public static final User admin = new User(ADMIN_ID, "Admin", ADMIN_MAIL, "admin", Role.ADMIN, Role.USER);
    public static final User userSecond = new User(SECOND_USER_ID, "User_Second", SECOND_USER_MAIL, "password2", Role.USER);


    public static User getNew() {
        return new User( null,"New", "new@gmail.com", "newPass", Role.USER);
    }

    public static User getUpdated() {
        User user=new User(FIRST_USER_ID, "UpdatedName", FIRST_USER_MAIL, "newPass", Role.USER);
        user.setRegistered(LocalDateTime.now());
        return user;
    }



    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }

}
