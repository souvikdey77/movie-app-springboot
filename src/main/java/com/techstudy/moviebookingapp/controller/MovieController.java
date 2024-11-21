package com.techstudy.moviebookingapp.controller;


import com.techstudy.moviebookingapp.exceptions.InvalidInputException;
import com.techstudy.moviebookingapp.model.BookingDetails;
import com.techstudy.moviebookingapp.model.MovieDescription;
import com.techstudy.moviebookingapp.model.MovieDetails;
import com.techstudy.moviebookingapp.model.PopularMovieResponse;
import com.techstudy.moviebookingapp.serviceimpl.MovieServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for the movie management
 * @Author Souvik Dey
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/movie-management")
@Slf4j
public class MovieController {

    private final MovieServiceImpl serviceImpl;

    public MovieController(MovieServiceImpl serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    /**
     * Method to get the first 10 popular movies
     * @return List<MovieDescription
     */
    @GetMapping("/popular/movies/{number}")
    public ResponseEntity<List<MovieDescription>> getPopularMovies(@PathVariable String number){
        log.info("MovieController : getPopularMovies started");
        try{
            List<MovieDescription> popularMovieResponse =  serviceImpl.getPopularMovies(number);
            log.info("MovieController : getPopularMovies completed");
            return new ResponseEntity<>(popularMovieResponse, HttpStatus.OK);
        }catch (NumberFormatException e) {
            log.info("MovieController : getPopularMovies error occurred with parameter {}",number);
            throw new InvalidInputException("Kindly provide the valid number in the popularNumber");
        }
    }

    /**
     * Method to search a movie
     * @param input it can be any input to search the movie
     * @return PopularMovieResponse
     */
    @GetMapping("/search/movies")
    public ResponseEntity<PopularMovieResponse> searchMovies(@RequestParam String input){
        log.info("MovieController : searchMovies started");
        PopularMovieResponse searchResponse =  serviceImpl.searchMovie(input);
        log.info("MovieController : getMovieDetails searchResponse is {}",searchResponse);
        log.info("MovieController : searchMovies completed");
        return new ResponseEntity<>(searchResponse, HttpStatus.OK);
    }

    /**
     * Method to get the movie details of a given movie id
     * @param movieId id of the movie
     * @return MovieDetails
     */
    @GetMapping("/movies/{movie_id}")
    public ResponseEntity<MovieDetails> getMovieDetails(@PathVariable(name = "movie_id") String movieId){
        log.info("MovieController : getMovieDetails started");
        MovieDetails movieDetails =  serviceImpl.getMovieDetails(movieId);
        log.info("MovieController : getMovieDetails response is {}",movieDetails);
        log.info("MovieController : getMovieDetails completed");
        return new ResponseEntity<>(movieDetails, HttpStatus.OK);
    }

    /**
     * Method to create a booking for a movie
     * @param bookingDetails the booking details
     * @return BookingDetails
     */
    @PostMapping("/bookTicket")
    public ResponseEntity<BookingDetails> createBooking(@RequestBody @Valid  BookingDetails bookingDetails) {
        log.info("MovieController : createBooking started");
        BookingDetails bookings = serviceImpl.createBooking(bookingDetails);
        log.info("MovieController : createBooking bookings data is {}",bookings);
        log.info("MovieController : createBooking completed");
        return new ResponseEntity<>(bookings, HttpStatus.CREATED);
    }
}
