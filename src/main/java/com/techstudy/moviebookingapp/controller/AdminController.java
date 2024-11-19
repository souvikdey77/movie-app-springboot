package com.techstudy.moviebookingapp.controller;

import com.techstudy.moviebookingapp.exceptions.CancelBookingException;
import com.techstudy.moviebookingapp.model.BookingDetails;
import com.techstudy.moviebookingapp.model.BookingTicketDetails;
import com.techstudy.moviebookingapp.serviceimpl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

/**
 * Controller for the admin related functionalities
 * @author Souvik Dey
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/admin-management")
public class AdminController {

    @Autowired
    private AdminServiceImpl serviceImpl;

    /**
     * Method to view all the bookings
     * @return List<BookingDetails>
     */
    @GetMapping("/bookings/view")
    public ResponseEntity<List<BookingDetails>> viewBookings(){
        return new ResponseEntity<>(serviceImpl.viewBookings(), HttpStatus.OK);
    }

    /**
     * Method to cancel a given booking
     * @param email
     * @return BookingDetails
     * @throws Exception
     */
    @GetMapping("/bookings/cancel/{email_id}")
    public ResponseEntity<BookingDetails> cancelBooking(@PathVariable("email_id") String email) throws Exception {
        BookingDetails bookingDetails = serviceImpl.cancelBooking(email);
         if(!bookingDetails.getBookingStatus().equalsIgnoreCase("cancelled")){
             throw new CancelBookingException(email);
         }
         return new ResponseEntity<>(bookingDetails, HttpStatus.OK);
    }

    /**
     * Method to search for all the booking by giving the input which will check with firstname, lastname & email
     * @param input
     * @return List<BookingDetails>
     */
    @GetMapping("/bookings/search")
    public ResponseEntity<List<BookingDetails>> searchBooking(@RequestParam String input){
        List<BookingDetails> bookings = serviceImpl.searchBooking(input);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    /**
     * Method to filter all the bookings by passing fromdate and todate
     * @param fromDate
     * @param toDate
     * @return List<BookingDetails>
     * @throws Exception
     */
    @GetMapping("/bookings/filter")
    public ResponseEntity<List<BookingDetails>> filterBooking(@RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate, @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate) throws Exception {
        List<BookingDetails> bookingDetails = serviceImpl.filterBooking(fromDate,toDate);
        return new ResponseEntity<>(bookingDetails, HttpStatus.OK);
    }

    /**
     * Method to update an existing booking
     * @param email
     * @param bookingTicketDetails
     * @return BookingDetails
     * @throws Exception
     */
    @PutMapping("/update/bookings/{email}")
    public ResponseEntity<BookingDetails> updateBooking(@PathVariable("email") String email, @RequestBody BookingTicketDetails bookingTicketDetails) throws Exception {
        BookingDetails updatedDetails = serviceImpl.updateBooking(email, bookingTicketDetails);
        return new ResponseEntity<>(updatedDetails, HttpStatus.OK);
    }
}
