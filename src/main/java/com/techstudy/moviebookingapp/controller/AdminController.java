package com.techstudy.moviebookingapp.controller;

import com.techstudy.moviebookingapp.exceptions.CancelBookingException;
import com.techstudy.moviebookingapp.model.BookingDetails;
import com.techstudy.moviebookingapp.model.BookingTicketDetails;
import com.techstudy.moviebookingapp.serviceimpl.AdminServiceImpl;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AdminController {

    private final AdminServiceImpl serviceImpl;

    public AdminController(AdminServiceImpl serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    /**
     * Method to view all the bookings
     * @return List<BookingDetails>
     */
    @GetMapping("/bookings/view")
    public ResponseEntity<List<BookingDetails>> viewBookings(){
        log.info("AdminController : viewBookings started");
        List<BookingDetails> bookingDetails =  serviceImpl.viewBookings();
        log.info("AdminController : viewBookings bookingDetails are {}",bookingDetails);
        log.info("AdminController : viewBookings completed");
        return new ResponseEntity<>(bookingDetails, HttpStatus.OK);
    }

    /**
     * Method to cancel a given booking
     * @param email email id to be provided
     * @return BookingDetails
     */
    @GetMapping("/bookings/cancel/{email_id}")
    public ResponseEntity<BookingDetails> cancelBooking(@PathVariable("email_id") String email) {
        log.info("AdminController : cancelBooking started with email id {}",email);
        BookingDetails bookingDetails = serviceImpl.cancelBooking(email);
        log.error("AdminController : cancelBooking booking detail is {}",bookingDetails);
         if(!bookingDetails.getBookingStatus().equalsIgnoreCase("cancelled")){
             log.error("AdminController : cancelBooking booking could not get cancelled");
             throw new CancelBookingException(email);
         }
        log.info("AdminController : cancelBooking completed with email id {}",email);
         return new ResponseEntity<>(bookingDetails, HttpStatus.OK);
    }

    /**
     * Method to search for all the booking by giving the input which will check with firstname, lastname & email
     * @param input input string needs to be provided
     * @return List<BookingDetails>
     */
    @GetMapping("/bookings/search")
    public ResponseEntity<List<BookingDetails>> searchBooking(@RequestParam String input){
        log.info("AdminController : searchBooking started with input {}",input);
        List<BookingDetails> bookings = serviceImpl.searchBooking(input);
        log.info("AdminController : searchBooking response is {}",bookings);
        log.info("AdminController : searchBooking completed");
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    /**
     * Method to filter all the bookings by passing fromdate and todate
     * @param fromDate from date to be provided
     * @param toDate to date to be provided
     * @return List<BookingDetails>
     */
    @GetMapping("/bookings/filter")
    public ResponseEntity<List<BookingDetails>> filterBooking(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate) {
        log.info("AdminController : filterBooking started with fromDate {} and toDate {}",fromDate,toDate);
        List<BookingDetails> bookingDetails = serviceImpl.filterBooking(fromDate,toDate);
        log.info("AdminController : bookingDetails are {}",bookingDetails);
        log.info("AdminController : filterBooking completed");
        return new ResponseEntity<>(bookingDetails, HttpStatus.OK);
    }

    /**
     * Method to update an existing booking
     * @param email email id to be provided
     * @param bookingTicketDetails booking ticket details to be provided
     * @return BookingDetails
     */
    @PutMapping("/update/bookings/{email}")
    public ResponseEntity<BookingDetails> updateBooking(@PathVariable("email") String email, @RequestBody BookingTicketDetails bookingTicketDetails) {
        BookingDetails updatedDetails = serviceImpl.updateBooking(email, bookingTicketDetails);
        return new ResponseEntity<>(updatedDetails, HttpStatus.OK);
    }
}
