package com.techstudy.moviebookingapp.exceptions;

public class UpdateBookingWithMaxLimitException extends RuntimeException{
    public UpdateBookingWithMaxLimitException(String email){
        super(String.format("Maximum limit reached with email %s", email));
    }
}
