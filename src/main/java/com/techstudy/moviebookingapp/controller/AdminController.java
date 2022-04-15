package com.techstudy.moviebookingapp.controller;

import com.techstudy.moviebookingapp.model.BookingDetails;
import com.techstudy.moviebookingapp.model.BookingTicketDetails;
import com.techstudy.moviebookingapp.serviceImpl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/admin-management")
public class AdminController {

    @Autowired
    private AdminServiceImpl serviceImpl;

    @GetMapping("/bookings/view")
    public ResponseEntity<List<BookingDetails>> viewBookings(){
        return new ResponseEntity<>(serviceImpl.viewBookings(), HttpStatus.OK);
    }

    @GetMapping("/bookings/{email_id}")
    public ResponseEntity<Boolean> cancelBooking(@PathVariable("email_id") String email) throws Exception {
         Boolean cancelledStatus = serviceImpl.cancelBooking(email);
         if(!cancelledStatus){
             throw new Exception("Booking is not cancelled with id : " + email);
         }
         return new ResponseEntity<>(cancelledStatus, HttpStatus.OK);
    }

    @GetMapping("/bookings/search")
    public ResponseEntity<List<BookingDetails>> searchBooking(@RequestParam String input){
        List<BookingDetails> bookings = serviceImpl.searchBooking(input);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/bookings/filter")
    public ResponseEntity<BookingDetails> filterBooking(@RequestParam(required = true) Date fromDate, @RequestParam(required = true) Date toDate) throws Exception {
        BookingDetails bookingDetails = serviceImpl.filterBooking(fromDate,toDate);
        if(bookingDetails == null){
            throw new Exception("No data found");
        }
        return new ResponseEntity<>(bookingDetails, HttpStatus.OK);
    }

    @PutMapping("/update/bookings/{email}")
    public ResponseEntity<BookingDetails> updateBooking(@PathVariable("email") String email, @RequestBody BookingTicketDetails bookingTicketDetails) throws Exception {
        BookingDetails updatedDetails = serviceImpl.updateBooking(email, bookingTicketDetails);
        if(updatedDetails == null){
            throw new Exception("Booking is not updated for email : "+ email);
        }
        return new ResponseEntity<>(updatedDetails, HttpStatus.OK);
    }
}
