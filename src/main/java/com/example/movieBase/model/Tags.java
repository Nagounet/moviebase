package com.example.movieBase.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity(name = "tags")
public class Tags {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotNull
        @Column(name = "tag_name")
        private String tagName;

        @ManyToMany(mappedBy = "movieTags")
        @JsonBackReference
        private List<Movie> movies;

}
