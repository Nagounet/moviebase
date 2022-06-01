package com.example.movieBase.controller;

import com.example.movieBase.model.Actor;
import com.example.movieBase.model.Movie;
import com.example.movieBase.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/actors")
public class ActorController {

    @Autowired
    private ActorService actorService;

    @GetMapping("/")
    public ResponseEntity<List<Actor>> getActors() {
        return ResponseEntity.ok(actorService.getActors());
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Actor> getActor(@PathVariable Long id) {
        Actor actor = actorService.findActorById(id);
        return ResponseEntity.ok(actor);
    }

    @GetMapping("/getByName")
    public ResponseEntity<Actor> getActorByName(@RequestParam String name) {
        Actor actor = actorService.findActorByName(name);
        return ResponseEntity.ok(actor);
    }

    @GetMapping("/getByAge")
    public ResponseEntity<List<Actor>> getActorByAge(@RequestParam Integer age) {
        List<Actor> actorsByAge = actorService.findActorByAge(age);
        return ResponseEntity.ok(actorsByAge);
    }

    @PostMapping("/addActor")
    ResponseEntity<Actor> addActor(@RequestBody @Valid Actor actor) throws URISyntaxException {
        Actor savedActor = actorService.addActor(actor);
        //return ResponseEntity.ok("Actor added");
        return ResponseEntity.created(new URI("/getById/"+savedActor.getId())).body(savedActor);
    }

    @PatchMapping("/updateActor/{id}")
    public ResponseEntity<String> updateActor(@RequestBody Actor actor, @PathVariable Long id) {
        actorService.updateActor(actor, id);
        return ResponseEntity.ok(actorService.findActorById(id).getName()+" updated");
    }

    @DeleteMapping(value="/deleteActor/{id}")
    public ResponseEntity<String> deleteActor(@PathVariable Long id) {
        String actorName = actorService.findActorById(id).getName();
        actorService.deleteActor(id);
        return ResponseEntity.ok(actorName+" with id "+id+" deleted");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
