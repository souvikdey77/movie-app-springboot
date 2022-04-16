package com.techstudy.moviebookingapp.exceptions;

public class UpdateBookingException extends RuntimeException{
    public UpdateBookingException(){
        super("Existing Booking details is not there to update");
    }
}
