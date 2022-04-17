package com.techstudy.moviebookingapp.exceptions;

/**
 * @author souvikdey
 */
public class DuplicateBookingException extends RuntimeException{
    public DuplicateBookingException(){
        super("Trying to create duplicate booking!!");
    }
}
