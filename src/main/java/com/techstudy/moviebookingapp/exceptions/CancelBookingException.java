package com.techstudy.moviebookingapp.exceptions;

/**
 * @author souvikdey
 */
public class CancelBookingException extends RuntimeException{

    public CancelBookingException(String email){
        super(String.format("Booking is not cancelled with email %s", email));
    }
}
