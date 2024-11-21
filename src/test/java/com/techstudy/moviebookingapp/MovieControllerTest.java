package com.techstudy.moviebookingapp;

import com.techstudy.moviebookingapp.controller.MovieController;
import com.techstudy.moviebookingapp.model.MovieDescription;
import com.techstudy.moviebookingapp.model.PopularMovieResponse;
import com.techstudy.moviebookingapp.serviceimpl.MovieServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@WebMvcTest(controllers = {MovieController.class})
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieServiceImpl movieService;

    @Test
    void whenGetAPopularMovies_FlowCorrect() throws Exception {
        MovieDescription movieDescription = new MovieDescription();
        movieDescription.setId("1");
        movieDescription.setTitle("Excellent Boy");
        List<MovieDescription> movieDescriptionList = new ArrayList<>();
        movieDescriptionList.add(movieDescription);
        Mockito.when(movieService.getPopularMovies("10")).thenReturn(movieDescriptionList);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/movie-management/popular/movies/10"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Excellent Boy"));
        verify(movieService, times(1)).getPopularMovies("10");
    }

    @Test
    void whenSearchMovie_FlowCorrect() throws Exception {
        MovieDescription movieDescription = new MovieDescription();
        movieDescription.setId("1");
        movieDescription.setTitle("Excellent Boy");
        List<MovieDescription> movieDescriptionList = new ArrayList<>();
        movieDescriptionList.add(movieDescription);
        PopularMovieResponse popularMovieResponse = new PopularMovieResponse();
        popularMovieResponse.setPage(0);
        popularMovieResponse.setResults(movieDescriptionList);

        Mockito.when(movieService.searchMovie("Venom")).thenReturn(popularMovieResponse);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/movie-management/search/movies?input="+"Venom"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.page").value("0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results[0].title").value("Excellent Boy"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results[0].id").value("1"));
        verify(movieService, times(1)).searchMovie("Venom");
    }

}
