package com.techstudy.moviebookingapp.serviceImpl;

import com.techstudy.moviebookingapp.exceptions.CreateBookingException;
import com.techstudy.moviebookingapp.exceptions.DuplicateBookingException;
import com.techstudy.moviebookingapp.exceptions.MovieNotFoundException;
import com.techstudy.moviebookingapp.model.*;
import com.techstudy.moviebookingapp.repository.AdminRepository;
import com.techstudy.moviebookingapp.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation class for Movie related functionalities
 * @author Souvik Dey
 */
@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AdminRepository repository;

    /**
     * Business implementation for getting the first 10 popular movies
     * @return List<MovieDescription>
     */
    @Override
    public List<MovieDescription> getPopularMovies() {
        ResponseEntity<PopularMovieResponse> resPopularMovies = restTemplate.getForEntity("https://api.themoviedb.org/3/discover/movie?api_key=102196722a052b03fa096856c680badd&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&with_watch_monetization_types=flatrate",PopularMovieResponse.class);
        List<MovieDescription> movies = resPopularMovies.getBody().getResults().stream().limit(10).collect(Collectors.toList());
        if(movies.size() == 0){
            throw new MovieNotFoundException();
        }
        return movies;
    }

    /**
     * Business implementation for search a movie based on the given input
     * @param input
     * @return PopularMovieResponse
     */
    @Override
    public PopularMovieResponse searchMovie(String input) {
        ResponseEntity<PopularMovieResponse> resPopularMovies = null;
        resPopularMovies = restTemplate.getForEntity("https://api.themoviedb.org/3/search/movie?api_key=102196722a052b03fa096856c680badd&language=en-US&query="+input+"&page=1&include_adult=false", PopularMovieResponse.class);
        if(resPopularMovies.getBody().getResults().isEmpty()){
            throw new MovieNotFoundException();
        }
        return resPopularMovies.getBody();
    }

    /**
     * Business implementation for get a movie details by passing movie id
     * @param movieId
     * @return MovieDetails
     */
    @Override
    public MovieDetails getMovieDetails(String movieId) {
        ResponseEntity<MovieDetails> movieDetails = restTemplate.getForEntity("https://api.themoviedb.org/3/movie/"+movieId+"?api_key=102196722a052b03fa096856c680badd&language=en-US",MovieDetails.class);
        return movieDetails.getBody();
    }

    /**
     * Business implementation for booking a movie ticket
     * @param bookingDetails
     * @return BookingDetails
     * @throws Exception
     */
    @Override
    public BookingDetails createBooking(BookingDetails bookingDetails) throws Exception {
        BookingDetails existingBooking = null;
        existingBooking = repository.findByEmail(bookingDetails.getEmail());
        if(existingBooking == null && bookingDetails.getNumberOfTickets() <= 10){
            bookingDetails.setBookingStatus("confirmed");
            repository.save(bookingDetails);
        }
        else if(bookingDetails.getNumberOfTickets() > 10){
            throw new CreateBookingException(bookingDetails.getNumberOfTickets(),bookingDetails.getEmail());
        }
        else if(existingBooking != null){
            throw new DuplicateBookingException();
        }
        return bookingDetails;
    }
}
