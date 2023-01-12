package ru.javaops.finaltask.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;


@Entity

@Table(name = "dish",//https://ru.minecraftfullmod.com/2025-defining-indexes-in-jpa
        indexes = @Index(name = "dishIndex", columnList = "restaurant_id,date_in_menu,name", unique = true))
//One unique dish for one restaurant per day
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Dish extends NamedEntity {
    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "date_in_menu", nullable = false, columnDefinition = "date default now()")
    private LocalDate date;
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "restaurant_id")
    @JsonBackReference
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Restaurant restaurant;

    public Dish(Integer id, String name, Integer price, LocalDate date, Restaurant restaurant) {
        super(id, name);
        this.price = price;
        this.date = date;
        this.restaurant = restaurant;
    }

    public Dish(Integer id, String name, Integer price, Restaurant restaurant) {
        super(id, name);
        this.price = price;
        this.restaurant = restaurant;
    }

    public Dish(Dish d) {
        this(d.id, d.name, d.price, d.date, d.restaurant);
    }

}
