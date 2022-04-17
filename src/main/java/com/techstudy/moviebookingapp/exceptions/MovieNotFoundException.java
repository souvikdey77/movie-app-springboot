package com.techstudy.moviebookingapp.exceptions;

/**
 * @author souvikdey
 */
public class MovieNotFoundException extends RuntimeException{
    public MovieNotFoundException(){
        super("No movie is available");
    }
}
