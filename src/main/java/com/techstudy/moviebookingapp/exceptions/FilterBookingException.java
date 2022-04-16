package com.techstudy.moviebookingapp.exceptions;

public class FilterBookingException extends RuntimeException {
    public FilterBookingException(){
        super("No movie is available in the given date range");
    }
}
