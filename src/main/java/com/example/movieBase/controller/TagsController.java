package com.example.movieBase.controller;

import com.example.movieBase.model.Movie;
import com.example.movieBase.model.Tags;
import com.example.movieBase.service.MovieService;
import com.example.movieBase.service.TagsService;
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
@RequestMapping("/tags")
public class TagsController {

    @Autowired
    private TagsService tagsService;

    @GetMapping("/")
    public ResponseEntity<List<Tags>> getTags() {
        return ResponseEntity.ok(tagsService.getTags());
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Tags> getTagById(@PathVariable Long id) {
        Tags tag = tagsService.findTagById(id);
        return ResponseEntity.ok(tag);
    }

    @GetMapping("/getByName")
    public ResponseEntity<Tags> getTagByName(@RequestParam String name) {
        Tags tag = tagsService.findTagByName(name);
        return ResponseEntity.ok(tag);
    }

    @PostMapping("/addTag")
    ResponseEntity<Tags> addTag(@RequestBody @Valid Tags tag) throws URISyntaxException {
        Tags savedTag = tagsService.addTag(tag);
        return ResponseEntity.created(new URI("/getById/"+savedTag.getId())).body(savedTag);
    }

    @PatchMapping(value = "/updateTag/{id}")
    public ResponseEntity<String> updateTag(@RequestBody Tags tag, @PathVariable Long id) {
        tagsService.updateTag(tag, id);
        return ResponseEntity.ok(tagsService.findTagById(id).getTagName()+" updated");
    }

    @DeleteMapping(value="/deleteTag/{id}")
    public ResponseEntity<String> deleteTag(@PathVariable Long id) {
        String tagName = tagsService.findTagById(id).getTagName();
        tagsService.deleteTag(id);
        return ResponseEntity.ok(tagName+" with id "+id+" deleted");
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
