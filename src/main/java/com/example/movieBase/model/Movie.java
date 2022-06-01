package com.example.movieBase.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank(message = "Please give a title name")
    @Column(name = "title")
    private String title;

    @NotNull
    @Min(value = 1, message = "Duration should not be less than 1")
    @Max(value = 1000, message = "Duration should not be greater than 1000")
    @Column(name = "duration")
    private Integer duration;

    @NotNull
    private String description;

    @NotNull
    @Column(name = "age_restriction")
    private Integer ageRestriction;

    /*@OneToMany
    private Set<Tags> tags;

    @OneToMany
    private Set<Actor> actors;*/

    @ManyToMany(fetch = FetchType.LAZY)
    //@JsonManagedReference
    @JoinTable(
            name = "movie_actors",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private List<Actor> actors;

    @ManyToMany(fetch = FetchType.LAZY)
    //@JsonManagedReference
    @JoinTable(
            name = "movie_tags",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tags> movieTags;

}
