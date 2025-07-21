package com.example.mealfinder.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "meals")
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String mealName;

    @CreationTimestamp
    private LocalDateTime localDateTime;

    @OneToMany
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private List<Product> products;

    @Column(name = "meal_image")
    private String mealImage;

    @ManyToOne
    private Chef chef;

}
