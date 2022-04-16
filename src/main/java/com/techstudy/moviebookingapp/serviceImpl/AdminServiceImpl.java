package com.techstudy.moviebookingapp.serviceImpl;

import com.techstudy.moviebookingapp.model.BookingDetails;
import com.techstudy.moviebookingapp.model.BookingTicketDetails;
import com.techstudy.moviebookingapp.repository.AdminRepository;
import com.techstudy.moviebookingapp.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public List<BookingDetails> viewBookings() {
        return adminRepository.findAll();
    }

    @Override
    public BookingDetails cancelBooking(String email){

        BookingDetails updatedBooking = null;
        Boolean cancelledStatus = false;
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
            cancelledStatus = true;
        }
        return updatedBooking;
    }

    @Override
    public List<BookingDetails> searchBooking(String input) {
        return adminRepository.searchBooking(input);
    }

    @Override
    public List<BookingDetails> filterBooking(Date fromDate, Date toDate) {
        List<BookingDetails> bookingDetails = adminRepository.filterBooking(fromDate,toDate);
        return bookingDetails;
    }

    @Override
    public BookingDetails updateBooking(String email, BookingTicketDetails bookingTicketDetails) throws Exception {
        int maxTicketCount = 10;
        BookingDetails existingBookingDetails = adminRepository.findByEmail(email);
        if(existingBookingDetails != null){
            existingBookingDetails.setBookingDate(bookingTicketDetails.getBookingDate());
            existingBookingDetails.setFirstName(bookingTicketDetails.getFirstName());
            existingBookingDetails.setLastName(bookingTicketDetails.getLastName());
            existingBookingDetails.setMovieTitle(bookingTicketDetails.getMovieTitle());
            if(maxTicketCount >= bookingTicketDetails.getNumberOfTickets()){
                existingBookingDetails.setNumberOfTickets(bookingTicketDetails.getNumberOfTickets());
            }else{
                throw new Exception("Max ticket limit reached");
            }
            return adminRepository.save(existingBookingDetails);
        }else{
            throw new Exception("Existing Booking details is not there to update");
        }
    }
}
