package com.example.movieBase.repository;

import com.example.movieBase.model.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagsRepository extends JpaRepository<Tags, Long> {
    Optional<Tags> findByTagName(String tag);
}
