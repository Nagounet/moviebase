package com.example.movieBase.controller;

import com.example.movieBase.model.Movie;
import com.example.movieBase.repository.MovieRepository;
import com.example.movieBase.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.QueryAnnotation;
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
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/")
    public ResponseEntity<List<Movie>> getMovies() {
        return ResponseEntity.ok(movieService.getMovies());
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        Movie movie = movieService.findMovieById(id);
        return ResponseEntity.ok(movie);
    }

    @GetMapping("/getByTitle")
    public ResponseEntity<Movie> getMovieByTitle(@RequestParam String title) {
        Movie movie = movieService.findMovieByTitle(title);
        return ResponseEntity.ok(movie);
    }

    @GetMapping("/getByTag")
    public ResponseEntity<List<Movie>> getMoviesByTag(@RequestParam String tag) {
        List<Movie> movies = movieService.findMoviesByTag(tag);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/getByActor")
    public ResponseEntity<List<Movie>> getMoviesByActor(@RequestParam String actor) {
        List<Movie> movies = movieService.findMoviesByActor(actor);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/getByAgeRestriction/{ageRestriction}")
    public ResponseEntity<List<Movie>> getMoviesByAgeRestriction(@PathVariable Integer ageRestriction) {
        List<Movie> movies = movieService.findMoviesByAgeRestriction(ageRestriction);
        return ResponseEntity.ok(movies);
    }

    @PostMapping("/addMovie")
    ResponseEntity<Movie> addMovie(@RequestBody @Valid Movie movie) throws URISyntaxException {
        Movie savedMovie = movieService.addMovie(movie);
        //return ResponseEntity.ok("Movie added");
        return ResponseEntity.created(new URI("/getById/"+savedMovie.getId())).body(savedMovie);
    }

    @PatchMapping(value = "/updateMovie/{id}")
    public ResponseEntity<String> updateMovie(@RequestBody Movie movie, @PathVariable Long id) {
        movieService.updateMovie(movie, id);
        return ResponseEntity.ok(movieService.findMovieById(id).getTitle()+" updated");
    }

    @DeleteMapping(value="/deleteMovie/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable Long id) {
        String movieName = movieService.findMovieById(id).getTitle();
        movieService.deleteMovie(id);
        return ResponseEntity.ok(movieName+" with id "+id+" deleted");
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
