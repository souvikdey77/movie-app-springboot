package com.techstudy.moviebookingapp.exceptions;

/**
 * @author souvikdey
 */
public class CreateBookingException extends  RuntimeException{
    public CreateBookingException(int numberOfTickets, String email){
        super(String.format("Maximum ticket limit is reached with ticket number %d for emailId %s",numberOfTickets, email));
    }
}
