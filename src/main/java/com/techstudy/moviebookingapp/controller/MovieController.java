package com.techstudy.moviebookingapp.controller;

import com.techstudy.moviebookingapp.model.*;
import com.techstudy.moviebookingapp.serviceImpl.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller for the movie management
 * @Author Souvik Dey
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/movie-management")
public class MovieController {

    @Autowired
    private MovieServiceImpl serviceImpl;

    /**
     * Method to get the first 10 popular movies
     * @return List<MovieDescription
     */
    @GetMapping("/popular/movies")
    public ResponseEntity<List<MovieDescription>> getPopularMovies(){
        List<MovieDescription> popularMovieResponse =  serviceImpl.getPopularMovies();
        return new ResponseEntity<>(popularMovieResponse, HttpStatus.OK);
    }

    /**
     * Method to search a movie
     * @param input
     * @return PopularMovieResponse
     */
    @GetMapping("/search/movies")
    public ResponseEntity<PopularMovieResponse> searchMovies(@RequestParam(required = true) String input){
        PopularMovieResponse searchResponse =  serviceImpl.searchMovie(input);
        return new ResponseEntity<>(searchResponse, HttpStatus.OK);
    }

    /**
     * Method to get the movie details of a given movie id
     * @param movieId
     * @return MovieDetails
     */
    @GetMapping("/movies/{movie_id}")
    public ResponseEntity<MovieDetails> getMovieDetails(@PathVariable(name = "movie_id", required = true) String movieId){
        MovieDetails movieDetails =  serviceImpl.getMovieDetails(movieId);
        return new ResponseEntity<>(movieDetails, HttpStatus.OK);
    }

    /**
     * Method to create a booking for a movie
     * @param bookingDetails
     * @return BookingDetails
     * @throws Exception
     */
    @PostMapping("/movie/bookTicket")
    public ResponseEntity<BookingDetails> createBooking(@RequestBody @Valid BookingDetails bookingDetails) throws Exception {
        BookingDetails bookings = serviceImpl.createBooking(bookingDetails);
        return new ResponseEntity<>(bookings, HttpStatus.CREATED);
    }
}
