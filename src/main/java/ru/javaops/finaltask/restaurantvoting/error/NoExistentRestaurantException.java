package ru.javaops.finaltask.restaurantvoting.error;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.HttpStatus;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.MESSAGE;

public class NoExistentRestaurantException extends AppException {

    public NoExistentRestaurantException(String msg) {
        super(HttpStatus.BAD_REQUEST, msg, ErrorAttributeOptions.of(MESSAGE));
    }
}