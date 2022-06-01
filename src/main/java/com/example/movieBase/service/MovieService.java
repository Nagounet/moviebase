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
import java.util.stream.Collectors;

@Slf4j
@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepo;
    @Autowired
    private TagsRepository tagsRepo;
    @Autowired
    private ActorRepository actorRepo;

    public List<Movie> getMovies(){
        return movieRepo.findAll();
    }

    public Movie findMovieById(Long id) {
        Optional<Movie> optionalMovie = movieRepo.findById(id);
        if (optionalMovie.isEmpty()) {
            throw new DataNotFoundException("Movie with ID "+id+" not found in the database");
        }
        return optionalMovie.get();
    }

    public Movie findMovieByTitle(String title){
        Optional<Movie> optionalMovie = movieRepo.findByTitle(title);
        if (optionalMovie.isEmpty()) {
            throw new DataNotFoundException("Movie with name "+title+" not found in the database");
        }
        return optionalMovie.get();
    }

    public List<Movie> findMoviesByTag(String tag){
        Optional<Tags> optionalTags = tagsRepo.findByTagName(tag);
        List<Movie> moviesByTags;
        if (optionalTags.isEmpty()) {
            throw new DataNotFoundException(tag+" not found in the database");
        }
        moviesByTags = optionalTags.get().getMovies();
        return moviesByTags;
    }

    public List<Movie> findMoviesByActor(String actor) {
        Optional<Actor> optionalActors = actorRepo.findByName(actor);
        List<Movie> moviesByActor;
        if (optionalActors.isEmpty()) {
            throw new DataNotFoundException(actor+" not found in the database");
        }
        moviesByActor = optionalActors.get().getMovies();
        return moviesByActor;
    }

    public List<Movie> findMoviesByAgeRestriction(Integer ageRestriction) {
        /*Optional<List<Movie>> optionalMovie = movieRepo.findByAgeRestriction(ageRestriction);
        if (optionalMovie.isEmpty() || optionalMovie.get().size()==0) {
            throw new DataNotFoundException("Movie with age restriction "+ageRestriction+" not found in the database");
        }
        return optionalMovie.get();*/
        List<Movie> allMovies = getMovies();
        return allMovies.stream().filter(movie -> movie.getAgeRestriction()>=ageRestriction).collect(Collectors.toList());
    }

    public Movie addMovie(Movie movie){
        return movieRepo.save(movie);
    }

    public Movie updateMovie(Movie movie, Long id) {
        Movie optionalMovie = findMovieById(id);
        Movie movieToUpdate = optionalMovie;

        boolean isTitleProvided = movie.getTitle() != null && !movie.getTitle().isEmpty() && !movie.getTitle().isBlank();
        boolean isDescProvided = movie.getDescription() != null && !movie.getDescription().isEmpty() && !movie.getDescription().isBlank();
        boolean isDurationProvided = movie.getDuration() != null && movie.getDuration() >=1;
        boolean isAgeRestrictionProvided = movie.getAgeRestriction() != null && movie.getAgeRestriction() >=10;
        boolean hasActors = movie.getActors() != null && movie.getActors().size() >= 1;
        boolean hasTags = movie.getMovieTags() != null && movie.getMovieTags().size() >= 1;

        if (isTitleProvided) {
            movieToUpdate.setTitle(movie.getTitle());
        }
        if (isDescProvided) {
            movieToUpdate.setDescription(movie.getDescription());
        }
        if (isDurationProvided) {
            movieToUpdate.setDuration(movie.getDuration());
        }
        if (isAgeRestrictionProvided) {
            movieToUpdate.setAgeRestriction(movie.getAgeRestriction());
        }
        if (hasActors) {
            movieToUpdate.setActors(movie.getActors());
        }
        if (hasTags) {
            movieToUpdate.setMovieTags(movie.getMovieTags());
        }
        return movieRepo.save(movieToUpdate);
    }

    public void deleteMovie(Long id) {
        Movie movieToDelete = findMovieById(id);
        movieRepo.delete(movieToDelete);
    }
}
