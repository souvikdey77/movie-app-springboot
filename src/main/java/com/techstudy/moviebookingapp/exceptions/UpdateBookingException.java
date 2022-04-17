package com.techstudy.moviebookingapp.exceptions;

/**
 * @author souvikdey
 */
public class UpdateBookingException extends RuntimeException{
    public UpdateBookingException(){
        super("Existing Booking details is not there to update");
    }
}
