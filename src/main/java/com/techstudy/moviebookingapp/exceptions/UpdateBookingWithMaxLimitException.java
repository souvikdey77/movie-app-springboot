package com.techstudy.moviebookingapp.exceptions;

/**
 * @author souvikdey
 */
public class UpdateBookingWithMaxLimitException extends RuntimeException{
    public UpdateBookingWithMaxLimitException(String email){
        super(String.format("Maximum limit reached with email %s", email));
    }
}
