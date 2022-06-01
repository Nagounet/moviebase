package com.example.movieBase.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity(name = "actor")
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Column(name = "name")
    private String name;

    @NotNull
    @Min(value = 3, message = "Age should not be less than 3")
    @Max(value = 150, message = "Duration should not be greater than 150")
    @Column(name = "age")
    private Integer age;

    @ManyToMany(mappedBy = "actors")
    @JsonBackReference
    private List<Movie> movies;

}
