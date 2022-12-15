package ru.javaops.finaltask.restaurantvoting;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface HasId {
    Integer getId();

    void setId(Integer id);
    @JsonIgnore
    default boolean isNew() {
        return getId() == null;
    }

    // doesn't work for hibernate lazy proxy
    default int id() {
        return getId();
    }
}
