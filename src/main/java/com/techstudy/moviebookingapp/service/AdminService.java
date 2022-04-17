package com.techstudy.moviebookingapp.service;

import com.techstudy.moviebookingapp.model.BookingDetails;
import com.techstudy.moviebookingapp.model.BookingTicketDetails;
import java.util.Date;
import java.util.List;

/**
 * Service interface for all the admin services
 * @author souvikdey
 */
public interface AdminService {

     List<BookingDetails> viewBookings();
     BookingDetails cancelBooking(String email);
     List<BookingDetails> searchBooking(String input);
     List<BookingDetails> filterBooking(Date fromDate, Date toDate);
     BookingDetails updateBooking(String email, BookingTicketDetails bookingTicketDetails) throws Exception;

}
