package com.example.movieBase.service;

import com.example.movieBase.exception.DataNotFoundException;
import com.example.movieBase.model.Actor;
import com.example.movieBase.model.Movie;
import com.example.movieBase.model.Tags;
import com.example.movieBase.repository.ActorRepository;
import com.example.movieBase.repository.MovieRepository;
import com.example.movieBase.repository.TagsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TagsService {

    @Autowired
    private TagsRepository tagsRepo;

    public List<Tags> getTags(){
        return tagsRepo.findAll();
    }

    public Tags findTagById(Long id) {
        Optional<Tags> optionalTag = tagsRepo.findById(id);
        if (optionalTag.isEmpty()) {
            throw new DataNotFoundException("Tag with ID "+id+" not found in the database");
        }
        return optionalTag.get();
    }

    public Tags findTagByName(String name){
        Optional<Tags> optionalTag = tagsRepo.findByTagName(name);
        if (optionalTag.isEmpty()) {
            throw new DataNotFoundException("Tag with name "+name+" not found in the database");
        }
        return optionalTag.get();
    }

    public Tags addTag(Tags tag){
        return tagsRepo.save(tag);
    }

    public Tags updateTag(Tags tag, Long id) {
        Tags optionalTag = findTagById(id);
        Tags tagToUpdate = optionalTag;

        boolean isTagNameProvided = tag.getTagName() != null && !tag.getTagName().isEmpty() && !tag.getTagName().isBlank();
        boolean hasMovies = tag.getMovies() != null && tag.getMovies().size() >= 1;

        if (isTagNameProvided) {
            tagToUpdate.setTagName(tag.getTagName());
        }
        if (hasMovies) {
            tagToUpdate.setMovies(tag.getMovies());
        }
        return tagsRepo.save(tagToUpdate);
    }

    public void deleteTag(Long id) {
        Tags tagToDelete = findTagById(id);
        tagsRepo.delete(tagToDelete);
    }
}
