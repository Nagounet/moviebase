package com.example.movieBase.repository;

import com.example.movieBase.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {
    Optional<Actor> findByName(String name);
    Optional<List<Actor>> findByAge(Integer age);
}
