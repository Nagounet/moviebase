package com.example.movieBase.repository;

import com.example.movieBase.model.Actor;
import com.example.movieBase.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByName(String name);

    Optional<List<Client>> findByAge(Integer age);

    Optional<Client> findByEmail(String email);
}
