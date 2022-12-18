package ru.javaops.finaltask.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Entity
@Table(name = "dishes",//https://ru.minecraftfullmod.com/2025-defining-indexes-in-jpa
        indexes = @Index(name = "dishIndex", columnList = "name,date_in_menu,restaurant_id", unique = true))
//One unique dish for one restaurant per day
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Dish extends NamedEntity{
   @Column(name = "price", nullable = false)
   private Integer price;

   @Column(name = "date_in_menu", nullable = false, columnDefinition = "date default now()")
   @NotNull
   private LocalDate date;
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "restaurant_id")
   @JsonBackReference
   private Restaurant restaurant;
   public Dish(Integer id, String name,Integer price,Restaurant restaurant) {
      super(id, name);
      this.price=price;
      this.restaurant=restaurant;
   }

}
