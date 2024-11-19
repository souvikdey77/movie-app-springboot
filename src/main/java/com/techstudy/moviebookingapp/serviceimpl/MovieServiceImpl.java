package com.techstudy.moviebookingapp.serviceimpl;

import com.techstudy.moviebookingapp.exceptions.CreateBookingException;
import com.techstudy.moviebookingapp.exceptions.DuplicateBookingException;
import com.techstudy.moviebookingapp.exceptions.MovieNotFoundException;
import com.techstudy.moviebookingapp.model.*;
import com.techstudy.moviebookingapp.repository.AdminRepository;
import com.techstudy.moviebookingapp.service.MovieService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.stream.Collectors;

/**
 * Service implementation class for Movie related functionalities
 *
 * @author Souvik Dey
 */
@Service
public class MovieServiceImpl implements MovieService {

    @Value("${movie.db.api}")
    private String movieApi;

    private final RestTemplate restTemplate;
    private final AdminRepository repository;

    public MovieServiceImpl(RestTemplate restTemplate, AdminRepository repository) {
        this.restTemplate = restTemplate;
        this.repository = repository;
    }

    /**
     * Business implementation for getting the first 10 popular movies
     *
     * @return List<MovieDescription>
     */
    @Override
    public List<MovieDescription> getPopularMovies() {
        ResponseEntity<PopularMovieResponse> resPopularMovies = restTemplate.getForEntity(movieApi, PopularMovieResponse.class);
        List<MovieDescription> movies = requireNonNull(resPopularMovies.getBody()).getResults().stream().limit(10).collect(Collectors.toList());
        if (movies.isEmpty()) {
            throw new MovieNotFoundException();
        }
        return movies;
    }

    /**
     * Business implementation for search a movie based on the given input
     *
     * @param input to search the movie
     * @return PopularMovieResponse
     */
    @Override
    public PopularMovieResponse searchMovie(String input) {
        ResponseEntity<PopularMovieResponse> resPopularMovies = null;
        String externalSearchServiceUrl = "https://api.themoviedb.org/3/search/movie?api_key=102196722a052b03fa096856c680badd&language=en-US&query=";
        String pageAndIncludeAdult = "&page=1&include_adult=false";
        resPopularMovies = restTemplate.getForEntity(externalSearchServiceUrl + input + pageAndIncludeAdult, PopularMovieResponse.class);
        if (requireNonNull(resPopularMovies.getBody()).getResults().isEmpty()) {
            throw new MovieNotFoundException();
        }
        return resPopularMovies.getBody();
    }

    /**
     * Business implementation for get a movie details by passing movie id
     *
     * @param movieId id of the movie
     * @return MovieDetails
     */
    @Override
    public MovieDetails getMovieDetails(String movieId) {
        ResponseEntity<MovieDetails> movieDetails = restTemplate.getForEntity("https://api.themoviedb.org/3/movie/" + movieId + "?api_key=102196722a052b03fa096856c680badd&language=en-US", MovieDetails.class);
        return movieDetails.getBody();
    }

    /**
     * Business implementation for booking a movie ticket
     *
     * @param bookingDetails booking details
     * @return BookingDetails
     */
    @Override
    public BookingDetails createBooking(BookingDetails bookingDetails) {
        BookingDetails existingBooking = repository.findByEmail(bookingDetails.getEmail());
        if (existingBooking == null && bookingDetails.getNumberOfTickets() <= 10) {
            bookingDetails.setBookingStatus("confirmed");
            repository.save(bookingDetails);
        } else if (bookingDetails.getNumberOfTickets() > 10) {
            throw new CreateBookingException(bookingDetails.getNumberOfTickets(), bookingDetails.getEmail());
        } else {
            throw new DuplicateBookingException();
        }
        return bookingDetails;
    }
}
