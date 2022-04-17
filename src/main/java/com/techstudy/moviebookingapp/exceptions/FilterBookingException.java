package com.techstudy.moviebookingapp.exceptions;

/**
 * @author souvikdey
 */
public class FilterBookingException extends RuntimeException {
    public FilterBookingException(){
        super("No movie is available in the given date range");
    }
}
