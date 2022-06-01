package com.example.movieBase.service;

import com.example.movieBase.exception.DataNotFoundException;
import com.example.movieBase.model.Actor;
import com.example.movieBase.model.Movie;
import com.example.movieBase.repository.ActorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ActorService {
    @Autowired
    private ActorRepository actorRepo;

    public List<Actor> getActors(){
        return actorRepo.findAll();
    }

    public Actor findActorById(Long id) {
        Optional<Actor> optionalActor = actorRepo.findById(id);
        if (optionalActor.isEmpty()) {
            throw new DataNotFoundException("Actor with ID "+id+" not found in the database");
        }
        return optionalActor.get();
    }

    public Actor findActorByName(String name) {
        Optional<Actor> optionalActor = actorRepo.findByName(name);
        if (optionalActor.isEmpty()) {
            throw new DataNotFoundException("Actor with name "+name+" not found in the database");
        }
        return optionalActor.get();
    }

    public List<Actor> findActorByAge(Integer age) {
        Optional<List<Actor>> optionalActors = actorRepo.findByAge(age);
        if (optionalActors.isEmpty() || optionalActors.get().size()==0) {
            throw new DataNotFoundException("Actor(s) with age "+age+" not found in the database");
        }
        return optionalActors.get();
    }

    public Actor addActor(Actor actor){
        return actorRepo.save(actor);
    }

    public Actor updateActor(Actor actor, Long id) {
        Actor optionalActor = findActorById(id);
        Actor actorToUpdate = optionalActor;

        boolean isNameProvided = actor.getName() != null && !actor.getName().isEmpty() && !actor.getName().isBlank();
        boolean isAgeProvided = actor.getAge() != null && actor.getAge() >=3;
        boolean hasMovies = actor.getMovies() != null && actor.getMovies().size() >= 1;

        if (isNameProvided) {
            actorToUpdate.setName(actor.getName());
        }
        if (isAgeProvided) {
            actorToUpdate.setAge(actor.getAge());
        }
        if (hasMovies) {
            actorToUpdate.setMovies(actor.getMovies());
        }
        return actorRepo.save(actorToUpdate);
    }

    public void deleteActor(Long id) {
        Actor actorToDelete = findActorById(id);
        actorRepo.delete(actorToDelete);
    }
}
