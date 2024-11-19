package com.techstudy.moviebookingapp.serviceimpl;

import com.techstudy.moviebookingapp.exceptions.FilterBookingException;
import com.techstudy.moviebookingapp.exceptions.UpdateBookingException;
import com.techstudy.moviebookingapp.exceptions.UpdateBookingWithMaxLimitException;
import com.techstudy.moviebookingapp.model.BookingDetails;
import com.techstudy.moviebookingapp.model.BookingTicketDetails;
import com.techstudy.moviebookingapp.repository.AdminRepository;
import com.techstudy.moviebookingapp.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

/**
 * Service implementation class for Admin related functionalities
 * @author souvikdey
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    /**
     * Business implementation for viewing all the bookings
     * @return List<BookingDetails>
     */
    @Override
    public List<BookingDetails> viewBookings() {
        return adminRepository.findAll();
    }

    /**
     * Business implementation for cancel the booking by providing an email id
     * @param email
     * @return BookingDetails
     */
    @Override
    public BookingDetails cancelBooking(String email){

        BookingDetails updatedBooking = null;
        BookingDetails existingBookingDetails = adminRepository.findByEmail(email);
        if(existingBookingDetails != null){
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
     * @param input
     * @return List<BookingDetails>
     */
    @Override
    public List<BookingDetails> searchBooking(String input) {
        return adminRepository.searchBooking(input);
    }

    /**
     * Business implementation for searching a booking by passing fromdate & todate
     * @param fromDate
     * @param toDate
     * @return List<BookingDetails>
     */
    @Override
    public List<BookingDetails> filterBooking(Date fromDate, Date toDate) {
        List<BookingDetails> bookingDetails = adminRepository.filterBooking(fromDate,toDate);
        if(bookingDetails.size() == 0){
            throw new FilterBookingException();
        }
        return bookingDetails;
    }

    /**
     * Business implementation for updating an existing booking
     * @param email
     * @param bookingTicketDetails
     * @return BookingDetails
     * @throws Exception
     */
    @Override
    public BookingDetails updateBooking(String email, BookingTicketDetails bookingTicketDetails) throws Exception {
        int maxTicketCount = 10;
        BookingDetails existingBookingDetails = adminRepository.findByEmail(email);
        if(existingBookingDetails != null){
            if(bookingTicketDetails.getBookingDate() != null){
                existingBookingDetails.setBookingDate(bookingTicketDetails.getBookingDate());
            }
            if(bookingTicketDetails.getNumberOfTickets() != 0 && maxTicketCount >= bookingTicketDetails.getNumberOfTickets()){
                existingBookingDetails.setNumberOfTickets(bookingTicketDetails.getNumberOfTickets());
            }else{
                throw new UpdateBookingWithMaxLimitException(email);
            }
            return adminRepository.save(existingBookingDetails);
        }else{
            throw new UpdateBookingException();
        }
    }
}
