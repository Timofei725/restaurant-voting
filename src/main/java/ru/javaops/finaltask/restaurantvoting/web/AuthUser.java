package ru.javaops.finaltask.restaurantvoting.web;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(of = "user")
public class AuthUser {


    public int id() {
        return 1;
    }
}