package com.techstudy.moviebookingapp.service;

import com.techstudy.moviebookingapp.model.*;

import java.util.List;

public interface MovieService {
     List<MovieDescription> getPopularMovies();
     PopularMovieResponse searchMovie(String input);
     MovieDetails getMovieDetails(String movieId);
     BookingDetails createBooking(BookingTicketDetails bookingTicketDetails) throws Exception;
}
