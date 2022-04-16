package com.techstudy.moviebookingapp.serviceImpl;

import com.techstudy.moviebookingapp.exceptions.CreateBookingException;
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

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AdminRepository repository;

    @Override
    public List<MovieDescription> getPopularMovies() {
        ResponseEntity<PopularMovieResponse> resPopularMovies = restTemplate.getForEntity("https://api.themoviedb.org/3/discover/movie?api_key=102196722a052b03fa096856c680badd&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&with_watch_monetization_types=flatrate",PopularMovieResponse.class);
        List<MovieDescription> movies = resPopularMovies.getBody().getResults().stream().limit(10).collect(Collectors.toList());
        if(movies.size() == 0){
            throw new MovieNotFoundException();
        }
        return movies;
    }

    @Override
    public PopularMovieResponse searchMovie(String input) {
        ResponseEntity<PopularMovieResponse> resPopularMovies = null;
        resPopularMovies = restTemplate.getForEntity("https://api.themoviedb.org/3/search/movie?api_key=102196722a052b03fa096856c680badd&language=en-US&query="+input+"&page=1&include_adult=false", PopularMovieResponse.class);
        if(resPopularMovies.getBody().getResults().isEmpty()){
            throw new MovieNotFoundException();
        }
        return resPopularMovies.getBody();
    }

    @Override
    public MovieDetails getMovieDetails(String movieId) {
        ResponseEntity<MovieDetails> movieDetails = restTemplate.getForEntity("https://api.themoviedb.org/3/movie/"+movieId+"?api_key=102196722a052b03fa096856c680badd&language=en-US",MovieDetails.class);
        return movieDetails.getBody();
    }

    @Override
    public BookingDetails createBooking(BookingTicketDetails bookingTicketDetails) throws Exception {
        BookingDetails bookingDetails = null;
        bookingDetails = repository.findByEmail(bookingTicketDetails.getEmailId());
        if(bookingDetails != null){
            int totalBookedTickets = bookingDetails.getNumberOfTickets();
            int allowedTickets = 10 - totalBookedTickets;
            if(allowedTickets > 0 && bookingTicketDetails.getNumberOfTickets() <= allowedTickets){
                bookingDetails = new BookingDetails();
                bookingDetails.setBookingDate(bookingTicketDetails.getBookingDate());
                bookingDetails.setLastName(bookingTicketDetails.getLastName());
                bookingDetails.setFirstName(bookingTicketDetails.getFirstName());
                bookingDetails.setEmail(bookingTicketDetails.getEmailId());
                bookingDetails.setBookingStatus("confirmed");
                bookingDetails.setNumberOfTickets(bookingTicketDetails.getNumberOfTickets() + totalBookedTickets);
                bookingDetails.setMovieTitle(bookingTicketDetails.getMovieTitle());
                repository.save(bookingDetails);
            }else{
                throw new CreateBookingException(bookingTicketDetails.getNumberOfTickets(),bookingTicketDetails.getEmailId());
            }
        }else if(bookingTicketDetails.getNumberOfTickets() > 10){
            throw new CreateBookingException(bookingTicketDetails.getNumberOfTickets(),bookingTicketDetails.getEmailId());
        }else{
            bookingDetails = new BookingDetails();
            bookingDetails.setBookingDate(bookingTicketDetails.getBookingDate());
            bookingDetails.setLastName(bookingTicketDetails.getLastName());
            bookingDetails.setFirstName(bookingTicketDetails.getFirstName());
            bookingDetails.setEmail(bookingTicketDetails.getEmailId());
            bookingDetails.setBookingStatus("confirmed");
            bookingDetails.setNumberOfTickets(bookingTicketDetails.getNumberOfTickets());
            bookingDetails.setMovieTitle(bookingTicketDetails.getMovieTitle());
            repository.save(bookingDetails);
        }
        return bookingDetails;
    }
}
