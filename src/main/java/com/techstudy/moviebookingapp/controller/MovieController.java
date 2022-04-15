package com.techstudy.moviebookingapp.controller;

import com.techstudy.moviebookingapp.model.*;
import com.techstudy.moviebookingapp.serviceImpl.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/movie-management")
public class MovieController {

    @Autowired
    private MovieServiceImpl serviceImpl;

    @GetMapping("/popular/movies")
    public ResponseEntity<List<MovieDescription>> getPopularMovies(){
        List<MovieDescription> popularMovieResponse =  serviceImpl.getPopularMovies();
        return new ResponseEntity<>(popularMovieResponse, HttpStatus.OK);
    }

    @GetMapping("/search/movies")
    public ResponseEntity<PopularMovieResponse> searchMovies(@RequestParam(required = true) String input){
        PopularMovieResponse searchResponse =  serviceImpl.searchMovie(input);
        return new ResponseEntity<>(searchResponse, HttpStatus.OK);
    }

    @GetMapping("/movies/{movie_id}")
    public ResponseEntity<MovieDetails> getMovieDetails(@PathVariable(name = "movie_id", required = true) String movieId){
        MovieDetails movieDetails =  serviceImpl.getMovieDetails(movieId);
        return new ResponseEntity<>(movieDetails, HttpStatus.OK);
    }

    @PostMapping("/movie/bookTicket")
    public ResponseEntity<BookingDetails> createBooking(@RequestBody BookingTicketDetails bookingTicketDetails) throws Exception {
        BookingDetails bookingDetails = serviceImpl.createBooking(bookingTicketDetails);
        if(bookingDetails == null){
            throw new Exception("Kindly verify the booking request and submit again");
        }
        return new ResponseEntity<>(bookingDetails, HttpStatus.CREATED);
    }
}
