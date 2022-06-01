package com.example.movieBase.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Data
@Entity(name = "client")
public class Client {

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

    @NotNull
    @NotBlank
    @Email
    @Column(name="email", unique = true)
    private String email;
}
