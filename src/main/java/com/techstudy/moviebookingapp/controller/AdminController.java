package com.techstudy.moviebookingapp.controller;

import com.techstudy.moviebookingapp.exceptions.CancelBookingException;
import com.techstudy.moviebookingapp.model.BookingDetails;
import com.techstudy.moviebookingapp.model.BookingTicketDetails;
import com.techstudy.moviebookingapp.serviceImpl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/admin-management")
public class AdminController {

    @Autowired
    private AdminServiceImpl serviceImpl;

    @GetMapping("/bookings/view")
    public ResponseEntity<List<BookingDetails>> viewBookings(){
        return new ResponseEntity<>(serviceImpl.viewBookings(), HttpStatus.OK);
    }

    @GetMapping("/bookings/cancel/{email_id}")
    public ResponseEntity<BookingDetails> cancelBooking(@PathVariable("email_id") String email) throws Exception {
        BookingDetails bookingDetails = serviceImpl.cancelBooking(email);
         if(!bookingDetails.getBookingStatus().equalsIgnoreCase("cancelled")){
             throw new CancelBookingException(email);
         }
         return new ResponseEntity<>(bookingDetails, HttpStatus.OK);
    }

    @GetMapping("/bookings/search")
    public ResponseEntity<List<BookingDetails>> searchBooking(@RequestParam String input){
        List<BookingDetails> bookings = serviceImpl.searchBooking(input);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/bookings/filter")
    public ResponseEntity<List<BookingDetails>> filterBooking(@RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate, @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate) throws Exception {
        List<BookingDetails> bookingDetails = serviceImpl.filterBooking(fromDate,toDate);
        return new ResponseEntity<>(bookingDetails, HttpStatus.OK);
    }

    @PutMapping("/update/bookings/{email}")
    public ResponseEntity<BookingDetails> updateBooking(@PathVariable("email") String email, @RequestBody BookingTicketDetails bookingTicketDetails) throws Exception {
        BookingDetails updatedDetails = serviceImpl.updateBooking(email, bookingTicketDetails);
        return new ResponseEntity<>(updatedDetails, HttpStatus.OK);
    }
}
