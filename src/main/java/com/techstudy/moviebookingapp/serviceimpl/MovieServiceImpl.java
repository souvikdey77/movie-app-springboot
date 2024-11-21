package com.techstudy.moviebookingapp.serviceimpl;

import com.techstudy.moviebookingapp.exceptions.CreateBookingException;
import com.techstudy.moviebookingapp.exceptions.DuplicateBookingException;
import com.techstudy.moviebookingapp.exceptions.MovieNotFoundException;
import com.techstudy.moviebookingapp.model.*;
import com.techstudy.moviebookingapp.repository.AdminRepository;
import com.techstudy.moviebookingapp.service.MovieService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.util.Objects.requireNonNull;


/**
 * Service implementation class for Movie related functionalities
 *
 * @author Souvik Dey
 */
@Service
@Slf4j
@Transactional
public class MovieServiceImpl implements MovieService {

    @Value("${movie.db.api}")
    String movieApi;

    @Value("${movie.externalsearchurl}")
    String externalSearchServiceUrl;

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
    public List<MovieDescription> getPopularMovies(String popularNumber) {
        log.info("MovieServiceImpl : getPopularMovies started with popularNumber {}",popularNumber);
        int noOfMovies = Integer.parseInt(popularNumber);
        log.info("MovieServiceImpl : getPopularMovies popularNumber is {}",popularNumber);
        ResponseEntity<PopularMovieResponse> resPopularMovies = restTemplate.getForEntity(movieApi, PopularMovieResponse.class);
        List<MovieDescription> movieDescriptionList = requireNonNull(resPopularMovies.getBody()).getResults().stream().limit(noOfMovies).toList();
        log.info("MovieServiceImpl : getPopularMovies response is {}", movieDescriptionList);
        log.info("MovieServiceImpl : getPopularMovies completed");
        return movieDescriptionList;
    }

    /**
     * Business implementation for search a movie based on the given input
     *
     * @param input to search the movie in the original_title (example : <a href="https://api.themoviedb.org/3/search/movie?api_key=102196722a052b03fa096856c680badd&language=en-US&query=venom&page=1&include_adult=false">...</a>)
     * @return PopularMovieResponse
     */
    @Override
    public PopularMovieResponse searchMovie(String input) {
        log.info("MovieServiceImpl : searchMovie started");
        String pageAndIncludeAdult = "&page=1&include_adult=false";
        ResponseEntity<PopularMovieResponse> resPopularMovies = restTemplate.getForEntity(externalSearchServiceUrl + input + pageAndIncludeAdult, PopularMovieResponse.class);
        log.error("MovieServiceImpl : searchMovie response  is {}",resPopularMovies.getBody());
        if (requireNonNull(resPopularMovies.getBody()).getResults().isEmpty()) {
            log.error("MovieServiceImpl : searchMovie result is empty");
            throw new MovieNotFoundException();
        }
        log.info("MovieServiceImpl : searchMovie completed");
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
        log.info("MovieServiceImpl : getMovieDetails started for movie id {}",movieId);
        String movieApiWithKey = "https://api.themoviedb.org/3/movie/";
        String apiKey = "?api_key=102196722a052b03fa096856c680badd&language=en-US";
        ResponseEntity<MovieDetails> movieDetails = restTemplate.getForEntity(movieApiWithKey + movieId + apiKey, MovieDetails.class);
        MovieDetails details = movieDetails.getBody();
        log.info("MovieServiceImpl : getMovieDetails response is {}",details);
        log.info("MovieServiceImpl : getMovieDetails completed");
        return details;
    }

    /**
     * Business implementation for booking a movie ticket
     *
     * @param bookingDetails booking details
     * @return BookingDetails
     */
    @Override
    public BookingDetails createBooking(BookingDetails bookingDetails) {
        log.info("MovieServiceImpl : createBooking started with bookingDetails {}",bookingDetails);
        BookingDetails existingBooking = repository.findByEmail(bookingDetails.getEmail());
        if (existingBooking == null && bookingDetails.getNumberOfTickets() <= 10) {
            bookingDetails.setBookingStatus("confirmed");
            repository.save(bookingDetails);
            log.error("MovieServiceImpl : createBooking booking is successful");
        } else if (bookingDetails.getNumberOfTickets() > 10) {
            log.error("MovieServiceImpl : createBooking number of tickets are more than 10");
            throw new CreateBookingException(bookingDetails.getNumberOfTickets(), bookingDetails.getEmail());
        } else {
            log.error("MovieServiceImpl : createBooking duplicate booking occurred");
            throw new DuplicateBookingException();
        }
        log.info("MovieServiceImpl : createBooking completed");
        return bookingDetails;
    }
}
