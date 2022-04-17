package com.techstudy.moviebookingapp.service;

import com.techstudy.moviebookingapp.model.*;

import java.util.List;

/**
 * Service interface for all the movie services
 * @author souvikdey
 */
public interface MovieService {
     List<MovieDescription> getPopularMovies();
     PopularMovieResponse searchMovie(String input);
     MovieDetails getMovieDetails(String movieId);
     BookingDetails createBooking(BookingDetails bookingDetails) throws Exception;
}
