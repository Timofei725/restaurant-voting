package ru.javaops.finaltask.restaurantvoting.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "vote",//https://ru.minecraftfullmod.com/2025-defining-indexes-in-jpa
        indexes = @Index(name = "voteIndex", columnList = "vote_date,user_id", unique = true))
//Only one vote per day to person
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Vote extends BaseEntity {

    @Column(name = "vote_date", nullable = false, columnDefinition = "date default now()")
    @NotNull
    private LocalDate date;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "restaurant_id")
    private Integer restaurantId;
}
