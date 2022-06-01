package com.example.movieBase.controller;

import com.example.movieBase.model.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerTestIntegration {

    @Autowired
    MovieController movieController;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenGetRequestToMovie_thenOkResponse() throws Exception {
        Movie movieTest = new Movie("Test",120,"Description test",10);

        mockMvc.perform(MockMvcRequestBuilders.get("/movies/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void whenPostRequestToMovieAndValidMovie_thenCreatedResponse() throws Exception {
        String testMovie = "{\"title\": \"San Andrea\", " +
                "\"duration\": 30," +
                " \"description\" : \"Un test d'ajout de film\"," +
                " \"ageRestriction\" : 12}";
        mockMvc.perform(MockMvcRequestBuilders.post("/movies/addMovie")
                        .content(testMovie)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void whenPostRequestToMovieAndInvalidMovie_thenCreatedResponse() throws Exception {
        //MediaType textPlainUtf8 = new MediaType(MediaType.TEXT_PLAIN, Charset.forName("UTF-8"));
        String testMovie = "{\"title\": \"San Andrea\", " +
                "\"duration\": 30," +
                " \"description\" : \"Un test d'ajout de film\"," +
                " \"ageRestriction\" : \"\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/movies/addMovie")
                        .content(testMovie)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
