package com.example.movieBase.controller;

import com.example.movieBase.exception.DataNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AdviceController {

    /*@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = { MovieSavingFailedException.class })
    protected ResponseEntity<Object> handleSubjectRegistrationException(MovieSavingFailedException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie adding failed");
    }*/

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = { DataNotFoundException.class })
    protected ResponseEntity<Object> handleSubjectRegistrationException(DataNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}