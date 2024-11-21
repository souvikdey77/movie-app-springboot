package com.techstudy.moviebookingapp.serviceimpl;

import com.techstudy.moviebookingapp.exceptions.FilterBookingException;
import com.techstudy.moviebookingapp.exceptions.UpdateBookingException;
import com.techstudy.moviebookingapp.exceptions.UpdateBookingWithMaxLimitException;
import com.techstudy.moviebookingapp.model.BookingDetails;
import com.techstudy.moviebookingapp.model.BookingTicketDetails;
import com.techstudy.moviebookingapp.repository.AdminRepository;
import com.techstudy.moviebookingapp.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Service implementation class for Admin related functionalities
 *
 * @author souvikdey
 */
@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    /**
     * Business implementation for viewing all the bookings
     *
     * @return List<BookingDetails>
     */
    @Override
    public List<BookingDetails> viewBookings() {
        return adminRepository.findAll();
    }

    /**
     * Business implementation for cancel the booking by providing an email id
     *
     * @param email email id needs to be provided
     * @return BookingDetails
     */
    @Override
    public BookingDetails cancelBooking(String email) {

        BookingDetails updatedBooking = null;
        BookingDetails existingBookingDetails = adminRepository.findByEmail(email);
        if (existingBookingDetails != null) {
            updatedBooking = new BookingDetails();
            updatedBooking.setBookingStatus("cancelled");
            updatedBooking.setNumberOfTickets(0);
            updatedBooking.setEmail(email);
            updatedBooking.setFirstName(existingBookingDetails.getFirstName());
            updatedBooking.setMovieTitle(existingBookingDetails.getMovieTitle());
            updatedBooking.setLastName(existingBookingDetails.getLastName());
            adminRepository.save(updatedBooking);
        }
        return updatedBooking;
    }

    /**
     * Business implementation for searching a booking by the admin input which will match with firstname, lastname & email
     *
     * @param input input string needs to be provided
     * @return List<BookingDetails>
     */
    @Override
    public List<BookingDetails> searchBooking(String input) {
        return adminRepository.searchBooking(input);
    }

    /**
     * Business implementation for searching a booking by passing fromdate & todate
     *
     * @param fromDate from date
     * @param toDate   to date
     * @return List<BookingDetails>
     */
    @Override
    public List<BookingDetails> filterBooking(Date fromDate, Date toDate) {
        List<BookingDetails> bookingDetails = adminRepository.filterBooking(fromDate, toDate);
        if (bookingDetails.isEmpty()) {
            throw new FilterBookingException();
        }
        return bookingDetails;
    }

    /**
     * Business implementation for updating an existing booking
     *
     * @param email                email id
     * @param bookingTicketDetails booking details
     * @return BookingDetails
     */
    @Override
    public BookingDetails updateBooking(String email, BookingTicketDetails bookingTicketDetails) {
        int maxTicketCount = 10;
        BookingDetails existingBookingDetails = adminRepository.findByEmail(email);
        if (existingBookingDetails != null) {
            if (bookingTicketDetails.getBookingDate() != null) {
                existingBookingDetails.setBookingDate(bookingTicketDetails.getBookingDate());
            }
            if (bookingTicketDetails.getNumberOfTickets() != 0 && maxTicketCount >= bookingTicketDetails.getNumberOfTickets()) {
                existingBookingDetails.setNumberOfTickets(bookingTicketDetails.getNumberOfTickets());
            } else {
                throw new UpdateBookingWithMaxLimitException(email);
            }
            return adminRepository.save(existingBookingDetails);
        } else {
            throw new UpdateBookingException();
        }
    }
}
